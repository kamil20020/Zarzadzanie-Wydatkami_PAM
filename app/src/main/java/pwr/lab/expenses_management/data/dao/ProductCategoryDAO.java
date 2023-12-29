package pwr.lab.expenses_management.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;

@Dao
public interface ProductCategoryDAO {

    @Query("SELECT * FROM products_categories")
    List<ProductCategoryEntity> loadAll();

    @Insert
    void insert(ProductCategoryEntity productCategoryEntity);
}
