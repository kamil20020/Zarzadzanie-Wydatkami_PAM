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

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.ui.expenses.expense.CreateExpenseActivity;
import pwr.lab.expenses_management.view_model.ExpensesViewModel;

public class ExpensesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExpensesViewModel viewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);

        setContentView(R.layout.activity_expenses);

        Button createExpenseButton = findViewById(R.id.create_expense);
        RecyclerView recyclerView = findViewById(R.id.expenses_recycler_view);
        Toolbar toolbar = findViewById(R.id.navigation);

        ExpensesAdapter adapter = new ExpensesAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        setSupportActionBar(toolbar);

        createExpenseButton.setOnClickListener(l -> {
            startActivity(new Intent(ExpensesActivity.this, CreateExpenseActivity.class));
        });

        viewModel.getAllDetailedExpenses().observe(this, detailedExpenses -> {
            adapter.update();
            System.out.println(detailedExpenses);
        });

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
