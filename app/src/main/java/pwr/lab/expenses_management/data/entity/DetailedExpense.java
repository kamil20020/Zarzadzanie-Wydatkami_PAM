package pwr.lab.expenses_management.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.Data;

@Data
public class DetailedExpense {

    @Embedded
    public ExpenseEntity expenseEntity;

    @Relation(parentColumn = "expense_id", entityColumn = "expense_id")
    public List<ExpenseProductEntity> expenseProductEntities;
}
