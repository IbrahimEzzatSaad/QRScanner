package com.example.qrscanner.ui.viewmodels;


import androidx.lifecycle.ViewModel;

import com.example.qrscanner.domain.model.QrDetail;
import com.example.qrscanner.domain.usecases.AddQR;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BarCodeViewModel extends ViewModel {

    private final AddQR addQR;

    @Inject
    public BarCodeViewModel(AddQR addQR) {
        this.addQR = addQR;
    }

    public void newQRScanned(String link) {

        addQR.execute(new QrDetail(link, false));
    }
}
