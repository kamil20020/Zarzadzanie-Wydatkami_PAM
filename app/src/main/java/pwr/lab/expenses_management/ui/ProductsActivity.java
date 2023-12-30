package pwr.lab.expenses_management.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.view_model.ProductsViewModel;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProductsViewModel viewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        viewModel.getAllProducts().observe(this, products -> {
            System.out.println(products);
        });

        setContentView(R.layout.activity_products);

        Toolbar toolbar = findViewById(R.id.navigation);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
