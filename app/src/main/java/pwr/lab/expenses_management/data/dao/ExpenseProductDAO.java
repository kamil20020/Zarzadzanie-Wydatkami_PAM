package pwr.lab.expenses_management.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import pwr.lab.expenses_management.data.entity.ExpenseProductEntity;

@Dao
public interface ExpenseProductDAO {

    @Query("""
        SELECT *
        FROM expenses_products e_p
        INNER JOIN expenses e ON e.expense_id = e_p.expense_id
        INNER JOIN products p ON p.product_id = e_p.product_id
        INNER JOIN products_categories c ON c.product_category_id = p.category_id
        WHERE p.category_id = :categoryId AND 
              strftime('%Y', e.date) = :year AND 
              (:month IS NULL OR strftime('%m', e.date) = :month)
    """
    )
    LiveData<List<ExpenseProductEntity>> loadByCategorySummary(Integer categoryId, String year, String month);

    @Insert
    void insert(List<ExpenseProductEntity> expenseProducts);

    @Update
    void update(ExpenseProductEntity expenseProductEntity);

    @Delete
    void delete(ExpenseProductEntity expenseProductEntity);
}
