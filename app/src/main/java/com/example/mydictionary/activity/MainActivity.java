package com.example.mydictionary.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydictionary.R;
import com.example.mydictionary.database.DBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView mAutoCompleteTextView;
    private DBHelper mDBHelper;
    private ArrayList<String> newList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        mDBHelper = new DBHelper(this, "sample.db", 1);
        try {
            mDBHelper.checkDB();
            mDBHelper.openDatabase();
        } catch (Exception e) {
        }
        newList = new ArrayList<>();

        mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    newList.addAll(mDBHelper.getEngWords());
                    mAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1));

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String word = newList.get(i);
                Toast.makeText(MainActivity.this, word, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findViews() {
        mAutoCompleteTextView = findViewById(R.id.autotxt);
    }
}