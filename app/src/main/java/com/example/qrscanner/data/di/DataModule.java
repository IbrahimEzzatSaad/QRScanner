package com.example.qrscanner.data.di;

import com.example.qrscanner.data.repositories.QrRepositoryImp;
import com.example.qrscanner.domain.repoistory.QrRepository;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Provides
    @Singleton
    public QrRepository provideQrRepository(QrRepositoryImp repository) {
        return repository;
    }


}