package pwr.lab.expenses_management.ui.product_categories;

import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.Executors;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.ui.TextChangedListener;
import pwr.lab.expenses_management.view_model.ProductsCategoriesViewModel;

public class ProductsCategoriesActivity extends AppCompatActivity {

    private Button showAddCategoryButton;
    private EditText categoryNameInput;
    private Button addCategoryButton;

    private ProductsCategoriesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProductsCategoriesViewModel.class);

        setContentView(R.layout.activity_products_categories);

        RecyclerView recyclerView = findViewById(R.id.products_categories_recycler_view);
        showAddCategoryButton = findViewById(R.id.show_add_category);
        categoryNameInput = findViewById(R.id.category_name);
        addCategoryButton = findViewById(R.id.add_category);
        Toolbar toolbar = findViewById(R.id.navigation);

        ProductsCategoriesAdapter adapter = new ProductsCategoriesAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        setSupportActionBar(toolbar);

        createCategoryLogic();

        viewModel.getAllCategories().observe(this, categories -> {
            adapter.update();
        });

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void setAddMode(){
        showAddCategoryButton.setVisibility(View.GONE);
        categoryNameInput.setVisibility(View.VISIBLE);
        addCategoryButton.setVisibility(View.VISIBLE);
    }

    private void createCategoryLogic(){

        if(viewModel.getIsAddModeEnabled()){
            setAddMode();
        }

        categoryNameInput.setText(viewModel.getCategoryName());

        showAddCategoryButton.setOnClickListener(l -> {
            setAddMode();
            viewModel.switchAddMode();
        });
        categoryNameInput.addTextChangedListener(new TextChangedListener<>(categoryNameInput){

            @Override
            public void onTextChanged(EditText target, Editable s) {
                viewModel.setCategoryName(target.getText().toString());
            }
        });

        addCategoryButton.setOnClickListener(l -> {

            String categoryName = categoryNameInput.getText().toString();

            if(categoryName.isBlank()){
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {

                try {
                    viewModel.create();
                }
                catch (IllegalArgumentException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                runOnUiThread(() -> {

                    Toast.makeText(this, "Utworzono kategorię", Toast.LENGTH_SHORT).show();

                    categoryNameInput.setText("");
                    categoryNameInput.setVisibility(View.GONE);
                    addCategoryButton.setVisibility(View.GONE);
                    showAddCategoryButton.setVisibility(View.VISIBLE);
                });
            });
        });
    }
}
