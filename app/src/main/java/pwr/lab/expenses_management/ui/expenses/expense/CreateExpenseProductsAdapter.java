package pwr.lab.expenses_management.ui.expenses.expense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.ExpenseProductEntity;
import pwr.lab.expenses_management.view_model.CreateExpenseProductsViewModel;

public class CreateExpenseProductsAdapter extends RecyclerView.Adapter<CreateExpenseProductsViewHolder> {

    private final CreateExpenseProductsViewModel viewModel;

    public CreateExpenseProductsAdapter(CreateExpenseProductsViewModel viewModel){
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public CreateExpenseProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_product_row_item, parent, false);

        return new CreateExpenseProductsViewHolder(view, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateExpenseProductsViewHolder holder, int position) {

        CreateExpenseProductsViewModel.Form form = viewModel.get(position);
        holder.getNameInput().setText(form.getName());
        holder.getCountInput().setText(form.getCount());
        holder.getPriceInput().setText(form.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return viewModel.getForm().size();
    }
}
