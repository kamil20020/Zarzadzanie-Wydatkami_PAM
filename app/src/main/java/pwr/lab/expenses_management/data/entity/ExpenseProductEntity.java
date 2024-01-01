package pwr.lab.expenses_management.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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
    tableName = "expenses_products",
    foreignKeys = {
        @ForeignKey(
            entity = ExpenseEntity.class,
            parentColumns = "expense_id",
            childColumns = "expense_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = ProductEntity.class,
            parentColumns = "product_id",
            childColumns = "product_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseProductEntity {

    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "expense_product_id")
    public Integer expenseProductId;

    @NotNull
    @ColumnInfo(name = "expense_id", index = true)
    public Integer expenseId;

    @NotNull
    @ColumnInfo(name = "product_id", index = true)
    public Integer productId;

    @NotNull
    public Long price;

    @NotNull
    public Integer count;
}
