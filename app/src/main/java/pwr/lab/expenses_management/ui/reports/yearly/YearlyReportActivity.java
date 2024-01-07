package pwr.lab.expenses_management.ui.reports.yearly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.ui.reports.monthly.MonthlyReportActivity;

public class YearlyReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_yearly_report);

        Button redirectMonthlyReport = findViewById(R.id.monthly_report);
        Toolbar toolbar = findViewById(R.id.navigation);

        redirectMonthlyReport.setOnClickListener(l -> {
            startActivity(new Intent(YearlyReportActivity.this, MonthlyReportActivity.class));
        });

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
