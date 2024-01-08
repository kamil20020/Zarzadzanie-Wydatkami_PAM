package pwr.lab.expenses_management.ui.reports.yearly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.ui.reports.monthly.MonthlyReportActivity;
import pwr.lab.expenses_management.ui.reports.categories_costs.CategoriesCostsAdapter;
import pwr.lab.expenses_management.view_model.YearlyReportViewModel;

public class YearlyReportActivity extends AppCompatActivity {

    private YearlyReportViewModel viewModel;

    private EditText yearPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(YearlyReportViewModel.class);

        setContentView(R.layout.activity_yearly_report);

        Button redirectMonthlyReport = findViewById(R.id.monthly_report);
        yearPicker = findViewById(R.id.year);
        RecyclerView recyclerView = findViewById(R.id.categories_costs);
        TextView totalCost = findViewById(R.id.total_cost);
        Toolbar toolbar = findViewById(R.id.navigation);
        CategoriesCostsAdapter adapter = new CategoriesCostsAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        redirectMonthlyReport.setOnClickListener(l -> {
            startActivity(new Intent(YearlyReportActivity.this, MonthlyReportActivity.class));
        });

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
