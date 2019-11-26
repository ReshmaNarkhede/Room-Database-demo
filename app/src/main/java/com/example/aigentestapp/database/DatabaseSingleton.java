package com.example.aigentestapp.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseSingleton {
    private static MyAppDatabase myAppDataBase;

    public static MyAppDatabase GetDatabase(Context context) {
        if (myAppDataBase == null)
            myAppDataBase = Room.databaseBuilder(context, MyAppDatabase.class, "AigenTech")
                    .allowMainThreadQueries()
                    .build();

        return myAppDataBase;

    }



//     Room.databaseBuilder(getApplicationContext(), MyDb.class, "database-name")
//            .addMigrations(MIGRATION_1_2,MIGRATION_1_3, MIGRATION_2_3).build();
//
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
//                    + "`name` TEXT, PRIMARY KEY(`id`))");
//        }
//    };
//
//    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Book "
//                    + " ADD COLUMN pub_year INTEGER");
//        }
//    };
//
//    static final Migration MIGRATION_1_3 = new Migration(1, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Book "
//                    + " ADD COLUMN pub_year INTEGER");
//        }
//    };
}
