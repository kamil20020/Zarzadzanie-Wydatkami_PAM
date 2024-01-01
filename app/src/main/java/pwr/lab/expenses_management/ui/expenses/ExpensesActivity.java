package pwr.lab.expenses_management.ui.expenses;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.view_model.ExpensesViewModel;
import pwr.lab.expenses_management.view_model.ProductsCategoriesViewModel;

public class ExpensesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExpensesViewModel viewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);

        setContentView(R.layout.activity_expenses);

        Toolbar toolbar = findViewById(R.id.navigation);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        viewModel.getAllDetailedExpenses().observe(this, detailedExpenses -> {
            System.out.println(detailedExpenses);
        });
    }
}
