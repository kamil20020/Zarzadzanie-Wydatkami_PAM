package pwr.lab.expenses_management.ui.expenses.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.relations.DetailedExpenseProduct;
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

        Long rawPrice = expenseProduct.getPrice();
        BigDecimal nonConvertedTotalPrice = BigDecimal.valueOf(rawPrice);
        BigDecimal divider = BigDecimal.valueOf(100);
        BigDecimal price = nonConvertedTotalPrice.divide(divider);

        String formattedPrice = String.format("%.2f zł", price);

        holderNameView.setText(product.getName());
        holderCountView.setText(expenseProduct.getCount().toString());
        holderPriceView.setText(formattedPrice);
    }

    @Override
    public int getItemCount() {
        return viewModel.productsSize();
    }
}
