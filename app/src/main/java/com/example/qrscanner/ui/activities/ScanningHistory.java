package com.example.qrscanner.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.qrscanner.R;
import com.example.qrscanner.databinding.ActivityBarcodeScanningBinding;
import com.example.qrscanner.databinding.ActivityScanningHistoryBinding;
import com.example.qrscanner.domain.model.QrDetail;
import com.example.qrscanner.ui.adapters.HistoryAdapter;
import com.example.qrscanner.ui.viewmodels.BarCodeViewModel;
import com.example.qrscanner.ui.viewmodels.HistoryViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ScanningHistory extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ScanningHistory.class);
        context.startActivity(intent);
    }

    private ActivityScanningHistoryBinding binding;
    HistoryViewModel historyViewModel;
    private HistoryAdapter historyAdapter;

    private boolean favoriteOnly = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityScanningHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        historyAdapter = new HistoryAdapter(new ArrayList<>(), qrCode -> {
            historyViewModel.toggleQrFavoriteState(qrCode.id);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(historyAdapter);


        historyViewModel.qrHistory.observe(this, qrCodes -> {
            if (qrCodes != null) {
                updateListWithFilter(qrCodes);
            }
        });

        binding.favoriteButton.setOnClickListener(v -> {
            favoriteOnly = !favoriteOnly;

            if (favoriteOnly) {
                binding.favoriteButton.setBackground(getDrawable(R.drawable.button_shape));
                binding.favoriteButton.setTextColor(getColor(R.color.white));
            } else {
                binding.favoriteButton.setBackground(getDrawable(R.drawable.unselected_button_shape));
                binding.favoriteButton.setTextColor(getColor(R.color.black));
            }

            if (historyViewModel.qrHistory.getValue() != null) {
                updateListWithFilter(historyViewModel.qrHistory.getValue());
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void updateListWithFilter(List<QrDetail> allItems) {
        if (favoriteOnly) {
            List<QrDetail> favoriteItems = allItems.stream()
                    .filter(qrDetail -> qrDetail.favorite)
                    .collect(Collectors.toList());
            historyAdapter.updateList(favoriteItems);
        } else {
            historyAdapter.updateList(allItems);
        }
    }
}