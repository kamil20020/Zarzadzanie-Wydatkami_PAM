package pwr.lab.expenses_management.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import pwr.lab.expenses_management.data.dao.ExpenseDAO;
import pwr.lab.expenses_management.data.dao.ExpenseProductDAO;
import pwr.lab.expenses_management.data.dao.ProductCategoryDAO;
import pwr.lab.expenses_management.data.dao.ProductDAO;
import pwr.lab.expenses_management.data.entity.ExpenseEntity;
import pwr.lab.expenses_management.data.entity.ExpenseProductEntity;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;
import pwr.lab.expenses_management.data.entity.ProductEntity;

@Database(
    entities = {
        ProductCategoryEntity.class,
        ProductEntity.class,
        ExpenseEntity.class,
        ExpenseProductEntity.class
    },
    version = 2,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE = null;

    public abstract ProductCategoryDAO productCategoryDAO();
    public abstract ProductDAO productDAO();

    public abstract ExpenseDAO expenseDAO();

    public abstract ExpenseProductDAO expenseProductDAO();

    public static AppDatabase getDatabase(Context context){

        if(INSTANCE != null){
            return INSTANCE;
        }

        synchronized (AppDatabase.class) {
            INSTANCE = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "app_database"
            )
            .addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    AsyncTask.execute(() -> {

                        db.beginTransaction();

                        String loadCategoriesSQL = """
                            INSERT INTO PRODUCTS_CATEGORIES (name)
                            VALUES
                                ('Moda'),
                                ('Kuchnia'),
                                ('Dom'),
                                ('Warsztat i auto'),
                                ('Ogród i balkon'),
                                ('Sport i wypoczynek');
                        """;

                        String loadProductsSQL = """
                            INSERT INTO PRODUCTS_CATEGORIES (name)
                            VALUES
                                ('Moda'),
                                ('Kuchnia'),
                                ('Dom'),
                                ('Warsztat i auto'),
                                ('Ogród i balkon'),
                                ('Sport i wypoczynek');
                        """;

                        String loadeExpensesSQL = """
                            INSERT INTO EXPENSES (date, title, total_price)
                            VALUES
                                ('23.09.2023', 'McDonald - Drwal', 25000),
                                ('12.06.2023', 'McDonald - BigMac', 18000),
                                ('31.12.2023', 'Pizza Hut - Sylwester', 50000);
                        """;

                        db.execSQL(loadCategoriesSQL);
                        //db.execSQL(loadProductsSQL);
                        db.execSQL(loadeExpensesSQL);

                        db.setTransactionSuccessful();
                        db.endTransaction();
                    });
                }
            })
            .build();

            return INSTANCE;
        }
    }
}
