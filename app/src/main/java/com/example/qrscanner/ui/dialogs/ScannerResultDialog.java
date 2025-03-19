package com.example.qrscanner.ui.dialogs;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.qrscanner.databinding.FragmentScannerResultDialogListDialogBinding;
import com.example.qrscanner.util.ClipboardUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ScannerResultDialog extends BottomSheetDialogFragment {

    private FragmentScannerResultDialogListDialogBinding binding;
    private DialogDismissListener listener;

    private static final String ARG_SCANNING_RESULT = "ARG_SCANNING_RESULT";

    public ScannerResultDialog(DialogDismissListener listener) {
        this.listener = listener;
    }

    public static ScannerResultDialog newInstance(String scanningResult, DialogDismissListener listener) {
        ScannerResultDialog dialog = new ScannerResultDialog(listener);
        Bundle args = new Bundle();
        args.putString(ARG_SCANNING_RESULT, scanningResult);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScannerResultDialogListDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String scannedResult = getArguments() != null ? getArguments().getString(ARG_SCANNING_RESULT) : "";
        binding.edtResult.setText(scannedResult);
        binding.btnCopy.setOnClickListener(v -> {
            ClipboardUtils.copyToClipboard(this.requireContext(), scannedResult);
            dismissAllowingStateLoss();
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        listener.onDismiss();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        listener.onDismiss();
    }

    public interface DialogDismissListener {
        void onDismiss();
    }
}
