package pwr.lab.expenses_management.ui.expenses;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pwr.lab.expenses_management.MainActivity;
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

        Button createExpenseButton = findViewById(R.id.create_expense);
        createExpenseButton.setOnClickListener(l -> {
            startActivity(new Intent(ExpensesActivity.this, CreateExpenseActivity.class));
        });

        RecyclerView recyclerView = findViewById(R.id.expenses_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        ExpensesAdapter adapter = new ExpensesAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        viewModel.getAllDetailedExpenses().observe(this, detailedExpenses -> {
            adapter.setExpenses(detailedExpenses);
        });
    }
}
