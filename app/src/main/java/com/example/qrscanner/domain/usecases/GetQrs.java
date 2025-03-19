package com.example.qrscanner.domain.usecases;

import androidx.lifecycle.LiveData;

import com.example.qrscanner.domain.model.QrDetail;
import com.example.qrscanner.domain.repoistory.QrRepository;

import java.util.List;

import javax.inject.Inject;

public class GetQrs {

    private final QrRepository qrRepository;

    @Inject
    public GetQrs(QrRepository qrRepository) {
        this.qrRepository = qrRepository;
    }

    public LiveData<List<QrDetail>> execute() {
        return qrRepository.getQrs();
    }
}