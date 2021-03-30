package com.example.myapplication.database;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.  In a real
 * app, consider exporting the schema to help you with migrations.
 */

@Database(entities = {Word.class, Sleep.class, Item.class}, version = 2, exportSchema = false)
abstract class WordRoomDatabase extends RoomDatabase {

    abstract WordDao wordDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile com.example.myapplication.database.WordRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static com.example.myapplication.database.WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (com.example.myapplication.database.WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            com.example.myapplication.database.WordRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onCreate method to populate the database.
     * For this sample, we clear the database every time it is created.
     */
    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                WordDao dao = INSTANCE.wordDao();
                dao.deleteAll();
                dao.deleteAllSleep();
                dao.deleteAllitem();
                fakeData(dao);
                Item item1 = new Item(1, "Bronze Thread", R.drawable.bronzethread, 5, 3);
                Item item2 = new Item(2, "Silver Thread", R.drawable.silverthread, 10, 2);
                Item item3 = new Item(3, "Gold Thread", R.drawable.goldthread, 15, 1);

                dao.insertItem(item1);
                dao.insertItem(item2);
                dao.insertItem(item3);
//                Date currentTime = Calendar.getInstance().getTime();
//                String time = DateFormat.getInstance().format(currentTime);

//                Date currentTime = Calendar.getInstance().getTime();

                //string to Date
                // System.out.println("Date is: "+d);
//
//                Word word = new Word("Hello",dateToStr, dateToStr);
//                dao.insert(word);
//                word = new Word("World",dateToStr, dateToStr);
//                dao.insert(word);
            });
        }
    };

    public static void fakeData(WordDao dao){
        Date currentTime = Calendar.getInstance().getTime();

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(currentTime);
        cal.add(Calendar.DATE, -1);
        Date preTime = cal.getTime();

        String time = DateFormat.getInstance().format(currentTime);
        dao.insertDuration(new Sleep(time, 360));
        dao.insertDuration(new Sleep(DateFormat.getInstance().format(preTime), 480));

        cal = new GregorianCalendar();
        cal.setTime(currentTime);
        cal.add(Calendar.DATE, -1);
        Date preTime2days = cal.getTime();

        dao.insertDuration(new Sleep(DateFormat.getInstance().format(preTime2days), 720));


        cal = new GregorianCalendar();
        cal.setTime(currentTime);
        cal.add(Calendar.DATE, -2);
        Date preTime3days = cal.getTime();

        dao.insertDuration(new Sleep(DateFormat.getInstance().format(preTime3days), 100));

        cal = new GregorianCalendar();
        cal.setTime(currentTime);
        cal.add(Calendar.DATE, -3);
        Date preTime4days = cal.getTime();

        dao.insertDuration(new Sleep(DateFormat.getInstance().format(preTime4days), 750));
    }


}
