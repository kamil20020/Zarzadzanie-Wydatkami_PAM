package pwr.lab.expenses_management.ui.expenses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.DetailedExpense;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.view_model.ExpensesViewModel;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesViewHolder> {

    private final List<DetailedExpense> detailedExpenses = new ArrayList<>();
    private final ExpensesViewModel viewModel;

    public ExpensesAdapter(ExpensesViewModel expensesViewModel){
        viewModel = expensesViewModel;
    }

    public void setExpenses(List<DetailedExpense> detailedExpenses){
        this.detailedExpenses.clear();
        this.detailedExpenses.addAll(detailedExpenses);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_row_item, parent, false);

        return new ExpensesViewHolder(view, parent.getContext(), viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesViewHolder holder, int position) {

        DetailedExpense currentDetailedExpense = detailedExpenses.get(position);
        ExpenseEntity currentExpense = currentDetailedExpense.getExpenseEntity();
        holder.getExpenseNameTextView().setText(currentExpense.getTitle());
        holder.getExpenseDateTextView().setText(currentExpense.getDate());

        Double totalPrice = Double.valueOf(currentExpense.getTotalPrice()) / 100;

        holder.getExpenseTotalPriceTextView().setText(totalPrice.toString() + " zł");
    }

    @Override
    public int getItemCount() {
        return detailedExpenses.size();
    }
}
