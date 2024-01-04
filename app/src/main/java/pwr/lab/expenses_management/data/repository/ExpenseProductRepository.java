package pwr.lab.expenses_management.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import pwr.lab.expenses_management.data.dao.ExpenseProductDAO;
import pwr.lab.expenses_management.data.entity.ExpenseProductEntity;

public class ExpenseProductRepository {

    private final ExpenseProductDAO expenseProductDAO;

    public ExpenseProductRepository(ExpenseProductDAO expenseProductDAO){
        this.expenseProductDAO = expenseProductDAO;
    }

    public void create(List<ExpenseProductEntity> expenseProducts){
        expenseProductDAO.insert(expenseProducts);
    }

    public void update(ExpenseProductEntity expenseProductEntity){
        expenseProductDAO.update(expenseProductEntity);
    }

    public void delete(ExpenseProductEntity expenseProductEntity){
        expenseProductDAO.delete(expenseProductEntity);
    }
}
