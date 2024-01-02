package pwr.lab.expenses_management.ui.product_categories;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.Executors;

import lombok.Getter;
import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.view_model.ProductsCategoriesViewModel;

@Getter
public class ProductsCategoriesViewHolder extends RecyclerView.ViewHolder {

    private final TextView productCategoryTextView;

    public ProductsCategoriesViewHolder(@NonNull View itemView, Context context, ProductsCategoriesViewModel viewModel) {
        super(itemView);

        productCategoryTextView = itemView.findViewById(R.id.product_category);
        Button removeButton = itemView.findViewById(R.id.remove_category);

        removeButton.setOnClickListener(l -> {

            int index = getAdapterPosition();

            Executors.newSingleThreadExecutor().execute(() -> {

                Looper.prepare();

                viewModel.remove(index);
                Toast.makeText(context, "Usunięto kategorię", Toast.LENGTH_SHORT).show();

                Looper.loop();
            });
        });
    }
}
