package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.data.entity.ExpenseProductEntity;
import pwr.lab.expenses_management.data.repository.ExpenseRepository;

public class CreateExpenseViewModel extends AndroidViewModel {

    @Data
    public static class Form {
        private String title = "";
        private LocalDate date = LocalDate.now();
    }

    public enum FormErrors {
        NAME,
        DATE,
    }

    private final Form form = new Form();
    private final MutableLiveData<List<Integer>> formErrors = new MutableLiveData<>(new ArrayList<>());
    private final ExpenseRepository expenseRepository;

    private CreateExpenseProductsViewModel createExpenseProductsViewModel;

    public CreateExpenseViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ExpenseDAO expenseDAO = appDatabase.expenseDAO();
        this.expenseRepository = new ExpenseRepository(expenseDAO);
    }

    public void setCreateExpenseProductsViewModel(CreateExpenseProductsViewModel viewModel){
        createExpenseProductsViewModel = viewModel;
    }

    public boolean validateForm() throws IllegalArgumentException{

        List<Integer> formErrorsData = formErrors.getValue();

        formErrorsData.clear();

        if(form.getTitle().isBlank()){
            formErrorsData.add(FormErrors.NAME.ordinal());
        }

        if(form.getDate() == null){
            formErrorsData.add(FormErrors.DATE.ordinal());
        }

        formErrors.setValue(formErrorsData);

        return formErrorsData.isEmpty();
    }

    public void createExpense(){

        createExpenseProductsViewModel.validateForm();

        if(expenseRepository.existsByName(form.getTitle())){
            throw new IllegalArgumentException("Istnieje już wydatek o takiej nazwie");
        }

        BigDecimal totalPrice = createExpenseProductsViewModel.getTotalPrice().getValue();
        BigDecimal multiplier = BigDecimal.valueOf(100);
        long convertedTotalPrice = totalPrice.multiply(multiplier).longValue();

        ExpenseEntity toCreateExpense = ExpenseEntity.builder()
            .title(form.getTitle())
            .date(form.getDate().toString())
            .totalPrice(convertedTotalPrice)
            .build();

        long createdExpenseId = expenseRepository.create(toCreateExpense);
        createExpenseProductsViewModel.create((int) createdExpenseId);
    }

    public Form getForm(){
        return form;
    }

    public LiveData<List<Integer>> getFormErrors(){
        return formErrors;
    }
}
