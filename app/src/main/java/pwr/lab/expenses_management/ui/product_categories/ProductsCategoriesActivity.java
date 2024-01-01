package pwr.lab.expenses_management.ui.product_categories;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;
import pwr.lab.expenses_management.ui.products.ProductsAdapter;
import pwr.lab.expenses_management.view_model.ProductsCategoriesViewModel;
import pwr.lab.expenses_management.view_model.ProductsViewModel;

public class ProductsCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProductsCategoriesViewModel viewModel = new ViewModelProvider(this).get(ProductsCategoriesViewModel.class);

        setContentView(R.layout.activity_products_categories);

        Toolbar toolbar = findViewById(R.id.navigation);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        RecyclerView recyclerView = findViewById(R.id.products_categories_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        ProductsCategoriesAdapter adapter = new ProductsCategoriesAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        viewModel.getAllCategories().observe(this, categories -> {
            adapter.setCategories(categories);
        });
    }
}
