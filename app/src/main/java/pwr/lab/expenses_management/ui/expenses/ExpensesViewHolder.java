package pwr.lab.expenses_management.ui.expenses;

import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.Executors;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.view_model.ExpensesViewModel;

public class ExpensesViewHolder extends RecyclerView.ViewHolder {

    private final TextView expenseNameTextView;
    private final TextView expenseDateTextView;
    private final TextView expenseTotalPriceTextView;

    public ExpensesViewHolder(@NonNull View itemView, Context context, ExpensesViewModel viewModel) {
        super(itemView);

        expenseNameTextView = itemView.findViewById(R.id.expense_name);
        expenseDateTextView = itemView.findViewById(R.id.expense_date);
        expenseTotalPriceTextView = itemView.findViewById(R.id.expense_total_price);
        Button removeButton = itemView.findViewById(R.id.remove_expense);

        removeButton.setOnClickListener(l -> {

            int index = getAdapterPosition();

            Executors.newSingleThreadExecutor().execute(() -> {

                Looper.prepare();

                viewModel.delete(index);
                Toast.makeText(context, "Usunięto wydatek", Toast.LENGTH_SHORT).show();

                Looper.loop();
            });
        });
    }
}
