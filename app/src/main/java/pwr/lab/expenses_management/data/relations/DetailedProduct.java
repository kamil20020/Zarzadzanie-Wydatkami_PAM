package pwr.lab.expenses_management.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import lombok.Data;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;
import pwr.lab.expenses_management.data.entity.ProductEntity;

@Data
public class DetailedProduct {

    @Embedded
    public ProductEntity productEntity;

    @Relation(parentColumn = "category_id", entityColumn = "product_category_id")
    public ProductCategoryEntity categoryEntity;
}
