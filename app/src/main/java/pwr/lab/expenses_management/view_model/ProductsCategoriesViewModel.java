package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ProductCategoryDAO;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;
import pwr.lab.expenses_management.data.repository.ProductCategoryRepository;

public class ProductsCategoriesViewModel extends AndroidViewModel {

    private String categoryName = "";
    private boolean isAddModeEnabled = false;
    private final ProductCategoryRepository productCategoryRepository;

    private final LiveData<List<ProductCategoryEntity>> productsCategories;

    public ProductsCategoriesViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ProductCategoryDAO productCategoryDAO = appDatabase.productCategoryDAO();
        productCategoryRepository = new ProductCategoryRepository(productCategoryDAO);
        this.productsCategories = productCategoryRepository.getAll();
    }

    public void setCategoryName(String categoryName){
        this.categoryName = categoryName;
    }

    public String getCategoryName(){
        return categoryName;
    }

    public void switchAddMode(){
        isAddModeEnabled = !isAddModeEnabled;
    }
    public boolean getIsAddModeEnabled(){
        return isAddModeEnabled;
    }

    public LiveData<List<ProductCategoryEntity>> getAllCategories() {
        return productsCategories;
    }

    public ProductCategoryEntity get(int index){
        return productsCategories.getValue().get(index);
    }

    public void create() throws IllegalArgumentException{

        if(productCategoryRepository.existsByName(categoryName)){
            throw new IllegalArgumentException("Istnieje już kategoria o takiej nazwie");
        }

        ProductCategoryEntity categoryEntity = ProductCategoryEntity.builder()
            .name(categoryName)
            .build();

        productCategoryRepository.create(categoryEntity);

        switchAddMode();
    }

    public void remove(int index){
        ProductCategoryEntity categoryToRemove = productsCategories.getValue().get(index);
        productCategoryRepository.remove(categoryToRemove);
    }

    public int size(){

        if(productsCategories.getValue() == null){
            return 0;
        }

        return productsCategories.getValue().size();
    }
}
