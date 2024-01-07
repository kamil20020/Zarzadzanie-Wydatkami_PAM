package pwr.lab.expenses_management.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;

@Dao
public interface ProductCategoryDAO {

    @Query("SELECT * FROM products_categories WHERE LOWER(name) = LOWER(:name)")
    boolean existsByName(String name);

    @Query("SELECT * FROM products_categories")
    LiveData<List<ProductCategoryEntity>> loadAll();

    @Query("SELECT * FROM products_categories")
    List<ProductCategoryEntity> loadAllStatic();

    @Insert
    void insert(ProductCategoryEntity productCategoryEntity);

    @Delete
    void remove(ProductCategoryEntity productCategoryEntity);
}
