package com.example.qrscanner.domain.usecases;

import com.example.qrscanner.domain.repoistory.QrRepository;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class ToggleQrFavorite {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final QrRepository qrRepository;

    @Inject
    public ToggleQrFavorite(QrRepository qrRepository) {
        this.qrRepository = qrRepository;
    }

    public void execute(int id) {
        executor.execute(() -> qrRepository.toggleFavorite(id));
    }
}