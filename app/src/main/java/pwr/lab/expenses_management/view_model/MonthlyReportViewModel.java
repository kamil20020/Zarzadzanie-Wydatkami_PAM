package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.dao.ExpenseProductDAO;
import pwr.lab.expenses_management.data.repository.ExpenseProductRepository;
import pwr.lab.expenses_management.data.repository.ExpenseRepository;

public class MonthlyReportViewModel extends AndroidViewModel implements CategoriesCostsViewModel{

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Form {
        private Integer year = 0;
        private Integer month = 0;
    }

    private final ExpenseRepository expenseRepository;
    private final ExpenseProductRepository expenseProductRepository;
    private final MutableLiveData<MonthlyReport> monthlyReport = new MutableLiveData<>();
    private final MutableLiveData<Form> form = new MutableLiveData<>(new Form());

    public MonthlyReportViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ExpenseDAO expenseDAO = appDatabase.expenseDAO();
        ExpenseProductDAO expenseProductDAO = appDatabase.expenseProductDAO();
        expenseRepository = new ExpenseRepository(expenseDAO);
        expenseProductRepository = new ExpenseProductRepository(expenseProductDAO);
    }

    public LiveData<Form> getForm(){
        return form;
    }

    public void setYear(int year){
        form.setValue(new Form(
            year,
            form.getValue().getMonth()
        ));
    }

    public void setMonth(int month){
        form.setValue(new Form(
            form.getValue().getYear(),
            month
        ));
    }

    public void update(){

        Form formValue = form.getValue();

        if(formValue.getYear() == 0 || formValue.getMonth() == 0){
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            List<ExpenseDAO.CategoryCost> categoriesCosts = expenseRepository.loadReport(
                formValue.getYear(), formValue.getMonth()
            );

            double totalCost = categoriesCosts.stream()
                    .mapToDouble(c -> c.getTotalCost())
                    .sum();

            monthlyReport.postValue(new MonthlyReport(
                    categoriesCosts,
                    totalCost
            ));
        });
    }

    public ExpenseDAO.CategoryCost get(int index){
        return monthlyReport.getValue().getCategoriesCosts().get(index);
    }

    public LiveData<MonthlyReport> getMonthlyReport(){
        return monthlyReport;
    }

    public double getTotalCost(){
        return monthlyReport.getValue().getTotalCost();
    }

    public int size(){

        if(monthlyReport.getValue() == null){
            return 0;
        }

        return  monthlyReport.getValue().getCategoriesCosts().size();
    }
}
