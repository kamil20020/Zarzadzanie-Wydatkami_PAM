package pwr.lab.expenses_management.ui.reports.yearly;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.ui.reports.monthly.MonthlyReportActivity;
import pwr.lab.expenses_management.ui.reports.categories_costs.CategoriesCostsAdapter;
import pwr.lab.expenses_management.view_model.YearlyReportViewModel;

public class YearlyReportActivity extends AppCompatActivity {

    private YearlyReportViewModel viewModel;

    private EditText yearPicker;

    private BarChart yearChart;

    private static final List<String> months = List.of(
        "Styczeń", "Luty", "Marzec", "Kwiecień",
        "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień",
        "Październik", "Listopad", "Grudzień"
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(YearlyReportViewModel.class);

        setContentView(R.layout.activity_yearly_report);

        Button redirectMonthlyReport = findViewById(R.id.monthly_report);
        yearPicker = findViewById(R.id.year);
        TextView totalCost = findViewById(R.id.total_cost);
        yearChart = findViewById(R.id.year_chart);
        RecyclerView recyclerView = findViewById(R.id.categories_costs);
        Toolbar toolbar = findViewById(R.id.navigation);

        yearChart.getDescription().setEnabled(false);
        yearChart.getAxisRight().setDrawLabels(false);
        yearChart.getLegend().setTextSize(18);
        yearChart.setTouchEnabled(false);

        yearChart.getAxisLeft().setTextSize(16);
        yearChart.getAxisLeft().setGranularity(1f);
        yearChart.getAxisLeft().setGranularityEnabled(true);

        yearChart.getXAxis().setTextSize(16);
        yearChart.getXAxis().setGranularity(1f);
        yearChart.getXAxis().setGranularityEnabled(true);
        yearChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        yearChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months));

        CategoriesCostsAdapter adapter = new CategoriesCostsAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        redirectMonthlyReport.setOnClickListener(l -> {
            startActivity(new Intent(YearlyReportActivity.this, MonthlyReportActivity.class));
        });

        yearPicker.setOnClickListener(l -> handleYearPicker());

        viewModel.getYear().observe(this, year -> {

            viewModel.update();

            if(year != 0){
                yearPicker.setText(String.valueOf(year));
            }
        });

        viewModel.getYearSummary().observe(this, summary -> loadYearChart(summary));

        viewModel.getMonthlyReport().observe(this, monthlyReport -> {
            adapter.update();
            totalCost.setText("Łącznie: " + (int) viewModel.getTotalCost() + " zł");
        });

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void loadYearChart(List<Double> summary){

        AtomicInteger i = new AtomicInteger(1);

        List<BarEntry> entries = summary.stream()
            .map(s -> new BarEntry(i.getAndIncrement(), s.floatValue()))
            .collect(Collectors.toList());

        BarDataSet dataSet = new BarDataSet(entries, "Miesiące");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(18);
        dataSet.setValueFormatter(new DefaultValueFormatter(0));

        BarData data = new BarData(dataSet);
        yearChart.setData(data);
        yearChart.invalidate();
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
}
