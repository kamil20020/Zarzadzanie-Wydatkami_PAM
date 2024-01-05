package pwr.lab.expenses_management.ui.product_categories;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.view_model.ProductsCategoriesViewModel;

public class ProductsCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProductsCategoriesViewModel viewModel = new ViewModelProvider(this).get(ProductsCategoriesViewModel.class);

        setContentView(R.layout.activity_products_categories);

        RecyclerView recyclerView = findViewById(R.id.products_categories_recycler_view);
        Toolbar toolbar = findViewById(R.id.navigation);

        ProductsCategoriesAdapter adapter = new ProductsCategoriesAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        setSupportActionBar(toolbar);

        viewModel.getAllCategories().observe(this, categories -> {
            adapter.update();
        });

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
