package pwr.lab.expenses_management.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.entity.ExpenseProductEntity;
import pwr.lab.expenses_management.data.repository.ExpenseRepository;

public class CreateExpenseProductsViewModel extends AndroidViewModel {

    @Data
    @AllArgsConstructor
    public static class Form {
        private String name = "";
        private Integer count = 0;
        private BigDecimal price = BigDecimal.ZERO;
    }

    private List<CreateExpenseProductsViewModel.Form> form = new ArrayList<>();

    private final ExpenseRepository expenseRepository;

    public CreateExpenseProductsViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ExpenseDAO expenseDAO = appDatabase.expenseDAO();
        this.expenseRepository = new ExpenseRepository(expenseDAO);

        createEmptyForm();
    }

    public void createEmptyForm(){

        var emptyForm = new Form("", 0, BigDecimal.ZERO);
        form.add(emptyForm);
    }

    public List<CreateExpenseProductsViewModel.Form> getForm(){
        return form;
    }

    public CreateExpenseProductsViewModel.Form get(int index){
        return form.get(index);
    }
}
