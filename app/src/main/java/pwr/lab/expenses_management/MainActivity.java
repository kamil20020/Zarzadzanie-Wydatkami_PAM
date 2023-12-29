package pwr.lab.expenses_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.repository.ProductCategoryRepository;

public class MainActivity extends AppCompatActivity {

    private AppDatabase appDatabase;
    private ProductCategoryRepository productCategoryRepository;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appDatabase = AppDatabase.getDatabase(this);
        productCategoryRepository = ProductCategoryRepository.getInstance(appDatabase.productCategoryDAO());

        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn);

        button.setOnClickListener((l) -> {
            AsyncTask.execute(() -> {
                System.out.println(productCategoryRepository.getAll());
            });
        });
    }
}