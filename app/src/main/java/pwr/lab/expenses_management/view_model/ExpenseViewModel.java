package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.relations.DetailedExpenseProduct;
import pwr.lab.expenses_management.data.relations.StronglyDetailedExpense;
import pwr.lab.expenses_management.data.repository.ExpenseRepository;

public class ExpenseViewModel extends AndroidViewModel {

    private final ExpenseRepository expenseRepository;
    private LiveData<StronglyDetailedExpense> expense;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ExpenseDAO expenseDAO = appDatabase.expenseDAO();
        expenseRepository = new ExpenseRepository(expenseDAO);
    }

    public void loadExpense(Integer expenseId){
        expense = expenseRepository.getStronglyDetailedById(expenseId);
    }

    public LiveData<StronglyDetailedExpense> getExpenseLiveData(){
        return expense;
    }

    public List<DetailedExpenseProduct> getExpenseProducts(){
        return expense.getValue().getDetailedExpenseProducts();
    }

    public DetailedExpenseProduct getExpenseProduct(int index){
        return getExpenseProducts().get(index);
    }

    public int productsSize(){

        if(expense.getValue() == null){
            return 0;
        }

        return getExpenseProducts().size();
    }
}
