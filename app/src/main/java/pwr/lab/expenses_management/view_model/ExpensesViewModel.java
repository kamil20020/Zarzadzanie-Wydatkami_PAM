package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.data.entity.DetailedExpense;
import pwr.lab.expenses_management.data.repository.ExpenseRepository;

public class ExpensesViewModel extends AndroidViewModel {

    private final ExpenseRepository expenseRepository;

    private final LiveData<List<DetailedExpense>> detailedExpenses;

    public ExpensesViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ExpenseDAO expenseDAO = appDatabase.expenseDAO();
        this.expenseRepository = new ExpenseRepository(expenseDAO);

        detailedExpenses = expenseRepository.getAllDetailed();
    }

    public LiveData<List<DetailedExpense>> getAllDetailedExpenses(){
        return detailedExpenses;
    }

    public DetailedExpense get(int index){
        return detailedExpenses.getValue().get(index);
    }

    public int size(){

        if(detailedExpenses.getValue() == null){
            return 0;
        }

        return detailedExpenses.getValue().size();
    }

    public Integer getId(int index){

        DetailedExpense detailedExpense = detailedExpenses.getValue().get(index);
        ExpenseEntity expense = detailedExpense.getExpenseEntity();

        return expense.getExpenseId();
    }

    public void delete(int index){
        DetailedExpense detailedExpenseToRemove = detailedExpenses.getValue().get(index);
        ExpenseEntity expenseToRemove = detailedExpenseToRemove.getExpenseEntity();
        expenseRepository.remove(expenseToRemove);
    }
}
