package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.dao.ExpenseProductDAO;
import pwr.lab.expenses_management.data.repository.ExpenseProductRepository;
import pwr.lab.expenses_management.data.repository.ExpenseRepository;

public class YearlyReportViewModel extends AndroidViewModel implements CategoriesCostsViewModel{

    private MutableLiveData<Integer> year = new MutableLiveData<>(0);
    private MutableLiveData<List<Double>> yearSummary = new MutableLiveData<>(new ArrayList<>());
    private final ExpenseRepository expenseRepository;
    private final MutableLiveData<MonthlyReport> monthlyReport = new MutableLiveData<>();

    public YearlyReportViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ExpenseDAO expenseDAO = appDatabase.expenseDAO();
        ExpenseProductDAO expenseProductDAO = appDatabase.expenseProductDAO();
        expenseRepository = new ExpenseRepository(expenseDAO);
    }

    @Override
    public ExpenseDAO.CategoryCost get(int index) {
        return monthlyReport.getValue().getCategoriesCosts().get(index);
    }

    @Override
    public double getTotalCost() {
        return monthlyReport.getValue().getTotalCost();
    }

    public LiveData<Integer> getYear(){
        return year;
    }

    public LiveData<MonthlyReport> getMonthlyReport(){
        return monthlyReport;
    }

    public LiveData<List<Double>> getYearSummary(){
        return yearSummary;
    }

    public void update(){

        if(year.getValue() == 0){
            return;
        }

        updateMonthlySummary();
        updateYearSummary();
    }

    private void updateMonthlySummary(){

        Executors.newSingleThreadExecutor().execute(() -> {
            List<ExpenseDAO.CategoryCost> categoriesCosts = expenseRepository.loadReport(
                year.getValue(), null
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

    private void updateYearSummary(){

        Executors.newSingleThreadExecutor().execute(() -> {
            yearSummary.postValue(
                expenseRepository.loadYearSummary(year.getValue())
            );
        });
    }

    @Override
    public int size() {

        if(monthlyReport.getValue() == null){
            return 0;
        }

        return  monthlyReport.getValue().getCategoriesCosts().size();
    }

    public void setYear(Integer year) {
        this.year.postValue(year);
    }
}
