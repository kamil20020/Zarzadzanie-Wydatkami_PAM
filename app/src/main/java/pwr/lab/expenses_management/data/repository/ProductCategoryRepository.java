package pwr.lab.expenses_management.data.repository;

import java.util.List;

import pwr.lab.expenses_management.data.dao.ProductCategoryDAO;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;

public class ProductCategoryRepository {

    private static ProductCategoryDAO productCategoryDAO;

    public ProductCategoryRepository(ProductCategoryDAO dao){
        productCategoryDAO = dao;
    }

    public List<ProductCategoryEntity> getAll(){
        return productCategoryDAO.loadAll();
    }
}
