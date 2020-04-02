package com.example.studentcourseevaluationsystem.activities_fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentcourseevaluationsystem.R;
import com.example.studentcourseevaluationsystem.database.databaseController;

public class LoginActivity extends AppCompatActivity {
    EditText student_idET, national_idET;
    databaseController dbcontoller = new databaseController(this);
    SQLiteDatabase database;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        student_idET = findViewById(R.id.student_idET);
        national_idET = findViewById(R.id.national_idET);
    }//end onCreate()

    public void login(View view) {
        database = dbcontoller.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM students where studentId=" + student_idET.getText()+" and nationalId="+national_idET.getText(), null);
        if (cursor.moveToFirst()) {//student exists
            editor.putBoolean("is_login", true); // Storing boolean - true/false
            editor.putString("first_name", cursor.getString(cursor.getColumnIndex("firstName"))); // Storing string
            editor.putString("last_name", cursor.getString(cursor.getColumnIndex("lastName"))); // Storing string
            editor.putInt("student_id", cursor.getInt(cursor.getColumnIndex("studentId"))); // Storing int
            editor.commit(); // commit changes
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            student_idET.setError("login error");
            national_idET.setError("login error");
        }//end if-else block
    }//end login()
}//end activity
