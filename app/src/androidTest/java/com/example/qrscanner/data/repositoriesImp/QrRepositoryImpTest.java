package com.example.qrscanner.data.repositoriesImp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.qrscanner.LiveDataTestUtil;
import com.example.qrscanner.data.cache.QRDatabase;
import com.example.qrscanner.data.repositories.QrRepository;
import com.example.qrscanner.domain.model.QrDetail;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class QrRepositoryImpTest {
    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Inject
    QrRepository qrRepository;

    @Inject
    QRDatabase database;

    @Before
    public void setup() {
        hiltRule.inject();
    }

    @After
    public void teardown() {
        database.close();
    }


    @Test
    public void getQrs_returnsInsertedQrs() throws InterruptedException {
        QrDetail qr1 = new QrDetail(1, "https://example.com", false, System.currentTimeMillis());
        QrDetail qr2 = new QrDetail(2, "https://google.com", true, System.currentTimeMillis());

        qrRepository.addQr(qr1);
        qrRepository.addQr(qr2);

        List<QrDetail> result = LiveDataTestUtil.getOrAwaitValue(qrRepository.getQrs());

        assertEquals("https://example.com", result.get(0).link);
        assertEquals("https://google.com", result.get(1).link);
    }

    @Test
    public void getFavoriteQrs_returnsOnlyFavorites() throws InterruptedException {
        QrDetail qr1 = new QrDetail(1, "https://example.com", false, System.currentTimeMillis());
        QrDetail qr2 = new QrDetail(2, "https://google.com", true, System.currentTimeMillis());

        qrRepository.addQr(qr1);
        qrRepository.addQr(qr2);

        List<QrDetail> result = LiveDataTestUtil.getOrAwaitValue(qrRepository.getFavoriteQrs());

        assertEquals(1, result.size());
        assertEquals("https://google.com", result.get(0).link);
    }

    @Test
    public void getQrById_returnsCorrectQr() throws InterruptedException {
        QrDetail qr = new QrDetail(1, "https://example.com", false, System.currentTimeMillis());
        qrRepository.addQr(qr);

        QrDetail result = LiveDataTestUtil.getOrAwaitValue(qrRepository.getQrById(1));

        assertNotNull(result);
        assertEquals("https://example.com", result.link);
    }

    @Test
    public void toggleFavorite_changesFavoriteStatus() throws InterruptedException {
        QrDetail qr = new QrDetail(1, "https://example.com", false, System.currentTimeMillis());
        qrRepository.addQr(qr);

        qrRepository.toggleFavorite(1);
        List<QrDetail> favorites = LiveDataTestUtil.getOrAwaitValue(qrRepository.getFavoriteQrs());

        assertTrue(favorites.get(0).favorite);
    }
}
