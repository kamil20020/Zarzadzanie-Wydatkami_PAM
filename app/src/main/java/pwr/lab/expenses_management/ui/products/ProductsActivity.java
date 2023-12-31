package pwr.lab.expenses_management.ui.products;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.view_model.ProductsViewModel;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProductsViewModel viewModel = new ViewModelProvider(this).get(ProductsViewModel.class);

        setContentView(R.layout.activity_products);

        RecyclerView recyclerView = findViewById(R.id.products_recycler_view);
        Toolbar toolbar = findViewById(R.id.navigation);

        ProductsAdapter adapter = new ProductsAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        setSupportActionBar(toolbar);

        viewModel.getAll().observe(this, products -> {
            adapter.update();
        });

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
