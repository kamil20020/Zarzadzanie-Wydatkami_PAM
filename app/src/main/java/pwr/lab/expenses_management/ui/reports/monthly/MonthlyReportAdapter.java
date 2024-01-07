package pwr.lab.expenses_management.ui.reports.monthly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pwr.lab.expenses_management.R;

public class MonthlyReportAdapter extends RecyclerView.Adapter<MonthlyReportViewHolder> {

    @NonNull
    @Override
    public MonthlyReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_category_row_item, parent, false);

        return new MonthlyReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlyReportViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
