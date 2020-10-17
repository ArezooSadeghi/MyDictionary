package com.example.mydictionary.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private String mDbName;
    private Context mContext;
    private String mDbPath;
    private String mTableName = "Dictionary";
    private String mEngCol = "English";



    public DBHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, name, null, version);
        mContext = context;
        mDbName = name;
        mDbPath = "/data/data/";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void checkDB() {
        SQLiteDatabase checkDB = null;
        try {
            String filePath = mDbPath + mDbName;
            checkDB = SQLiteDatabase.openDatabase(filePath, null, 0);
        } catch (Exception e) {
            if (checkDB != null) {
                Log.d("checkDB", "database already exists!");
                checkDB.close();
            } else {
                createDatabase();
            }
        }
    }

    public void createDatabase() {
        this.getReadableDatabase();
        try {
            InputStream is = mContext.getAssets().open(mDbName);
            OutputStream os = new FileOutputStream(mDbPath + mDbName);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDatabase() {
        String filePath = mDbPath + mDbName;
        SQLiteDatabase.openDatabase(filePath, null, 0);
    }

    public ArrayList<String> getEngWords() {
        ArrayList<String> engList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(mTableName,
                new String[]{mEngCol},
                mEngCol + "LIKE ?",
                new String[]{"%"},
                null,
                null, mEngCol);
        int index = cursor.getColumnIndex(mEngCol);
        while (cursor.moveToNext()) {
            engList.add(cursor.getString(index));
        }

        sqLiteDatabase.close();
        cursor.close();
        return engList;
    }
}
