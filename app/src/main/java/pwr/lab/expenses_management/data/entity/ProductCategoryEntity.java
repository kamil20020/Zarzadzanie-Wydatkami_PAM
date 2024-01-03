package pwr.lab.expenses_management.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(
    tableName = "products_categories",
    indices = {
        @Index(value = "name", unique = true)
    }
)
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_category_id")
    public Integer productId;
    @NotNull
    public String name;
}
