package pwr.lab.expenses_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.ui.product_categories.ProductsCategoriesActivity;
import pwr.lab.expenses_management.ui.products.ProductsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDatabase.getDatabase(this);

        setContentView(R.layout.activity_main);

        Button productsButton = findViewById(R.id.products_button);
        productsButton.setOnClickListener(l -> {
            startActivity(new Intent(MainActivity.this, ProductsActivity.class));
        });

        Button categoriesButton = findViewById(R.id.categories_button);
        categoriesButton.setOnClickListener(l -> {
            startActivity(new Intent(MainActivity.this, ProductsCategoriesActivity.class));
        });
    }
}