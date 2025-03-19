package com.example.qrscanner.data.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qrscanner.LiveDataTestUtil;
import com.example.qrscanner.data.cache.QRDatabase;
import com.example.qrscanner.data.cache.daos.QRDao;
import com.example.qrscanner.data.cache.entities.CachedQrDetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class QRDaoTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Inject
    QRDatabase database;

    @Inject
    QRDao qrDao;

    @Before
    public void setup() {
        hiltRule.inject();
    }

    @After
    public void teardown() {
        database.close();
    }


    @Test
    public void toggleFavorite_updatesFavoriteStatus() throws InterruptedException {
        CachedQrDetails qrDetail = new CachedQrDetails(1, "https://example.com", false, System.currentTimeMillis());
        qrDao.insertQrDetail(qrDetail);

        qrDao.toggleFavorite(1);

        LiveData<CachedQrDetails> liveData = qrDao.getQrById(1);
        CachedQrDetails updatedDetail = LiveDataTestUtil.getOrAwaitValue(liveData);

        assertTrue(updatedDetail.favorite);
    }

    @Test
    public void getFavorites_returnsOnlyFavoriteQRs() throws InterruptedException {
        CachedQrDetails qr1 = new CachedQrDetails(1, "https://example.com", false, System.currentTimeMillis());
        CachedQrDetails qr2 = new CachedQrDetails(2, "https://google.com", true, System.currentTimeMillis());

        qrDao.insertQrDetail(qr1);
        qrDao.insertQrDetail(qr2);

        List<CachedQrDetails> favorites = LiveDataTestUtil.getOrAwaitValue(qrDao.getFavorites());

        assertEquals(1, favorites.size());
        assertEquals("https://google.com", favorites.get(0).link);
    }

    @Test
    public void insertAndGetQRs_expectReturnOnlyTwo() throws InterruptedException {
        CachedQrDetails qr1 = new CachedQrDetails(1, "https://example.com", false, System.currentTimeMillis());
        CachedQrDetails qr2 = new CachedQrDetails(2, "https://google.com", true, System.currentTimeMillis());

        qrDao.insertQrDetail(qr1);
        qrDao.insertQrDetail(qr2);

        List<CachedQrDetails> qrs = LiveDataTestUtil.getOrAwaitValue(qrDao.getQRs());

        assertEquals(2, qrs.size());
    }

}