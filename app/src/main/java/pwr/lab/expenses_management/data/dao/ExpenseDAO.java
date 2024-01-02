package pwr.lab.expenses_management.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.data.entity.DetailedExpense;

@Dao
public interface ExpenseDAO {
    @Query("SELECT * FROM expenses")
    LiveData<List<DetailedExpense>> getAllDetailed();

    @Insert
    void insert(ExpenseEntity expenseEntity);

    @Update
    void update(ExpenseEntity expenseEntity);

    @Delete
    void delete(ExpenseEntity expenseEntity);
}