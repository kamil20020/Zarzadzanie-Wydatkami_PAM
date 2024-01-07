package pwr.lab.expenses_management.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import pwr.lab.expenses_management.data.dao.ProductCategoryDAO;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;

public class ProductCategoryRepository {

    private static ProductCategoryDAO productCategoryDAO;

    public ProductCategoryRepository(ProductCategoryDAO dao){
        productCategoryDAO = dao;
    }

    public boolean existsByName(String name){
        return productCategoryDAO.existsByName(name);
    }
    public LiveData<List<ProductCategoryEntity>> getAll(){
        return productCategoryDAO.loadAll();
    }

    public List<ProductCategoryEntity> getAllStatic(){
        return productCategoryDAO.loadAllStatic();
    }

    public void create(ProductCategoryEntity categoryEntity) {
        productCategoryDAO.insert(categoryEntity);
    }

    public void remove(ProductCategoryEntity category){
        productCategoryDAO.remove(category);
    }
}
