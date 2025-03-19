package com.example.qrscanner.domain.usecases;

import com.example.qrscanner.domain.model.QrDetail;
import com.example.qrscanner.domain.repoistory.QrRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class AddQR {

    private final QrRepository qrRepository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Inject
    public AddQR(QrRepository qrRepository) {
        this.qrRepository = qrRepository;
    }

    public void execute(QrDetail qrDetail) {
        executor.execute(() -> qrRepository.addQr(qrDetail));


    }
}