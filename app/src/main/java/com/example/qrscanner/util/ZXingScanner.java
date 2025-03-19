package com.example.qrscanner.util;
import android.graphics.ImageFormat;
import android.util.Log;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

public class ZXingScanner implements ImageAnalysis.Analyzer {
    private final MultiFormatReader multiFormatReader = new MultiFormatReader();
    private final AtomicBoolean isScanning = new AtomicBoolean(false);
    private final ScanningResultListener listener;

    public ZXingScanner(ScanningResultListener listener) {
        this.listener = listener;
    }


    @Override
    public void analyze(ImageProxy image) {
        if (isScanning.get()) {
            image.close();
            return;
        }
        isScanning.set(true);

        if ((image.getFormat() == ImageFormat.YUV_420_888 || image.getFormat() == ImageFormat.YUV_422_888 || image.getFormat() == ImageFormat.YUV_444_888) && image.getPlanes().length == 3) {
            RotatedImage rotatedImage = new RotatedImage(getLuminancePlaneData(image), image.getWidth(), image.getHeight());
            rotateImageArray(rotatedImage, image.getImageInfo().getRotationDegrees());

            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
                    rotatedImage.byteArray,
                    rotatedImage.width,
                    rotatedImage.height,
                    0, 0,
                    rotatedImage.width,
                    rotatedImage.height,
                    false
            );
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                Result rawResult = multiFormatReader.decodeWithState(binaryBitmap);
                Log.d("Barcode:", rawResult.getText());


                listener.onScanned(rawResult.getText());
            } catch (NotFoundException e) {
                e.printStackTrace();
            } finally {
                multiFormatReader.reset();
                image.close();
                isScanning.set(false);
            }
        }
    }

    private void rotateImageArray(RotatedImage imageToRotate, int rotationDegrees) {
        if (rotationDegrees == 0 || rotationDegrees % 90 != 0) return;

        int width = imageToRotate.width;
        int height = imageToRotate.height;
        byte[] rotatedData = new byte[imageToRotate.byteArray.length];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (rotationDegrees) {
                    case 90:
                        rotatedData[x * height + height - y - 1] = imageToRotate.byteArray[x + y * width];
                        break;
                    case 180:
                        rotatedData[width * (height - y - 1) + width - x - 1] = imageToRotate.byteArray[x + y * width];
                        break;
                    case 270:
                        rotatedData[y + x * height] = imageToRotate.byteArray[y * width + width - x - 1];
                        break;
                }
            }
        }

        imageToRotate.byteArray = rotatedData;
        if (rotationDegrees != 180) {
            imageToRotate.height = width;
            imageToRotate.width = height;
        }
    }

    private byte[] getLuminancePlaneData(ImageProxy image) {
        ImageProxy.PlaneProxy plane = image.getPlanes()[0];
        ByteBuffer buffer = plane.getBuffer();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        buffer.rewind();
        int width = image.getWidth();
        int height = image.getHeight();
        int rowStride = plane.getRowStride();
        int pixelStride = plane.getPixelStride();

        byte[] cleanData = new byte[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cleanData[y * width + x] = data[y * rowStride + x * pixelStride];
            }
        }
        return cleanData;
    }

    private static class RotatedImage {
        byte[] byteArray;
        int width;
        int height;

        RotatedImage(byte[] byteArray, int width, int height) {
            this.byteArray = byteArray;
            this.width = width;
            this.height = height;
        }
    }
}
