package pwr.lab.expenses_management.ui.products;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;

import pwr.lab.expenses_management.R;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;
import pwr.lab.expenses_management.data.entity.ProductEntity;
import pwr.lab.expenses_management.data.relations.DetailedProduct;
import pwr.lab.expenses_management.view_model.ProductsViewModel;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsViewHolder> {

    private ProductsViewModel viewModel;

    private Context context;

    public ProductsAdapter(ProductsViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void update(){
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       context = parent.getContext();

        View view = LayoutInflater.from(context)
            .inflate(R.layout.product_row_item, parent, false);

        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {

        DetailedProduct detailedProduct = viewModel.get(position);
        ProductEntity product = detailedProduct.getProductEntity();
        Optional<ProductCategoryEntity> categoryOpt = Optional.ofNullable(
            detailedProduct.getCategoryEntity()
        );

        String categoryMessage;

        if(categoryOpt.isPresent()){
            categoryMessage = categoryOpt.get().getName();
        }
        else{
            categoryMessage = "Ogólny";
        }

        holder.getProductNameTextView().setText(product.getName());
        holder.getProductCategoryTextView().setText(categoryMessage);

        holder.itemView.setOnClickListener(l -> {

            int productIndex = holder.getAdapterPosition();

            String[] categories = viewModel.getCategoriesStrings();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("Wybierz kategorię");

            builder.setItems(categories, (dialog, which) -> {
                viewModel.updateProductCategory(productIndex, which - 1);
            });

            builder.show();
        });

        holder.getDeleteButton().setOnClickListener(l -> {

            int index = holder.getAdapterPosition();

            Executors.newSingleThreadExecutor().execute(() -> {

                Looper.prepare();

                viewModel.delete(index);
                Toast.makeText(context, "Usunięto produkt", Toast.LENGTH_SHORT).show();

                Looper.loop();
            });
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.size();
    }
}
