package pwr.lab.expenses_management.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pwr.lab.expenses_management.data.entity.ProductEntity;

@Dao
public interface ProductDAO {

    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> loadAll();

    @Query("SELECT * FROM products WHERE LOWER(name) = LOWER(:name)")
    ProductEntity getByName(String name);

    @Insert
    long insert(ProductEntity productEntity);

    @Update
    void update(ProductEntity productEntity);

    @Delete
    void delete(ProductEntity productEntity);
}
