package pwr.lab.expenses_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Locale;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.ui.expenses.ExpensesActivity;
import pwr.lab.expenses_management.ui.product_categories.ProductsCategoriesActivity;
import pwr.lab.expenses_management.ui.products.ProductsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale.setDefault(new Locale("pl", "PL"));

        AppDatabase.getDatabase(this);

        setContentView(R.layout.activity_main);

        Button expensesButton = findViewById(R.id.expenses_button);
        Button productsButton = findViewById(R.id.products_button);
        Button categoriesButton = findViewById(R.id.categories_button);

        expensesButton.setOnClickListener(l -> {
            startActivity(new Intent(MainActivity.this, ExpensesActivity.class));
        });

        productsButton.setOnClickListener(l -> {
            startActivity(new Intent(MainActivity.this, ProductsActivity.class));
        });

        categoriesButton.setOnClickListener(l -> {
            startActivity(new Intent(MainActivity.this, ProductsCategoriesActivity.class));
        });
    }
}