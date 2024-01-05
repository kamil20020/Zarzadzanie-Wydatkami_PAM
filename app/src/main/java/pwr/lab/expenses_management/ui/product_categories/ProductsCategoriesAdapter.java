package pwr.lab.expenses_management.ui.product_categories;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;
import pwr.lab.expenses_management.view_model.ProductsCategoriesViewModel;

public class ProductsCategoriesAdapter extends RecyclerView.Adapter<ProductsCategoriesViewHolder> {
    private final ProductsCategoriesViewModel viewModel;

    private Context context;

    public ProductsCategoriesAdapter(ProductsCategoriesViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void update(){
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductsCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.product_category_row_item, parent, false);

        context = parent.getContext();

        return new ProductsCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsCategoriesViewHolder holder, int position) {

        ProductCategoryEntity currentCategory = viewModel.get(position);
        holder.getProductCategoryTextView().setText(currentCategory.getName());

        holder.getRemoveButton().setOnClickListener(l -> {

            int index = holder.getAdapterPosition();

            Executors.newSingleThreadExecutor().execute(() -> {

                Looper.prepare();

                viewModel.remove(index);
                Toast.makeText(context, "Usunięto kategorię", Toast.LENGTH_SHORT).show();

                Looper.loop();
            });
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.size();
    }
}
