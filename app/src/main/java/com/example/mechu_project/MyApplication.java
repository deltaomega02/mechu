package com.example.mechu_project;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

public class MyApplication extends Application {
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db;
    private static final String PREFS_NAME = "DatabasePrefs";
    private static final String KEY_DB_CREATED = "isDatabaseCreated";

    @Override
    public void onCreate() {
        super.onCreate();

        // 최초 실행 여부 및 DB 생성 확인
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isDatabaseCreated = prefs.getBoolean(KEY_DB_CREATED, false);

        dbHelper = new DatabaseHelper(this);

        if (!isDatabaseCreated) {
            dbHelper.deleteDatabase(this);
            dbHelper = new DatabaseHelper(this);
            db = dbHelper.getWritableDatabase();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(KEY_DB_CREATED, true);
            editor.apply();
        } else {
            db = dbHelper.getWritableDatabase();
        }
    }

    public static SQLiteDatabase getDatabase() {
        return db;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
