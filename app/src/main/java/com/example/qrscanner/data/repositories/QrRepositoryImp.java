package com.example.qrscanner.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.qrscanner.data.cache.Cache;
import com.example.qrscanner.data.cache.entities.CachedQrDetails;
import com.example.qrscanner.data.di.DataModule;
import com.example.qrscanner.domain.model.QrDetail;
import com.example.qrscanner.domain.repoistory.QrRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class QrRepositoryImp implements QrRepository {

    private final Cache cache;

    @Inject
    public QrRepositoryImp(Cache cache) {
        this.cache = cache;
    }
    @Override
    public LiveData<List<QrDetail>> getQrs() {
        return androidx.lifecycle.Transformations.map(cache.getQrs(), cachedList -> {
            List<QrDetail> qrDetails = new ArrayList<>();
            for (CachedQrDetails cached : cachedList) {
                qrDetails.add(cached.toDomain());
            }
            return qrDetails;
        });
    }
    @Override
    public LiveData<List<QrDetail>> getFavoriteQrs() {
        return Transformations.map(cache.getFavoriteQrs(), cachedList -> {
            List<QrDetail> qrDetails = new ArrayList<>();
            for (CachedQrDetails cached : cachedList) {
                qrDetails.add(cached.toDomain());
            }
            return qrDetails;
        });
    }

    @Override
    public LiveData<QrDetail> getQrById(int id) {
        return Transformations.map(cache.getQrById(id), CachedQrDetails::toDomain);
    }

    @Override
    public void addQr(QrDetail qr) {
        cache.insertQrDetail(qr.toEntity());
    }

    @Override
    public void toggleFavorite(int id) {
        cache.toggleFavorite(id);
    }

    @Override
    public void clearHistory() {
        cache.clearHistory();
    }
}
