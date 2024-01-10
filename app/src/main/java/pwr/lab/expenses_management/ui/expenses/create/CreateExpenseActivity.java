package pwr.lab.expenses_management.ui.expenses.create;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.ui.TextChangedListener;
import pwr.lab.expenses_management.view_model.CreateExpenseProductsViewModel;
import pwr.lab.expenses_management.view_model.CreateExpenseViewModel;

public class CreateExpenseActivity extends AppCompatActivity {

    private CreateExpenseViewModel createExpenseViewModel;
    private CreateExpenseProductsViewModel createExpenseProductsViewModel;

    private CreateExpenseViewModel.Form createExpenseForm;

    private static final int CAMERA_REQUEST = 4;
    private final static String REQUIRED_MESSAGE = "Pole wymagane";
    private ImageButton loadReceiptButton;

    private EditText expenseNameInput;
    private EditText expenseDateInput;

    private TextView totalCostView;

    private CreateExpenseProductsAdapter expenseProductsAdapter;

    private static final int ALL_PERMISSION_CODE = 303;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createExpenseProductsViewModel = new ViewModelProvider(this).get(CreateExpenseProductsViewModel.class);
        createExpenseViewModel = new ViewModelProvider(this).get(CreateExpenseViewModel.class);
        createExpenseViewModel.setCreateExpenseProductsViewModel(createExpenseProductsViewModel);

        setContentView(R.layout.activity_create_expense);

        loadReceiptButton = findViewById(R.id.load_photo);
        expenseNameInput = findViewById(R.id.expense_name);
        expenseDateInput = findViewById(R.id.expense_date);
        totalCostView = findViewById(R.id.total_cost);
        Button saveButton = findViewById(R.id.save);
        Button createEmptyExpenseProduct = findViewById(R.id.create_empty_expense_product);
        RecyclerView expenseProductsRecyclerView = findViewById(R.id.expense_products_recycler_view);
        Toolbar toolbar = findViewById(R.id.navigation);

        createExpenseForm = createExpenseViewModel.getForm();

        expenseNameInput.setText(createExpenseForm.getTitle());
        expenseDateInput.setText(createExpenseForm.getDate().toString());
        initTotalPrice();

        expenseProductsAdapter = new CreateExpenseProductsAdapter(createExpenseProductsViewModel);
        expenseProductsRecyclerView.setAdapter(expenseProductsAdapter);
        expenseProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        setSupportActionBar(toolbar);

        expenseDateInput.setOnClickListener(l -> handleInputDate());
        loadReceiptButton.setOnClickListener(l -> checkPermissions());
        saveButton.setOnClickListener(l -> save());
        createEmptyExpenseProduct.setOnClickListener(l -> createEmptyExpenseProduct());

        expenseNameInput.addTextChangedListener(new TextChangedListener<>(expenseNameInput) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                target.setError(null);
                createExpenseForm.setTitle(target.getText().toString());
            }
        });

        loadExpenseFormErrors();

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void createEmptyExpenseProduct(){
        createExpenseProductsViewModel.createEmptyForm();
        expenseProductsAdapter.notifyLastAdded();
    }

    private void initTotalPrice(){
        createExpenseProductsViewModel.getTotalPrice().observe(this, totalPrice -> {
            String formattedTotalPrice = String.format("%.2f", totalPrice);
            totalCostView.setText("Razem: " + formattedTotalPrice + " zł");
        });
    }

    private void loadExpenseFormErrors(){

        createExpenseViewModel.getFormErrors().observe(this, errors -> {

            if(errors.contains(CreateExpenseViewModel.FormErrors.NAME.ordinal())){
                expenseNameInput.setError(REQUIRED_MESSAGE);
            }

            if(errors.contains(CreateExpenseViewModel.FormErrors.DATE.ordinal())){
                expenseDateInput.setError(REQUIRED_MESSAGE);
            }
        });
    }

    public void checkPermissions() {

        String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        boolean hasPermissions = true;

        for(String permission : permissions){

            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED)  {
                hasPermissions = false;
            }
        }

        if(!hasPermissions){
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSION_CODE);
        }
        else{
            handleLoadPhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ALL_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleLoadPhoto();
            }
        }
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

            try {
                createExpenseViewModel.loadDataFromRecipe(this, photo);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

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
                createExpenseForm.setDate(localDate);
            },
            initialYear,
            initialMonth,
            initialDay
        );

        datePicker.show();
    }

    private void save(){

        if(!createExpenseViewModel.validateForm()){
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {

            Looper.prepare();

            try {
                createExpenseViewModel.createExpense();
                finish();
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            Looper.loop();
        });
    }
}
