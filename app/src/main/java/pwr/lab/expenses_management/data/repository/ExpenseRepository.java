package pwr.lab.expenses_management.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.data.entity.DetailedExpense;

public class ExpenseRepository {

    private static ExpenseDAO expenseDAO;

    public ExpenseRepository(ExpenseDAO expenseDAO){
        this.expenseDAO = expenseDAO;
    }

    public LiveData<List<DetailedExpense>> getAllDetailed(){
        return expenseDAO.getAllDetailed();
    }

    public void remove(ExpenseEntity expenseEntity){
        expenseDAO.delete(expenseEntity);
    }
}
