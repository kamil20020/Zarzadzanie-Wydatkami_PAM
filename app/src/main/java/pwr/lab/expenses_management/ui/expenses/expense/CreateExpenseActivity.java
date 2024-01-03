package pwr.lab.expenses_management.ui.expenses.expense;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.ui.TextChangedListener;
import pwr.lab.expenses_management.view_model.CreateExpenseViewModel;
import pwr.lab.expenses_management.view_model.ExpensesViewModel;

public class CreateExpenseActivity extends AppCompatActivity {

    private CreateExpenseViewModel viewModel;

    private CreateExpenseViewModel.Form form;

    private static final int CAMERA_REQUEST = 4;
    private final static String REQUIRED_MESSAGE = "Pole wymagane";
    private ImageButton loadReceiptButton;

    private EditText expenseNameInput;
    private EditText expenseDateInput;

    private TextView totalCostView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CreateExpenseViewModel.class);

        setContentView(R.layout.activity_create_expense);

        Toolbar toolbar = findViewById(R.id.navigation);
        loadReceiptButton = findViewById(R.id.load_photo);
        expenseNameInput = findViewById(R.id.expense_name);
        expenseDateInput = findViewById(R.id.expense_date);
        totalCostView = findViewById(R.id.total_cost);
        Button saveButton = findViewById(R.id.save);

        form = viewModel.getForm();
        expenseNameInput.setText(form.getTitle());
        expenseDateInput.setText(form.getDate().toString());
        totalCostView.setText("Razem: " + form.getTotalPrice().toString() + " zł");

        expenseNameInput.addTextChangedListener(new TextChangedListener<>(expenseNameInput) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                target.setError(null);
                form.setTitle(target.getText().toString());
            }
        });

        expenseDateInput.setOnClickListener(l -> handleInputDate());

        viewModel.getFormErrors().observe(this, errors -> {

            if(errors.contains(CreateExpenseViewModel.FormErrors.NAME.ordinal())){
                expenseNameInput.setError(REQUIRED_MESSAGE);
            }

            if(errors.contains(CreateExpenseViewModel.FormErrors.DATE.ordinal())){
                expenseDateInput.setError(REQUIRED_MESSAGE);
            }
        });

        loadReceiptButton.setOnClickListener(l -> handleLoadPhoto());
        saveButton.setOnClickListener(l -> createExpense());

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void handleLoadPhoto(){

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
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

    private void handleInputDate(){

        final Calendar c = Calendar.getInstance();
        int initialYear = c.get(Calendar.YEAR);
        int initialMonth = c.get(Calendar.MONTH);
        int initialDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(
            this,
            (view, year, month, day) -> {
                LocalDate localDate = LocalDate.of(year, month + 1, day);
                expenseDateInput.setText(localDate.toString());
                expenseDateInput.setError(null);
                form.setDate(localDate);
            },
            initialYear,
            initialMonth,
            initialDay
        );

        datePicker.show();
    }

    private void createExpense(){

        if(!viewModel.validateForm()){
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {

            Looper.prepare();

            try {
                viewModel.createExpense();
                finish();
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            Looper.loop();
        });
    }
}
