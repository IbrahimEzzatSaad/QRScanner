package com.example.qrscanner.data.cache;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.qrscanner.data.cache.daos.QRDao;
import com.example.qrscanner.data.cache.entities.CachedQrDetails;

@Database(entities = {CachedQrDetails.class}, version = 1)
public abstract class QRDatabase extends RoomDatabase {

    public abstract QRDao qrDao();


}