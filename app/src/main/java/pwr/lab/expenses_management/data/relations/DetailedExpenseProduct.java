package pwr.lab.expenses_management.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import lombok.Data;
import pwr.lab.expenses_management.data.entity.ExpenseProductEntity;
import pwr.lab.expenses_management.data.entity.ProductEntity;

@Data
public class DetailedExpenseProduct {

    @Embedded
    public ExpenseProductEntity expenseProductEntity;

    @Relation(parentColumn = "product_id", entityColumn = "product_id")
    public ProductEntity productEntity;
}
