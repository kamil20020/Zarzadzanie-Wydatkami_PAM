package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import pwr.lab.expenses_management.data.dao.ExpenseDAO;

public class YearlyReportViewModel extends AndroidViewModel implements CategoriesCostsViewModel{

    public YearlyReportViewModel(@NonNull Application application) {
        super(application);

    }

    @Override
    public ExpenseDAO.CategoryCost get(int index) {
        return null;
    }

    @Override
    public double getTotalCost() {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }
}
