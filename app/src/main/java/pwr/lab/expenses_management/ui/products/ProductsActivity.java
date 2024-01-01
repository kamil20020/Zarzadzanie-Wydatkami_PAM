package pwr.lab.expenses_management.ui.products;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.ProductEntity;
import pwr.lab.expenses_management.view_model.ProductsViewModel;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProductsViewModel viewModel = new ViewModelProvider(this).get(ProductsViewModel.class);

        setContentView(R.layout.activity_products);

        Toolbar toolbar = findViewById(R.id.navigation);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        RecyclerView recyclerView = findViewById(R.id.products_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        ProductsAdapter adapter = new ProductsAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getAllProducts().observe(this, products -> {
            adapter.setProducts(products);
        });
    }
}
