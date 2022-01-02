package com.jnbulls.simaodt.Data.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jnbulls.simaodt.Data.Entities.*;
import com.jnbulls.simaodt.Data.DB.Dao.*;

@Database(entities = {DetallesOdtEntity.class, Informes.class, Materiales.class, Motivos.class, OdtEntity.class, Zonas.class, Users.class}, exportSchema = true,
        version = 2)

public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance; //porque es volatil la instancia?
    public abstract DetallesOdtDao detallesOdtDao();
    public abstract InformesDao informesDao();
    public abstract MaterialesDao materialesDao();
    public abstract MotivosDao motivosDao();
    public abstract OdtDao odtDao();
    public abstract ZonasDao zonasDao();
    public abstract UsersDao usersDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null)
                    instance = buildDatabase(context);
            }
        }
        return instance;
    }

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "odtdatabase")
                .addMigrations(MIGRATION_1_2)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        //OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(SeedDatabaseWorker.class).build();
                        //WorkManager.getInstance().enqueue(workRequest);
                    }
                }).build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `users` (`idUser` INTEGER NOT NULL, "
                    + "`email` TEXT, `password` TEXT, PRIMARY KEY(`idUser`))");
        }
    };

    public static void destroyDatabase() {
        instance = null;
    }


}
