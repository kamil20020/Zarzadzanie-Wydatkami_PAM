package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.data.repository.ExpenseRepository;

public class ExpensesViewModel extends AndroidViewModel {

    private final ExpenseRepository expenseRepository;

    private final LiveData<List<ExpenseEntity>> expenses;

    public ExpensesViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ExpenseDAO expenseDAO = appDatabase.expenseDAO();
        this.expenseRepository = new ExpenseRepository(expenseDAO);

        expenses = expenseRepository.getAll();
    }

    public LiveData<List<ExpenseEntity>> getAll(){
        return expenses;
    }

    public ExpenseEntity get(int index){
        return expenses.getValue().get(index);
    }

    public int size(){

        if(expenses.getValue() == null){
            return 0;
        }

        return expenses.getValue().size();
    }

    public Integer getId(int index){

        ExpenseEntity expense = expenses.getValue().get(index);

        return expense.getExpenseId();
    }

    public void delete(int index){
        ExpenseEntity expenseToRemove = expenses.getValue().get(index);
        expenseRepository.remove(expenseToRemove);
    }
}
