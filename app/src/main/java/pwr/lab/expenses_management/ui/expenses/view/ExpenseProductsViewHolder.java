package pwr.lab.expenses_management.ui.expenses.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;
import pwr.lab.expenses_management.R;

@Getter
public class ExpenseProductsViewHolder extends RecyclerView.ViewHolder {

    private final EditText nameInput;
    private final EditText countInput;
    private final EditText priceInput;

    private final Button deleteButton;

    private boolean isTouched = false;

    public ExpenseProductsViewHolder(@NonNull View itemView, boolean disableEdit) {
        super(itemView);

        nameInput = itemView.findViewById(R.id.expense_product_name);
        countInput = itemView.findViewById(R.id.expense_product_count);
        priceInput = itemView.findViewById(R.id.expense_product_price);
        deleteButton = itemView.findViewById(R.id.remove_expense_product);

        if(disableEdit){
            deleteButton.setVisibility(View.INVISIBLE);
            deleteButton.setEnabled(false);
        }
    }

    public void setIsTouched(){
        isTouched = true;
    }
}
