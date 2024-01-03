package pwr.lab.expenses_management.ui.expenses.expense;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
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

    public CreateExpenseProductsViewHolder(@NonNull View itemView, CreateExpenseProductsViewModel viewModel) {
        super(itemView);

        nameInput = itemView.findViewById(R.id.expense_product_name);
        countInput = itemView.findViewById(R.id.expense_product_count);
        priceInput = itemView.findViewById(R.id.expense_product_price);

        nameInput.addTextChangedListener(new TextChangedListener<>(nameInput) {
            @Override
            public void onTextChanged(EditText target, Editable s) {

                String name = target.getText().toString();

                getForm(viewModel).setName(name);
            }
        });

        countInput.addTextChangedListener(new TextChangedListener<>(countInput) {
            @Override
            public void onTextChanged(EditText target, Editable s) {

                String countStr = target.getText().toString();
                Integer count = Integer.valueOf(countStr);

                getForm(viewModel).setCount(count);
            }
        });

        priceInput.addTextChangedListener(new TextChangedListener<>(priceInput) {
            @Override
            public void onTextChanged(EditText target, Editable s) {

                String priceStr = target.getText().toString();
                BigDecimal price = new BigDecimal(priceStr);

                getForm(viewModel).setPrice(price);
            }
        });
    }

    private CreateExpenseProductsViewModel.Form getForm(CreateExpenseProductsViewModel viewModel){

        int index = getAdapterPosition();

        return viewModel.get(index);
    }
}
