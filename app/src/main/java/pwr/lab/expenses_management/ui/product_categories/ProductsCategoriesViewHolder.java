package pwr.lab.expenses_management.ui.product_categories;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;
import pwr.lab.expenses_management.R;

@Getter
public class ProductsCategoriesViewHolder extends RecyclerView.ViewHolder {

    private final TextView productCategoryTextView;

    private final Button removeButton;

    public ProductsCategoriesViewHolder(@NonNull View itemView) {
        super(itemView);

        productCategoryTextView = itemView.findViewById(R.id.product_category);
        removeButton = itemView.findViewById(R.id.remove_category);
    }
}
