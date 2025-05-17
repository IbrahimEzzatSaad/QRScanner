package com.example.qrscanner.data.repositories;


import androidx.lifecycle.LiveData;

import com.example.qrscanner.domain.model.QrDetail;
import java.util.List;

public interface QrRepository {

    LiveData<List<QrDetail>> getQrs();

    LiveData<List<QrDetail>> getFavoriteQrs();

    LiveData<QrDetail> getQrById(int id);

    void addQr(QrDetail qr);


    void toggleFavorite(int id);

    void clearHistory();

}