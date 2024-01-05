package pwr.lab.expenses_management.ui.products;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;
import pwr.lab.expenses_management.R;

@Getter
public class ProductsViewHolder extends RecyclerView.ViewHolder {

    private final TextView productNameTextView;
    private final TextView productCategoryTextView;

    private final Button deleteButton;

    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        productNameTextView = itemView.findViewById(R.id.product_name);
        productCategoryTextView = itemView.findViewById(R.id.product_category);
        deleteButton = itemView.findViewById(R.id.delete_product);
    }
}
