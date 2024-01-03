package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.data.repository.ExpenseRepository;

public class CreateExpenseViewModel extends AndroidViewModel {

    @Data
    public static class Form {
        private String title = "";
        private LocalDate date = LocalDate.now();
        private BigDecimal totalPrice = BigDecimal.ZERO;
    }

    public enum FormErrors {
        NAME,
        DATE,
        TOTAL_PRICE
    }

    private Form form = new Form();
    private MutableLiveData<List<Integer>> formErrors = new MutableLiveData<>(new ArrayList<>());

    private final ExpenseRepository expenseRepository;

    public CreateExpenseViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ExpenseDAO expenseDAO = appDatabase.expenseDAO();
        this.expenseRepository = new ExpenseRepository(expenseDAO);
    }
    public boolean validateForm(){

        List<Integer> formErrorsData = formErrors.getValue();

        formErrorsData.clear();

        if(form.getTitle().isBlank()){
            formErrorsData.add(FormErrors.NAME.ordinal());
        }

        if(form.getDate() == null){
            formErrorsData.add(FormErrors.DATE.ordinal());
        }

        if(form.getTotalPrice() == null){
            formErrorsData.add(FormErrors.TOTAL_PRICE.ordinal());
        }

        formErrors.setValue(formErrorsData);

        return formErrorsData.isEmpty();
    }

    public void createExpense() throws IllegalArgumentException{

        BigDecimal multiplier = BigDecimal.valueOf(100);
        Long totalPrice = form.getTotalPrice().multiply(multiplier).longValue();

        if(expenseRepository.existsByName(form.getTitle())){
            throw new IllegalArgumentException("Istnieje już wydatek o takiej nazwie");
        }

        ExpenseEntity expenseEntity = ExpenseEntity.builder()
            .title(form.getTitle())
            .date(form.getDate().toString())
            .totalPrice(totalPrice)
            .build();

        expenseRepository.create(expenseEntity);
    }

    public Form getForm(){
        return form;
    }

    public LiveData<List<Integer>> getFormErrors(){
        return formErrors;
    }
}
