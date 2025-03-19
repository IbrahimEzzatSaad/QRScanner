package com.example.qrscanner.data.cache;


import androidx.lifecycle.LiveData;

import com.example.qrscanner.data.cache.entities.CachedQrDetails;

import java.util.List;

public interface Cache {

    LiveData<List<CachedQrDetails>> getQrs();

    LiveData<List<CachedQrDetails>> getFavoriteQrs();

    LiveData<CachedQrDetails> getQrById(int id);

    void insertQrDetail(CachedQrDetails cachedQrDetails);

    void toggleFavorite(int id);

    void clearHistory();



}