package pwr.lab.expenses_management.ui.reports.monthly;

import android.app.AlertDialog;
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

import java.util.Calendar;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.ui.reports.categories_costs.CategoriesCostsAdapter;
import pwr.lab.expenses_management.ui.reports.yearly.YearlyReportActivity;
import pwr.lab.expenses_management.view_model.MonthlyReportViewModel;

public class MonthlyReportActivity extends AppCompatActivity {

    private MonthlyReportViewModel viewModel;
    private EditText yearPicker;
    private EditText monthPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MonthlyReportViewModel.class);

        setContentView(R.layout.activity_monthly_report);

        Button redirectYearlyReport = findViewById(R.id.yearly_report);
        yearPicker = findViewById(R.id.year);
        monthPicker = findViewById(R.id.month);
        RecyclerView recyclerView = findViewById(R.id.categories_costs);
        TextView totalCost = findViewById(R.id.total_cost);
        Toolbar toolbar = findViewById(R.id.navigation);
        CategoriesCostsAdapter adapter = new CategoriesCostsAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        redirectYearlyReport.setOnClickListener(l -> {
            startActivity(new Intent(MonthlyReportActivity.this, YearlyReportActivity.class));
        });

        yearPicker.setOnClickListener(l -> handleYearPicker());
        monthPicker.setOnClickListener(l -> handleMonthPicker());

        viewModel.getForm().observe(this, form -> {

            viewModel.update();

            if(form.getYear() != 0){
                yearPicker.setText(String.valueOf(form.getYear()));
            }

            if(form.getMonth() != 0){
                monthPicker.setText(String.valueOf(form.getMonth()));
            }
        });

        viewModel.getMonthlyReport().observe(this, monthlyReport -> {
            adapter.update();
            totalCost.setText("Łącznie: " + (int) viewModel.getTotalCost() + " zł");
        });

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
            viewModel.setYear(Integer.valueOf(selectedYear));
        });

        builder.show();
    }

    private void handleMonthPicker(){

        String[] months = {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Wybierz miesiąc");

        builder.setItems(months, (dialog, which) -> {
            String selectedMonth = months[which];
            viewModel.setMonth(which + 1);
        });

        builder.show();
    }
}
