package com.example.mydictionary.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mydictionary.model.Word;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class WordDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "dic.db";
    public static final String DB_PATH = "/data/data/com.example.mydictionary/databases/";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public WordDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DB_NAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public List<Word> getWords() {
        Word word = null;
        List<Word> words = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.query("dictionary",
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor == null || cursor.getCount() == 0) {
            return words;
        }
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(0);
                String mean = cursor.getString(1);
                word = new Word(name, mean);
                words.add(word);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
            closeDatabase();
        }
        return words;
    }

    public boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(WordDBHelper.DB_NAME);
            OutputStream outputStream =
                    new FileOutputStream(WordDBHelper.DB_PATH + WordDBHelper.DB_NAME);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
