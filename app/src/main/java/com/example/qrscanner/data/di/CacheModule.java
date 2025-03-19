package com.example.qrscanner.data.di;

import android.content.Context;

import androidx.room.Room;

import com.example.qrscanner.data.cache.Cache;
import com.example.qrscanner.data.cache.QRDatabase;
import com.example.qrscanner.data.cache.RoomCache;
import com.example.qrscanner.data.cache.daos.QRDao;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class CacheModule {

    @Binds
    public abstract Cache bindCache(RoomCache cache);

    @Module
    @InstallIn(SingletonComponent.class)
    public static class CacheModuleProvider {

        @Provides
        @Singleton
        public static QRDatabase provideDatabase(@ApplicationContext Context context) {
            return Room.databaseBuilder(context, QRDatabase.class, "qrscanner.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        @Provides
        public static QRDao provideQRDao(QRDatabase qrDatabase) {
            return qrDatabase.qrDao();
        }

    }
}