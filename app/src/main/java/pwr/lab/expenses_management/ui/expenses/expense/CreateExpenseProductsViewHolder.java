package pwr.lab.expenses_management.ui.expenses.expense;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;

import lombok.Getter;
import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.ui.TextChangedListener;
import pwr.lab.expenses_management.view_model.CreateExpenseProductsViewModel;

@Getter
public class CreateExpenseProductsViewHolder extends RecyclerView.ViewHolder {

    private final EditText nameInput;
    private final EditText countInput;
    private final EditText priceInput;

    private final Button deleteButton;

    private boolean isTouched = false;

    public CreateExpenseProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        nameInput = itemView.findViewById(R.id.expense_product_name);
        countInput = itemView.findViewById(R.id.expense_product_count);
        priceInput = itemView.findViewById(R.id.expense_product_price);
        deleteButton = itemView.findViewById(R.id.remove_expense_product);
    }

    public void setIsTouched(){
        isTouched = true;
    }
}
