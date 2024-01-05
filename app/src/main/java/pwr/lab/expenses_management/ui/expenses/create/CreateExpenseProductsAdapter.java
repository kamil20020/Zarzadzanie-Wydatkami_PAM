package pwr.lab.expenses_management.ui.expenses.create;

import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.ui.TextChangedListener;
import pwr.lab.expenses_management.ui.expenses.view.ExpenseProductsViewHolder;
import pwr.lab.expenses_management.view_model.CreateExpenseProductsViewModel;

public class CreateExpenseProductsAdapter extends RecyclerView.Adapter<ExpenseProductsViewHolder> {

    private final CreateExpenseProductsViewModel viewModel;

    int errorColor = 0;
    int normalColor = 0;

    public CreateExpenseProductsAdapter(CreateExpenseProductsViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void notifyLastAdded(){

        int size = viewModel.size();

        notifyItemInserted(size - 1);
        notifyItemRangeChanged(size - 1, size);
    }

    @NonNull
    @Override
    public ExpenseProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_product_row_item, parent, false);

        Context context = parent.getContext();

        errorColor = ContextCompat.getColor(context, R.color.red);
        normalColor = ContextCompat.getColor(context, R.color.black);

        return new ExpenseProductsViewHolder(view, false);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseProductsViewHolder holder, int position) {

        handleHolderNameInput(holder, position);
        handleHolderCountInput(holder, position);
        handleHolderPriceInput(holder, position);
        handleRemoveItem(holder);
    }

    private void handleHolderNameInput(ExpenseProductsViewHolder holder, int position){

        EditText holderNameInput = holder.getNameInput();

        holderNameInput.setText(viewModel.getForm(position).getName());
        holderNameInput.addTextChangedListener(new TextChangedListener<>(holder.getNameInput()) {
            @Override
            public void onTextChanged(EditText target, Editable s) {

                int index = holder.getAdapterPosition();

                String name = target.getText().toString();

                if (!holder.isTouched()) {

                    if (!name.isBlank()) {
                        holder.setIsTouched();
                    }
                }
                else {

                    if (name.isBlank()) {
                        target.setTextColor(errorColor);
                    }
                    else {
                        target.setTextColor(normalColor);
                    }
                }

                viewModel.getForm(index).setName(name);
            }
        });
    }

    private void handleHolderCountInput(ExpenseProductsViewHolder holder, int position){

        EditText holderCountInput = holder.getCountInput();

        holderCountInput.setText(viewModel.getForm(position).getCount().toString());
        holderCountInput.addTextChangedListener(new TextChangedListener<>(holderCountInput) {
            @Override
            public void onTextChanged(EditText target, Editable s) {

                int index = holder.getAdapterPosition();

                String countStr = target.getText().toString();
                Integer count;

                if(countStr.isBlank()){
                    count = 0;
                    target.setText(count.toString());
                }
                else{
                    count = Integer.valueOf(countStr);
                }

                if(holder.isTouched()){

                    if(count > 0){
                        target.setTextColor(normalColor);
                    }
                    else{
                        target.setTextColor(errorColor);
                    }
                }

                var form = viewModel.getForm(index);

                viewModel.updateTotalPrice(index, form.getPrice(), count);
            }
        });
    }

    private void handleHolderPriceInput(ExpenseProductsViewHolder holder, int position) {

        EditText holderPriceInput = holder.getPriceInput();

        holderPriceInput.setText(viewModel.getForm(position).getPrice().toString());

        holderPriceInput.addTextChangedListener(new TextChangedListener<>(holderPriceInput) {
            @Override
            public void onTextChanged(EditText target, Editable s) {

                int index = holder.getAdapterPosition();

                String priceStr = target.getText().toString();
                BigDecimal price;

                if(priceStr.isBlank()){
                    price = BigDecimal.ZERO;
                    target.setText(price.toString());
                }
                else{
                    price = new BigDecimal(priceStr);
                }

                if(holder.isTouched()){

                    if(price.intValue() > 0){
                        target.setTextColor(normalColor);
                    }
                    else{
                        target.setTextColor(errorColor);
                    }
                }

                var form = viewModel.getForm(index);

                viewModel.updateTotalPrice(index, price, form.getCount());
            }
        });
    }

    private void handleRemoveItem(ExpenseProductsViewHolder holder) {

        Button removeButton = holder.getDeleteButton();
        removeButton.setOnClickListener(l -> {

            int index = holder.getAdapterPosition();

            viewModel.remove(index);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, getItemCount());
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.getForm().size();
    }
}
