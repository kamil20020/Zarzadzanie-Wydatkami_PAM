package pwr.lab.expenses_management.view_model;

import android.app.Application;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import pwr.lab.expenses_management.data.AppDatabase;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.dao.ExpenseProductDAO;
import pwr.lab.expenses_management.data.dao.ProductDAO;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.data.entity.ExpenseProductEntity;
import pwr.lab.expenses_management.data.entity.ProductEntity;
import pwr.lab.expenses_management.data.repository.ExpenseProductRepository;
import pwr.lab.expenses_management.data.repository.ExpenseRepository;
import pwr.lab.expenses_management.data.repository.ProductRepository;

public class CreateExpenseProductsViewModel extends AndroidViewModel {

    @Data
    @AllArgsConstructor
    public static class Form {
        private String name = "";
        private Integer count = 0;
        private BigDecimal price = BigDecimal.ZERO;
    }

    private MutableLiveData<BigDecimal> totalPrice = new MutableLiveData<>(BigDecimal.ZERO);

    private List<CreateExpenseProductsViewModel.Form> form = new ArrayList<>();
    private final ProductRepository productRepository;
    private final ExpenseProductRepository expenseProductRepository;

    public CreateExpenseProductsViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        ProductDAO productDAO = appDatabase.productDAO();
        ExpenseProductDAO expenseProductDAO = appDatabase.expenseProductDAO();
        this.productRepository = new ProductRepository(productDAO);
        this.expenseProductRepository = new ExpenseProductRepository(expenseProductDAO);

        createEmptyForm();
    }

    public void createEmptyForm(){

        var emptyForm = new Form("", 0, BigDecimal.ZERO);
        form.add(emptyForm);
    }

    public void validate() throws IllegalArgumentException{

        if(form.size() == 0) {
            throw new IllegalArgumentException("Nie podano produktów");
        }

        List<CreateExpenseProductsViewModel.Form> validForm = form.stream()
            .filter(f ->
                !f.getName().isBlank() &&
                f.getCount() > 0 &&
                f.getPrice().longValue() > 0
            )
            .collect(Collectors.toList());

        if(validForm.size() != form.size()){
            throw new IllegalArgumentException("Nie uzupełniono poprawnie informacji o produktach");
        }
    }

    public void remove(int index){
        form.remove(index);
    }

    public void create(Integer expenseId) throws IllegalArgumentException{

        List<ExpenseProductEntity> expenseProducts = new ArrayList<>();

        form.forEach(f -> {

            BigDecimal multiplier = BigDecimal.valueOf(100);
            Long convertedPrice = f.getPrice().multiply(multiplier).longValue();

            String name = f.getName();

            long productId = -1;

            Optional<ProductEntity> foundProductOpt = Optional.ofNullable(
                productRepository.getByName(name)
            );

            if(foundProductOpt.isPresent()){
                productId = foundProductOpt.get().getProductId();
            }
            else{
                ProductEntity productEntity = ProductEntity.builder()
                    .name(name)
                    .build();

                productId = productRepository.create(productEntity);
            }

            ExpenseProductEntity expenseProduct = ExpenseProductEntity.builder()
                .productId((int) productId)
                .expenseId(expenseId)
                .count(f.getCount())
                .price(convertedPrice)
                .build();

            expenseProducts.add(expenseProduct);
        });

        expenseProductRepository.create(expenseProducts);
    }

    private BigDecimal getTotalPriceWithoutProduct(int index){

        CreateExpenseProductsViewModel.Form f = getForm(index);

        int countInt = f.getCount();
        BigDecimal count = BigDecimal.valueOf(countInt);

        BigDecimal oldProductPrice = f.getPrice();
        BigDecimal oldTotalProductPrice = oldProductPrice.multiply(count);

        BigDecimal oldPrice = getTotalPrice().getValue();

        return oldPrice.subtract(oldTotalProductPrice);
    }

    public void updateTotalPrice(int index, BigDecimal newProductPrice, int newCountInt){

        BigDecimal oldMinusTotalProductPrice = getTotalPriceWithoutProduct(index);

        CreateExpenseProductsViewModel.Form f = getForm(index);
        f.setCount(newCountInt);
        f.setPrice(newProductPrice);

        BigDecimal newCount = BigDecimal.valueOf(newCountInt);
        BigDecimal newTotalProductPrice = newProductPrice.multiply(newCount);
        BigDecimal newTotalPrice = oldMinusTotalProductPrice.add(newTotalProductPrice);

        System.out.println(newProductPrice);

        totalPrice.setValue(newTotalPrice);
    }

    public LiveData<BigDecimal> getTotalPrice(){
        return totalPrice;
    }

    public List<CreateExpenseProductsViewModel.Form> getForm(){
        return form;
    }

    public CreateExpenseProductsViewModel.Form getForm(int index){
        return form.get(index);
    }

    public int size(){
        return form.size();
    }
}
