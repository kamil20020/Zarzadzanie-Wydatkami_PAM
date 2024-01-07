package pwr.lab.expenses_management.ui.reports.monthly;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.ui.product_categories.ProductsCategoriesAdapter;
import pwr.lab.expenses_management.ui.reports.yearly.YearlyReportActivity;
import pwr.lab.expenses_management.view_model.MonthlyReportViewModel;

public class MonthlyReportActivity extends AppCompatActivity {

    private EditText yearPicker;
    private EditText monthPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MonthlyReportViewModel viewModel = new ViewModelProvider(this).get(MonthlyReportViewModel.class);

        setContentView(R.layout.activity_monthly_report);

        Button redirectYearlyReport = findViewById(R.id.yearly_report);
        yearPicker = findViewById(R.id.year);
        monthPicker = findViewById(R.id.month);
        RecyclerView recyclerView = findViewById(R.id.categories_costs);
        Toolbar toolbar = findViewById(R.id.navigation);

        MonthlyReportAdapter adapter = new MonthlyReportAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        redirectYearlyReport.setOnClickListener(l -> {
            startActivity(new Intent(MonthlyReportActivity.this, YearlyReportActivity.class));
        });

        yearPicker.setOnClickListener(l -> handleYearPicker());
        monthPicker.setOnClickListener(l -> handleMonthPicker());

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void handleYearPicker(){

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        int startYear = 2020;

        String[] years = new String[currentYear - startYear + 1];

        for(int i = 0; i < years.length; i++){
            years[i] = String.valueOf(startYear + i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Wybierz rok");

        builder.setItems(years, (dialog, which) -> {
            String selectedYear = years[which];
            yearPicker.setText(selectedYear);
        });

        builder.show();
    }

    private void handleMonthPicker(){

        String[] months = {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Wybierz miesiąc");

        builder.setItems(months, (dialog, which) -> {
            String selectedMonth = months[which];
            monthPicker.setText(selectedMonth);
        });

        builder.show();
    }
}
