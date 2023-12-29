package pwr.lab.expenses_management.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import pwr.lab.expenses_management.data.dao.ProductCategoryDAO;
import pwr.lab.expenses_management.data.dao.ProductDAO;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;

public class ProductCategoryRepository {

    private static ProductCategoryDAO productCategoryDAO;

    private static ProductCategoryRepository INSTANCE = null;

    private ProductCategoryRepository(ProductCategoryDAO dao){
        productCategoryDAO = dao;
    }

    public static ProductCategoryRepository getInstance(ProductCategoryDAO productCategoryDAO){

        if(INSTANCE != null){
            return INSTANCE;
        }

        return new ProductCategoryRepository(productCategoryDAO);
    }

    public ProductCategoryRepository getInstance(){
        return INSTANCE;
    }

    public List<ProductCategoryEntity> getAll(){
        return productCategoryDAO.loadAll();
    }
}
