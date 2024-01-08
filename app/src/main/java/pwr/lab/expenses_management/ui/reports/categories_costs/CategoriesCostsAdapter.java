package pwr.lab.expenses_management.ui.reports.categories_costs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.view_model.CategoriesCostsViewModel;
import pwr.lab.expenses_management.view_model.MonthlyReportViewModel;

public class CategoriesCostsAdapter extends RecyclerView.Adapter<CategoriesCostsReportViewHolder> {

    private CategoriesCostsViewModel viewModel;

    public CategoriesCostsAdapter(CategoriesCostsViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void update(){
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesCostsReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_category_row_item, parent, false);

        return new CategoriesCostsReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesCostsReportViewHolder holder, int position) {

        ExpenseDAO.CategoryCost row = viewModel.get(position);

        TextView categoryView = holder.getCategoryView();
        TextView categoryCostView = holder.getCostView();
        ProgressBar progressBar = holder.getProgressBar();

        double totalCost = viewModel.getTotalCost();
        double cost = row.getTotalCost();
        int percentage = (int) (cost / totalCost * 100);

        categoryView.setText(row.getCategory());
        categoryCostView.setText((int) cost + " zł (" + percentage + "%)");
        progressBar.setProgress(percentage);
    }

    @Override
    public int getItemCount() {
        return viewModel.size();
    }
}
