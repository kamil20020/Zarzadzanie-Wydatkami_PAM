package pwr.lab.expenses_management.ui.expenses.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.ui.expenses.ExpensesAdapter;
import pwr.lab.expenses_management.view_model.ExpenseViewModel;

public class ExpenseActivity extends AppCompatActivity {

    private ExpenseViewModel viewModel;

    private TextView expenseNameView;
    private TextView expenseDateView;
    private TextView expenseTotalPriceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String expenseIdStr = getIntent().getStringExtra("expense_id");
        Integer expenseId = Integer.valueOf(expenseIdStr);

        viewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        viewModel.loadExpense(expenseId);

        setContentView(R.layout.activity_expense);

        expenseNameView = findViewById(R.id.expense_name);
        expenseDateView = findViewById(R.id.expense_date);
        expenseTotalPriceView = findViewById(R.id.total_cost);
        RecyclerView recyclerView = findViewById(R.id.expense_products_recycler_view);
        Toolbar toolbar = findViewById(R.id.navigation);

        ExpenseProductsAdapter adapter = new ExpenseProductsAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        setSupportActionBar(toolbar);

        viewModel.getExpenseLiveData().observe(this, stronglyDetailedExpense -> {

            ExpenseEntity expense = stronglyDetailedExpense.getExpenseEntity();

            Long rawTotalPrice = expense.getTotalPrice();
            BigDecimal nonConvertedTotalPrice = BigDecimal.valueOf(rawTotalPrice);
            BigDecimal divider = BigDecimal.valueOf(100);
            BigDecimal totalPrice = nonConvertedTotalPrice.divide(divider);

            expenseNameView.setText("Nazwa: " + expense.getTitle());
            expenseDateView.setText("Data: " + expense.getDate());
            expenseTotalPriceView.setText("Razem: " + totalPrice.toString() + " zł");

            adapter.update();
        });

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
