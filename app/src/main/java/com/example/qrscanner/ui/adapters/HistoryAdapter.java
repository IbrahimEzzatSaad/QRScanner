package com.example.qrscanner.ui.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrscanner.databinding.QrItemBinding;
import com.example.qrscanner.domain.model.QrDetail;
import com.example.qrscanner.util.ClipboardUtils;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final ArrayList<QrDetail> items;
    private final OnFavoriteClickListener favoriteClickListener;

    public interface OnFavoriteClickListener {
        void onFavoriteClicked(QrDetail qrDetail);
    }

    public HistoryAdapter(ArrayList<QrDetail> items, OnFavoriteClickListener favoriteClickListener) {
        this.items = new ArrayList<>(items);
        this.favoriteClickListener = favoriteClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final QrItemBinding binding;

        public ViewHolder(QrItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(QrDetail qrDetail, OnFavoriteClickListener listener) {
            binding.title.setText(qrDetail.link);
            binding.favoriteButton.setSelected(qrDetail.favorite);

            binding.copyLayout.setOnClickListener(v ->{
                ClipboardUtils.copyToClipboard(binding.getRoot().getContext(), qrDetail.link);
            });
            binding.favoriteButton.setOnClickListener(v -> {
                listener.onFavoriteClicked(qrDetail);
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QrItemBinding binding = QrItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position), favoriteClickListener);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateList(List<QrDetail> newList) {
        items.clear();
        items.addAll(newList);
        notifyDataSetChanged();
    }

}