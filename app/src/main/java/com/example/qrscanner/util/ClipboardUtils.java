package com.example.qrscanner.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class ClipboardUtils {
    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = ContextCompat.getSystemService(context, ClipboardManager.class);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("label", text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }
}