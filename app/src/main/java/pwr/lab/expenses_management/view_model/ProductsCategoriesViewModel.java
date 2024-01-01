package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ProductCategoryDAO;
import pwr.lab.expenses_management.data.dao.ProductDAO;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;
import pwr.lab.expenses_management.data.entity.ProductEntity;
import pwr.lab.expenses_management.data.repository.ProductCategoryRepository;
import pwr.lab.expenses_management.data.repository.ProductRepository;

public class ProductsCategoriesViewModel extends AndroidViewModel {

    private final ProductCategoryRepository productCategoryRepository;

    private LiveData<List<ProductCategoryEntity>> productsCategories;

    public ProductsCategoriesViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ProductCategoryDAO productCategoryDAO = appDatabase.productCategoryDAO();
        productCategoryRepository = new ProductCategoryRepository(productCategoryDAO);
        this.productsCategories = productCategoryRepository.getAll();
    }

    public LiveData<List<ProductCategoryEntity>> getAllCategories() {
        return productsCategories;
    }

    public void remove(int index){
        ProductCategoryEntity categoryToRemove = productsCategories.getValue().get(index);
        productCategoryRepository.remove(categoryToRemove);
    }
}
