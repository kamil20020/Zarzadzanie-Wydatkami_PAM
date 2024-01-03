package pwr.lab.expenses_management.ui.expenses;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.core.ViewPort;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import pwr.lab.expenses_management.R;

public class LoadReceiptDialogFragment extends DialogFragment {

    private ImageCapture imageCapture;
    private Activity activity;

    private PreviewView previewView;

    public LoadReceiptDialogFragment(Activity activity){
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return new AlertDialog.Builder(requireContext())
            .setTitle("Skanowanie paragonu")
            .setView(R.layout.camera_view)
            .setIcon(R.drawable.baseline_receipt_24)
            .setNegativeButton("Anuluj", (dialog, which) -> {})
            .setPositiveButton("Zapisz", (dialog, which) -> {})
            .create();
    }

    private void startCamera(){

        var cameraProviderFuture = ProcessCameraProvider.getInstance(activity);

        cameraProviderFuture.addListener(() -> {

            CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

            Preview preview = new Preview.Builder().build();
            preview.setSurfaceProvider(previewView.getSurfaceProvider());

            ViewPort viewPort = previewView.getViewPort();

            imageCapture = new ImageCapture.Builder().build();

            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                UseCaseGroup useCaseGroup = new UseCaseGroup.Builder()
                    .addUseCase(preview)
                    .addUseCase(imageCapture)
                    .setViewPort(viewPort)
                    .build();

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview);
            }
            catch(Exception e) {
                System.out.println("Use case binding failed");
            }

        }, ContextCompat.getMainExecutor(activity));
    }

    private void takePhoto(){

        imageCapture.takePicture(
            null,
            ContextCompat.getMainExecutor(activity),
            null
        );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startCamera();

        previewView = view.findViewById(R.id.camera_preview);

        Button capturePhotoButton = view.findViewById(R.id.capture_photo);
        capturePhotoButton.setOnClickListener(l -> {
            takePhoto();
        });
    }
}
