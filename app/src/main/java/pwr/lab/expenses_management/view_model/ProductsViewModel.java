package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ProductCategoryDAO;
import pwr.lab.expenses_management.data.dao.ProductDAO;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;
import pwr.lab.expenses_management.data.entity.ProductEntity;
import pwr.lab.expenses_management.data.relations.DetailedProduct;
import pwr.lab.expenses_management.data.repository.ProductCategoryRepository;
import pwr.lab.expenses_management.data.repository.ProductRepository;

public class ProductsViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    private final LiveData<List<DetailedProduct>> products;
    private List<ProductCategoryEntity> categories = new ArrayList<>();

    public ProductsViewModel(Application application){
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ProductDAO productDAO = appDatabase.productDAO();
        ProductCategoryDAO productCategoryDAO = appDatabase.productCategoryDAO();
        productRepository = new ProductRepository(productDAO);
        productCategoryRepository = new ProductCategoryRepository(productCategoryDAO);

        products = productRepository.getAllDetailed();

        Executors.newSingleThreadExecutor().execute(() -> {
            categories = productCategoryRepository.getAllStatic();
        });
    }

    public void delete(int index){

        ProductEntity productEntity = get(index).getProductEntity();
        productRepository.delete(productEntity);
    }

    public LiveData<List<DetailedProduct>> getAll() {
        return products;
    }

    public DetailedProduct get(int index){
        return products.getValue().get(index);
    }

    public void updateProductCategory(int productIndex, int categoryIndex){

        categoryIndex++;

        ProductEntity toUpdateProduct = products.getValue().get(productIndex).getProductEntity();

        Integer categoryId = categories.get(categoryIndex).getCategoryId();
        toUpdateProduct.setCategoryId(categoryId);

        Executors.newSingleThreadExecutor().execute(() -> {
            productRepository.update(toUpdateProduct);
        });
    }

    public String[] getCategoriesStrings(){

        return categories.stream()
            .map(ProductCategoryEntity::getName)
            .toArray(size -> new String[size]);
    }

    public int size(){

        if(products.getValue() == null){
            return 0;
        }

        return products.getValue().size();
    }
}
