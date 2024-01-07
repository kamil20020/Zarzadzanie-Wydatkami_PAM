package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.repository.ExpenseRepository;

public class MonthlyReportViewModel extends AndroidViewModel {

    private final ExpenseRepository expenseRepository;

    public MonthlyReportViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ExpenseDAO expenseDAO = appDatabase.expenseDAO();
        expenseRepository = new ExpenseRepository(expenseDAO);
    }
}
