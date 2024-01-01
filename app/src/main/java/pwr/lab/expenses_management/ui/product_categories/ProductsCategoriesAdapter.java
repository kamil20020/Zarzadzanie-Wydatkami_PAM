package pwr.lab.expenses_management.ui.product_categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;
import pwr.lab.expenses_management.data.entity.ProductEntity;
import pwr.lab.expenses_management.view_model.ProductsCategoriesViewModel;

public class ProductsCategoriesAdapter extends RecyclerView.Adapter<ProductsCategoriesViewHolder> {

    private List<ProductCategoryEntity> productCategories = new ArrayList<>();
    private final ProductsCategoriesViewModel viewModel;

    public ProductsCategoriesAdapter(ProductsCategoriesViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void setCategories(List<ProductCategoryEntity> categories){
        this.productCategories.clear();
        this.productCategories.addAll(categories);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductsCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.product_category_row_item, parent, false);

        return new ProductsCategoriesViewHolder(view, parent.getContext(), viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsCategoriesViewHolder holder, int position) {

        ProductCategoryEntity currentCategory = productCategories.get(position);
        holder.getProductCategoryTextView().setText(currentCategory.getName());
    }

    @Override
    public int getItemCount() {
        return productCategories.size();
    }
}
