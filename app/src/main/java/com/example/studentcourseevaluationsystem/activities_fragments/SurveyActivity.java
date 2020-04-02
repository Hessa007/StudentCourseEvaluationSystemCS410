package com.example.studentcourseevaluationsystem.activities_fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentcourseevaluationsystem.R;
import com.example.studentcourseevaluationsystem.database.databaseController;

public class SurveyActivity extends AppCompatActivity {
    MediaPlayer mp;
    String[] survey_question = {"كان المقرر الدراسي مشوق", "المقرر الدراسي له علاقة بتخصصي ومرتبط به", "معلومات المقرر الدراسي حديثة "
            , "يحتوي المنهج الدراسي على أمثلة وتجارب عملية", "المقرر الدراسي وصل لمستوى توقعاتي أو تخطاها"};
    int current_Q = 0;
    double surveyResult = 0.0;
    RadioGroup answerRG;
    TextView questionTV;
    Button next;
    String course_ref;
    databaseController dbcontoller = new databaseController(this);
    SQLiteDatabase database;
    TextView courseTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_survey);
        Intent intent = getIntent();
        course_ref=intent.getStringExtra("course_ref");
        questionTV = findViewById(R.id.questionTV);
        answerRG = findViewById(R.id.answerRG);
        next = findViewById(R.id.nextBTN);
        courseTitle=findViewById((R.id.courseTitleTV));

        setCourseName();
        //display first question
        questionTV.setText(survey_question[current_Q]);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNextQuestion();
            }
        });
    }//end onCreate()

    private void setCourseName() {
        database = dbcontoller.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM mycourse where ref_num="+course_ref, null);
        if (cursor.moveToFirst()) {
            String code=cursor.getString(cursor.getColumnIndex("code")); // Storing string
            String number=cursor.getString(cursor.getColumnIndex("number"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            courseTitle.setText(String.format("%s%s%s-%s","تقييم مقرر ",code,number,name ));
        }//end if
    }//end setCourseName

    private void goToNextQuestion() {
        int radioButtonID = answerRG.getCheckedRadioButtonId();
        if (radioButtonID == -1) {
            Toast.makeText(this, "يجب تحديد أحد الخيارات قبل الانتقال إلى السؤال التالي", Toast.LENGTH_SHORT).show();
        } else {
            //1 register value to satisfaction rate
            View radioButton = answerRG.findViewById(radioButtonID);
            int idx = answerRG.indexOfChild(radioButton);//0= full mark, 4=0
            surveyResult += (((float)(idx - 4) / 4) * -1);
            //check is last question?
            if (current_Q == 4) {
                surveyComplete();
            } else {
                current_Q += 1;

                //2 display next question
                questionTV.setText(survey_question[current_Q]);
                //3 clear selection
                answerRG.clearCheck();
                if (current_Q == 4) {
                    //now on last question
                    next.setText("إنهاء");
                }//end if change button text
            }//end if else survey completed?
        }//end if else is radio button checked?
    }//end goToNextQuestion();

    private void surveyComplete() {
        //hide button+ radioGroup
        next.setVisibility(View.GONE);
        answerRG.setVisibility(View.GONE);
        //satisfaction rate
        double finalSatisfyRate=(surveyResult/5)*100;
        //add evaluationState
        updateEvaluationState(finalSatisfyRate);
        //display thank you message
        questionTV.setText("تم تعبئة الاستبانة بنجاح!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("تم تعبئة الاستبانة بنجاح!");
        builder.setMessage("نسبة رضاك عن المقرر "+finalSatisfyRate+"%");
        builder.setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dismiss dialog
                Intent intent=new Intent(SurveyActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }//end onClick
        });//end set negative button
        builder.show();
    }//end surveyComplete

    private void updateEvaluationState(double finalSatisfyRate) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        int student_id = pref.getInt("student_id", 0);

        dbcontoller = new databaseController(this);
        database = dbcontoller.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("evaluationState", String.valueOf(finalSatisfyRate));
            String whereClause = "course_Id = ? AND student_Id = ?";
            String whereArgs[] = {course_ref,String.valueOf(student_id)};
            database.update("student_course", contentValues, whereClause, whereArgs);
        mp = MediaPlayer.create(this, R.raw.chill_music);
    }//end updateEvaluationState

    public void musicOn(View view) {
        mp = MediaPlayer.create(this, R.raw.chill_music);
        mp.start();
    }//end musicOn()

    public void musicOff(View view) {
        mp.stop();
    }//end musicOff()
}//end Activity

