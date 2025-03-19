package com.example.qrscanner.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.qrscanner.domain.model.QrDetail;
import com.example.qrscanner.domain.usecases.AddQR;
import com.example.qrscanner.domain.usecases.GetQrs;
import com.example.qrscanner.domain.usecases.ToggleQrFavorite;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HistoryViewModel extends ViewModel {

    private final ToggleQrFavorite toggleQrFavorite;

    public final LiveData<List<QrDetail>> qrHistory;

    @Inject
    public HistoryViewModel(GetQrs getQrs, ToggleQrFavorite toggleQrFavorite) {
        this.toggleQrFavorite = toggleQrFavorite;
        this.qrHistory = getQrs.execute();
    }

    public void toggleQrFavoriteState(int id){
        toggleQrFavorite.execute(id);
    }
}