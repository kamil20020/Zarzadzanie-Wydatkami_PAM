package pwr.lab.expenses_management.ui.expenses.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.DetailedExpenseProduct;
import pwr.lab.expenses_management.data.entity.ExpenseProductEntity;
import pwr.lab.expenses_management.data.entity.ProductEntity;
import pwr.lab.expenses_management.view_model.ExpenseViewModel;

public class ExpenseProductsAdapter extends RecyclerView.Adapter<ExpenseProductsViewHolder> {

    private ExpenseViewModel viewModel;

    public ExpenseProductsAdapter(ExpenseViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void update(){
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_product_row_item, parent, false);

        return new ExpenseProductsViewHolder(view, true);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseProductsViewHolder holder, int position) {

        TextView holderNameView = holder.getNameInput();
        TextView holderCountView = holder.getCountInput();
        TextView holderPriceView = holder.getPriceInput();

        DetailedExpenseProduct detailedExpenseProduct = viewModel.getExpenseProduct(position);
        ExpenseProductEntity expenseProduct = detailedExpenseProduct.getExpenseProductEntity();
        ProductEntity product = detailedExpenseProduct.getProductEntity();

        holderNameView.setText(product.getName());
        holderCountView.setText(expenseProduct.getCount().toString());
        holderPriceView.setText(expenseProduct.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return viewModel.productsSize();
    }
}
