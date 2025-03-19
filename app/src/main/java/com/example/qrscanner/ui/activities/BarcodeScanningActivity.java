package com.example.qrscanner.ui.activities;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Size;
import android.view.Surface;
import android.view.View;
import android.view.OrientationEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.core.TorchState;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrscanner.R;
import com.example.qrscanner.databinding.ActivityBarcodeScanningBinding;
import com.example.qrscanner.ui.dialogs.ScannerResultDialog;
import com.example.qrscanner.ui.viewmodels.BarCodeViewModel;
import com.example.qrscanner.util.ScanningResultListener;
import com.example.qrscanner.util.ZXingScanner;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BarcodeScanningActivity extends AppCompatActivity {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ActivityBarcodeScanningBinding binding;
    /** Blocking camera operations are performed using this executor */
    private ExecutorService cameraExecutor;
    private boolean flashEnabled = false;

    BarCodeViewModel barCodeViewModel;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, BarcodeScanningActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBarcodeScanningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        barCodeViewModel = new ViewModelProvider(this).get(BarCodeViewModel.class);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        // Initialize our background executor
        cameraExecutor = Executors.newSingleThreadExecutor();

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));

        binding.overlay.post(() -> binding.overlay.setViewFinder());
    }

    private void bindPreview(ProcessCameraProvider cameraProvider) {

        if (isDestroyed() || isFinishing()) {
            // This check avoids an exception when trying to re-bind use cases but the user closes the activity.
            return;
        }

        if (cameraProvider != null) {
            cameraProvider.unbindAll();
        }

        Preview preview = new Preview.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(binding.cameraPreview.getWidth(), binding.cameraPreview.getHeight()))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        OrientationEventListener orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                // Monitors orientation values to determine the target rotation value
                int rotation;
                if (orientation >= 45 && orientation <= 134) {
                    rotation = Surface.ROTATION_270;
                } else if (orientation >= 135 && orientation <= 224) {
                    rotation = Surface.ROTATION_180;
                } else if (orientation >= 225 && orientation <= 314) {
                    rotation = Surface.ROTATION_90;
                } else {
                    rotation = Surface.ROTATION_0;
                }
                imageAnalysis.setTargetRotation(rotation);
            }
        };
        orientationEventListener.enable();

        class ScanningListener implements ScanningResultListener {
            @Override
            public void onScanned(@NonNull final String result) {
                runOnUiThread(() -> {
                    imageAnalysis.clearAnalyzer();
                    if (cameraProvider != null) {
                        cameraProvider.unbindAll();
                    }
                    barCodeViewModel.newQRScanned(result);
                    ScannerResultDialog.newInstance(result, new ScannerResultDialog.DialogDismissListener() {
                        @Override
                        public void onDismiss() {
                            bindPreview(cameraProvider);
                        }
                    }).show(getSupportFragmentManager(), ScannerResultDialog.class.getSimpleName());
                });
            }
        }
        ImageAnalysis.Analyzer analyzer = new ZXingScanner(new ScanningListener());


        imageAnalysis.setAnalyzer(cameraExecutor, analyzer);
        preview.setSurfaceProvider(binding.cameraPreview.getSurfaceProvider());

        Camera camera = cameraProvider != null ? cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview) : null;

        if (camera != null && camera.getCameraInfo().hasFlashUnit()) {
            binding.ivFlashControl.setVisibility(View.VISIBLE);

            binding.ivFlashControl.setOnClickListener(v -> {
                camera.getCameraControl().enableTorch(!flashEnabled);
            });

            camera.getCameraInfo().getTorchState().observe(this, torchState -> {
                if (torchState != null) {
                    if (torchState == TorchState.ON) {
                        flashEnabled = true;
                        binding.ivFlashControl.setImageResource(R.drawable.ic_round_flash_on);
                    } else {
                        flashEnabled = false;
                        binding.ivFlashControl.setImageResource(R.drawable.ic_round_flash_off);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shut down our background executor
        cameraExecutor.shutdown();
    }
}
