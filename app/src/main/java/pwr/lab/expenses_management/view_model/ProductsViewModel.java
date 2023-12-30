package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ProductDAO;
import pwr.lab.expenses_management.data.entity.ProductEntity;
import pwr.lab.expenses_management.data.repository.ProductRepository;

public class ProductsViewModel extends AndroidViewModel {

    private final ProductRepository productRepository;

    private final LiveData<List<ProductEntity>> products;

    public ProductsViewModel(Application application){
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ProductDAO productDAO = appDatabase.productDAO();
        productRepository = new ProductRepository(productDAO);
        products = productRepository.getAll();
    }

    public LiveData<List<ProductEntity>> getAllProducts() {
        return products;
    }
}
