package pwr.lab.expenses_management.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pwr.lab.expenses_management.data.entity.ExpenseProductEntity;

@Dao
public interface ExpenseProductDAO {

    @Insert
    void insert(ExpenseProductEntity expenseProductEntity);

    @Update
    void update(ExpenseProductEntity expenseProductEntity);

    @Delete
    void delete(ExpenseProductEntity expenseProductEntity);
}
