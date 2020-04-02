package com.example.studentcourseevaluationsystem.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseController extends SQLiteOpenHelper {
    private static final String databaseName = "finalproject";
    public databaseController(Context context) {
        super(context, databaseName, null, 1);
    }//end of constructor
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS students(studentId INTEGER PRIMARY KEY" +
                                                      ", firstName VARCHAR, lastName VARCHAR" +
                                                      ", nationalId INTEGER, nationality VARCHAR" +
                                                      ",college VARCHAR, program VARCHAR" +
                                                      ",level VARCHAR, semester VARCHAR);";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS mycourse(ref_num INTEGER PRIMARY KEY" +
                ", code VARCHAR, number VARCHAR" +
                ", name VARCHAR, section VARCHAR" +
                ", teacher VARCHAR, status VARCHAR)";
        //drop colom status
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS student_course(Id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", student_Id INTEGER , course_Id VARCHAR" +
                ", evaluationState VARCHAR)";
        db.execSQL(query);

    }//end onCreate()

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }//end onUpgrade()
}//end of class



//        String query="INSERT INTO mycourse values(44112,"+
//                "'عال'"+","+
//                "'210ت'"+","+
//                "'برمجة جوال'"+","+
//                "'8C9'"+","+
//                "'أ-روان العمري '"+","+
//                "''"+")";
//        database.execSQL(query);
//
//        query="INSERT INTO mycourse values(44113,"+
//                "'عال'"+","+
//                "'310ت'"+","+
//                "'الرؤية ومعالجة الصور'"+","+
//                "'6C2'"+","+
//                "'د.مريم حجوني '"+","+
//                "''"+")";
//        database.execSQL(query);
//
//        query="INSERT INTO mycourse values(44114,"+
//                "'ريض'"+","+
//                "'201ت'"+","+
//                "'تفاضل وتكامل1'"+","+
//                "'1C2'"+","+
//                "'د.أمل محمود '"+","+
//                "''"+")";
//        database.execSQL(query);
//
//        query="INSERT INTO mycourse values(44115,"+
//                "'نفس'"+","+
//                "'101ت'"+","+
//                "'مدخل لعلم النفس'"+","+
//                "'1F2'"+","+
//                "'د.ميساء صالح '"+","+
//                "''"+")";
//        database.execSQL(query);
//
//        query= "INSERT INTO student_course(student_Id,course_Id) values(437004614,44112)";
//        database.execSQL(query);
//        query= "INSERT INTO student_course(student_Id,course_Id) values(437004614,44113)";
//        database.execSQL(query);
//        query= "INSERT INTO student_course(student_Id,course_Id) values(437004614,44114)";
//        database.execSQL(query);
//        query= "INSERT INTO student_course(student_Id,course_Id) values(437004614,44115)";
//        database.execSQL(query);




//        dbcontoller = new databaseController(getContext());
//        database = dbcontoller.getReadableDatabase();
//        cursor = database.rawQuery("SELECT * FROM mycourse", null);
//        if (cursor.moveToNext()) {
//            Log.d("course id",cursor.getString(cursor.getColumnIndex("ref_num")));
//        }
