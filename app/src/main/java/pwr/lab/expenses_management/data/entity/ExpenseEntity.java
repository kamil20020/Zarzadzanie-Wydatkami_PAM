package pwr.lab.expenses_management.data.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(
    tableName = "expenses",
    indices = {
        @Index(value = "title", unique = true)
    }
)
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseEntity {

    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "expense_id")
    public Integer expenseId;

    @NotNull
    public String title;

    @NotNull
    public String date;

    @ColumnInfo(name = "total_price")
    public Long totalPrice;
}
