package pwr.lab.expenses_management.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import pwr.lab.expenses_management.data.dao.ProductCategoryDAO;
import pwr.lab.expenses_management.data.dao.ProductDAO;
import pwr.lab.expenses_management.data.entity.ProductCategoryEntity;
import pwr.lab.expenses_management.data.entity.ProductEntity;

@Database(entities = {ProductCategoryEntity.class, ProductEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE = null;

    public abstract ProductCategoryDAO productCategoryDAO();
    public abstract ProductDAO productDAO();

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

                        String sql = """
                        INSERT INTO PRODUCTS_CATEGORIES (name)
                        VALUES
                            ('Moda'),
                            ('Kuchnia'),
                            ('Dom'),
                            ('Warsztat i auto'),
                            ('Ogród i balkon'),
                            ('Sport i wypoczynek')
                    """;

                        db.execSQL(sql);

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
