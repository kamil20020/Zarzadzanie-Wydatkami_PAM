package pwr.lab.expenses_management.ui.reports.monthly;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;
import pwr.lab.expenses_management.R;

@Getter
public class MonthlyReportViewHolder extends RecyclerView.ViewHolder {

    private TextView categoryView;
    private TextView costView;
    private ProgressBar progressBar;
    private Button showDetailsButton;

    public MonthlyReportViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryView = itemView.findViewById(R.id.category);
        costView = itemView.findViewById(R.id.category_cost);
        progressBar = itemView.findViewById(R.id.progress_bar);
        showDetailsButton = itemView.findViewById(R.id.show_details);
    }
}
