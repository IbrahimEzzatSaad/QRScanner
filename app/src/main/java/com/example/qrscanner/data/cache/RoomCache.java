package com.example.qrscanner.data.cache;

import androidx.lifecycle.LiveData;

import com.example.qrscanner.data.cache.daos.QRDao;
import com.example.qrscanner.data.cache.entities.CachedQrDetails;

import java.util.List;

import javax.inject.Inject;

public class RoomCache implements Cache {

    private final QRDao qrDao;

    @Inject
    public RoomCache(QRDao qrDao) {
        this.qrDao = qrDao;
    }

    @Override
    public LiveData<List<CachedQrDetails>> getQrs() {
        return qrDao.getQRs();
    }

    @Override
    public LiveData<List<CachedQrDetails>> getFavoriteQrs() {
        return qrDao.getFavorites();
    }

    @Override
    public LiveData<CachedQrDetails> getQrById(int id) {
        return qrDao.getQrById(id);
    }

    @Override
    public void insertQrDetail(CachedQrDetails cachedQrDetails) {
        qrDao.insertQrDetail(cachedQrDetails);
    }

    @Override
    public void toggleFavorite(int id) {
        qrDao.toggleFavorite(id);
    }

    @Override
    public void clearHistory() {
        qrDao.clearAll();
    }
}
