package pwr.lab.expenses_management.ui.expenses;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;
import pwr.lab.expenses_management.R;
@Getter
public class ExpensesViewHolder extends RecyclerView.ViewHolder {

    private final TextView expenseNameTextView;
    private final TextView expenseDateTextView;
    private final TextView expenseTotalPriceTextView;

    private final Button removeButton;

    public ExpensesViewHolder(@NonNull View itemView) {
        super(itemView);

        expenseNameTextView = itemView.findViewById(R.id.expense_name);
        expenseDateTextView = itemView.findViewById(R.id.expense_date);
        expenseTotalPriceTextView = itemView.findViewById(R.id.expense_total_price);
        removeButton = itemView.findViewById(R.id.remove_expense);
    }
}
