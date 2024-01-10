package pwr.lab.expenses_management.data.dao;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import lombok.Data;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.data.relations.DetailedExpense;
import pwr.lab.expenses_management.data.relations.StronglyDetailedExpense;

@Dao
public interface ExpenseDAO {

    @Data
    class CategoryCost{
        public double totalCost;
        public String category;
    }

    @Query("SELECT * FROM expenses")
    LiveData<List<ExpenseEntity>> getAll();

    @Query("SELECT * FROM expenses")
    LiveData<List<DetailedExpense>> getAllDetailed();

    @Query("SELECT * FROM expenses WHERE expense_id = :id")
    LiveData<StronglyDetailedExpense> getStronglyDetailedById(Integer id);

    @Query("""
        SELECT c.name as category, SUM(e_p.price * e_p.count) / 100 as totalCost
        FROM expenses_products e_p
        INNER JOIN expenses e ON e.expense_id = e_p.expense_id
        INNER JOIN products p ON p.product_id = e_p.product_id
        INNER JOIN products_categories c ON c.product_category_id = p.category_id
        WHERE strftime('%Y', e.date) = :year AND 
              (:month IS NULL OR strftime('%m', e.date) = :month)
        GROUP BY category
    """
    )
    List<ExpenseDAO.CategoryCost> loadCategoriesSummary(String year, @Nullable String month);

    @Query("""
        SELECT SUM(total_price) / 100
        FROM expenses
        WHERE strftime('%Y', date) = :year
        GROUP BY strftime('%m', date)
    """
    )
    List<Double> loadYearSummary(String year);

    @Query("SELECT EXISTS(SELECT * FROM expenses WHERE title = :name)")
    boolean existsByName(String name);

    @Insert
    long insert(ExpenseEntity expenseEntity);

    @Update
    void update(ExpenseEntity expenseEntity);

    @Delete
    void delete(ExpenseEntity expenseEntity);
}
