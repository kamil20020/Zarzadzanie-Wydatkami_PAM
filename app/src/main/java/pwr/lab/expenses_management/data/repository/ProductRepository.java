package pwr.lab.expenses_management.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import pwr.lab.expenses_management.data.dao.ProductDAO;
import pwr.lab.expenses_management.data.entity.ProductEntity;

public class ProductRepository {

    private ProductDAO productDAO;

    public ProductRepository(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    public LiveData<List<ProductEntity>> getAll(){
        return productDAO.loadAll();
    }

    public void create(ProductEntity productEntity){
        productDAO.insert(productEntity);
    }

    public void update(ProductEntity productEntity){
        productDAO.update(productEntity);
    }

    public void delete(ProductEntity productEntity){
        productDAO.delete(productEntity);
    }
}
