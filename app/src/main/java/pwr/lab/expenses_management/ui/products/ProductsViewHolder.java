package pwr.lab.expenses_management.ui.products;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pwr.lab.expenses_management.R;

public class ProductsViewHolder extends RecyclerView.ViewHolder {

    private final TextView productNameTextView;

    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        productNameTextView = itemView.findViewById(R.id.product_name);
    }

    public TextView getProductNameTextView(){
        return productNameTextView;
    }
}
