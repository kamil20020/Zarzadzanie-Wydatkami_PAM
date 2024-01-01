package pwr.lab.expenses_management.ui.products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.ProductEntity;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsViewHolder> {

    private List<ProductEntity> products = new ArrayList<>();

    public void setProducts(List<ProductEntity> products){
        this.products.clear();
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.product_row_item, parent, false);

        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {

        holder.getProductNameTextView().setText(products.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
