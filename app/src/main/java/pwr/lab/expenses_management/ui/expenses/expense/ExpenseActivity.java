package pwr.lab.expenses_management.ui.expenses.expense;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import pwr.lab.expenses_management.R;

public class ExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expense);

        Toolbar toolbar = findViewById(R.id.navigation);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
