package pwr.lab.expenses_management.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import lombok.Data;

@Data
public class DetailedExpenseProduct {

    @Embedded
    public ExpenseProductEntity expenseProductEntity;

    @Relation(parentColumn = "product_id", entityColumn = "product_id")
    public ProductEntity productEntity;
}
