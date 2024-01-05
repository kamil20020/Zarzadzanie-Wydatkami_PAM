package pwr.lab.expenses_management.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.data.relations.DetailedExpense;
import pwr.lab.expenses_management.data.relations.StronglyDetailedExpense;

public class ExpenseRepository {

    private static ExpenseDAO expenseDAO;

    public ExpenseRepository(ExpenseDAO expenseDAO){
        this.expenseDAO = expenseDAO;
    }

    public LiveData<StronglyDetailedExpense> getStronglyDetailedById(Integer id){
        return expenseDAO.getStronglyDetailedById(id);
    }

    public boolean existsByName(String name){
        return expenseDAO.existsByName(name);
    }
    public long create(ExpenseEntity expenseEntity){ return expenseDAO.insert(expenseEntity);}

    public LiveData<List<DetailedExpense>> getAllDetailed(){
        return expenseDAO.getAllDetailed();
    }
    public LiveData<List<ExpenseEntity>> getAll(){
        return expenseDAO.getAll();
    }

    public void remove(ExpenseEntity expenseEntity){
        expenseDAO.delete(expenseEntity);
    }
}
