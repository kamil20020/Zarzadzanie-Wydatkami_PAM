package pwr.lab.expenses_management.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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
    tableName = "products",
    indices = {
        @Index(value = "name", unique = true)
    },
    foreignKeys = {
        @ForeignKey(
            entity = ProductCategoryEntity.class,
            parentColumns = "product_category_id",
            childColumns = "category_id",
            onDelete = ForeignKey.SET_NULL
        )
    }
)
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    public Integer productId;

    @ColumnInfo(name = "category_id", index = true)
    public Integer categoryId;

    @NotNull
    public String name;
}
