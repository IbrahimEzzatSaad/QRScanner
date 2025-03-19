package com.example.qrscanner.data.cache.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.qrscanner.domain.model.QrDetail;

@Entity(tableName = "qr_details")
public class CachedQrDetails {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String link;

    public boolean favorite;
    public long createdAt;

    public CachedQrDetails(String link, boolean favorite, long createdAt) {
        this.link = link;
        this.favorite = favorite;
        this.createdAt = createdAt;
    }

    @Ignore
    public CachedQrDetails(int id, String link, boolean favorite, long createdAt) {
        this.id = id;
        this.link = link;
        this.favorite = favorite;
        this.createdAt = createdAt;
    }
    public QrDetail toDomain() {
        return new QrDetail(id, link, favorite, createdAt);
    }

}