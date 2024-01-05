package pwr.lab.expenses_management.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.data.relations.DetailedExpense;
import pwr.lab.expenses_management.data.relations.StronglyDetailedExpense;

@Dao
public interface ExpenseDAO {

    @Query("SELECT * FROM expenses")
    LiveData<List<ExpenseEntity>> getAll();

    @Query("SELECT * FROM expenses")
    LiveData<List<DetailedExpense>> getAllDetailed();

    @Query("SELECT * FROM expenses WHERE expense_id = :id")
    LiveData<StronglyDetailedExpense> getStronglyDetailedById(Integer id);

    @Query("SELECT EXISTS(SELECT * FROM expenses WHERE title = :name)")
    boolean existsByName(String name);

    @Insert
    long insert(ExpenseEntity expenseEntity);

    @Update
    void update(ExpenseEntity expenseEntity);

    @Delete
    void delete(ExpenseEntity expenseEntity);
}
