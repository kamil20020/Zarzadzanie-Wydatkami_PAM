package pwr.lab.expenses_management.ui.expenses;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.Executors;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.DetailedExpense;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.ui.expenses.expense.ExpenseActivity;
import pwr.lab.expenses_management.view_model.ExpensesViewModel;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesViewHolder> {

    private final ExpensesViewModel viewModel;
    private Context context;

    public ExpensesAdapter(ExpensesViewModel expensesViewModel){
        viewModel = expensesViewModel;
    }

    public void update(){
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_row_item, parent, false);

        context = parent.getContext();

        return new ExpensesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesViewHolder holder, int position) {

        DetailedExpense currentDetailedExpense = viewModel.get(position);
        ExpenseEntity currentExpense = currentDetailedExpense.getExpenseEntity();
        holder.getExpenseNameTextView().setText(currentExpense.getTitle());
        holder.getExpenseDateTextView().setText(currentExpense.getDate());

        Double totalPrice = Double.valueOf(currentExpense.getTotalPrice()) / 100;

        holder.getExpenseTotalPriceTextView().setText(totalPrice + " zł");

        holder.itemView.setOnClickListener(l -> {

            int index = holder.getAdapterPosition();

            Intent intent = new Intent(context, ExpenseActivity.class);
            intent.putExtra("expense_id", viewModel.getId(index));

            context.startActivity(new Intent(context, ExpenseActivity.class));
        });

        holder.getRemoveButton().setOnClickListener(l -> {

            int index = holder.getAdapterPosition();

            Executors.newSingleThreadExecutor().execute(() -> {

                Looper.prepare();

                viewModel.delete(index);
                Toast.makeText(context, "Usunięto wydatek", Toast.LENGTH_SHORT).show();

                Looper.loop();
            });
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.size();
    }
}
