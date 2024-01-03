package pwr.lab.expenses_management.ui.expenses;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import pwr.lab.expenses_management.R;

public class CreateExpenseActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 4;
    private ImageButton loadReceiptButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_expense);

        Toolbar toolbar = findViewById(R.id.navigation);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        loadReceiptButton = findViewById(R.id.load_photo);
        loadReceiptButton.setOnClickListener(l -> handleLoadPhoto());
    }

    private boolean hasCameraPermission(){

        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(
            this,
            new String[]{Manifest.permission.CAMERA},
            4
        );
    }

    private void handleLoadPhoto(){

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

        /*if(hasCameraPermission()){
            new LoadReceiptDialogFragment(this).show(getSupportFragmentManager(), "LoadReceiptDialog");
        }
        else{
            requestCameraPermission();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photo = Bitmap.createScaledBitmap(photo, 400, 500, false);
            loadReceiptButton.setImageBitmap(photo);
        }
    }
}
