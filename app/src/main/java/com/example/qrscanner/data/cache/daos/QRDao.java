package com.example.qrscanner.data.cache.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.qrscanner.data.cache.entities.CachedQrDetails;

import java.util.List;

@Dao
public abstract class QRDao {

    @Transaction
    @Query("SELECT * FROM qr_details ORDER BY createdAt DESC")
    public abstract LiveData<List<CachedQrDetails>> getQRs();

    @Transaction
    @Query("SELECT * FROM qr_details WHERE favorite = 1")
    public abstract LiveData<List<CachedQrDetails>> getFavorites();

    @Query("UPDATE qr_details SET favorite = NOT favorite WHERE id = :id")
    public abstract void toggleFavorite(int id);


    @Query("SELECT * FROM qr_details WHERE id = :id")
    public abstract LiveData<CachedQrDetails> getQrById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertQrDetail(CachedQrDetails qrDetail);

    @Query("DELETE FROM qr_details")
    public abstract void clearAll();
}