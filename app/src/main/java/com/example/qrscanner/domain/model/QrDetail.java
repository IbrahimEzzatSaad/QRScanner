package com.example.qrscanner.domain.model;


import com.example.qrscanner.data.cache.entities.CachedQrDetails;

import java.util.Date;

public class QrDetail {
    public Integer id;
    public String link;
    public boolean favorite;
    public long createdAt;

    public QrDetail(String link, boolean favorite) {
        this.id = null;
        this.link = link;
        this.favorite = favorite;
        this.createdAt = new Date().getTime();
    }

    public QrDetail(int id, String link, boolean favorite, long createdAt) {
        this.id = id;
        this.link = link;
        this.favorite = favorite;
        this.createdAt = createdAt;
    }

    public CachedQrDetails toEntity() {
        return new CachedQrDetails(link, favorite, createdAt);
    }
}