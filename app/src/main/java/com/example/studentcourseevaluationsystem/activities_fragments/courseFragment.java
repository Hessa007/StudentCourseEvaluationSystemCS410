package com.example.studentcourseevaluationsystem.activities_fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studentcourseevaluationsystem.R;
import com.example.studentcourseevaluationsystem.activities_fragments.SurveyActivity;
import com.example.studentcourseevaluationsystem.database.databaseController;
import com.example.studentcourseevaluationsystem.listAdapter.course;
import com.example.studentcourseevaluationsystem.listAdapter.listAdapter;

import java.util.ArrayList;
import java.util.List;

public class courseFragment extends Fragment {
    databaseController dbcontoller = new databaseController(getContext());
    SQLiteDatabase database;
    String evaluationStatus;
    View v;
    listAdapter ca;
    private ArrayList<course> mycourses = new ArrayList<course>();
    private ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        return v;
    }//end onCreateView

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("المواد الدراسية");
        fillData();
        lv = v.findViewById(R.id.lv);
        ca = new listAdapter(getContext(), mycourses);
        ca.notifyDataSetChanged();
        lv.setAdapter(ca);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mycourses.get(position).getStatus().matches("لم يتم التقييم بعد")){
                    Intent intent = new Intent(getActivity(), SurveyActivity.class);
                    intent.putExtra("course_ref", mycourses.get(position).getRef_num() + "");
                    startActivity(intent);
                }else{//في حال تم التقييم
                    Toast.makeText(getContext(),"لقد قمت بتقييم المقرر سابقاً",Toast.LENGTH_LONG).show();
                }//end if else block
            }//end onItemClick
        });//end setOnItemClickListener
    }//end onViewCreated()

    private void fillData() {
        mycourses.clear();
        List<Integer> student_courses_id = new ArrayList<>(); //dynamic list
        student_courses_id.clear();
        dbcontoller = new databaseController(getContext());
        database = dbcontoller.getReadableDatabase();
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        int student_id = pref.getInt("student_id", 0);
        Cursor cursor = database.rawQuery("SELECT course_Id FROM student_course where student_Id=" + student_id, null);
        cursor.moveToNext();
        while (!cursor.isAfterLast()) {
            student_courses_id.add(cursor.getInt(cursor.getColumnIndex("course_Id")));
            cursor.moveToNext();
        }//end while
        dbcontoller = new databaseController(getContext());
        database = dbcontoller.getReadableDatabase();
        for (int i = 0; i < student_courses_id.size(); i++) {
            cursor = database.rawQuery("SELECT * FROM mycourse where ref_num=" + student_courses_id.get(i), null);
            //since no two courses have the same ref_num (PK) then no need for loop
            if (cursor.moveToNext()) {
                int ref_num = cursor.getInt(cursor.getColumnIndex("ref_num"));
                String code = cursor.getString(cursor.getColumnIndex("code"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String section = cursor.getString(cursor.getColumnIndex("section"));
                String teacher = cursor.getString(cursor.getColumnIndex("teacher"));
                String status = "لم يتم التقييم بعد";
                mycourses.add(new course(ref_num, code, number, name, section, teacher, status));
            }//end cursor if
        }//end of student course loop
        dbcontoller = new databaseController(getContext());
        database = dbcontoller.getReadableDatabase();
        for (int i = 0; i < mycourses.size(); i++) {
            cursor = database.rawQuery("SELECT * FROM student_course where course_Id=" + mycourses.get(i).getRef_num() + " AND student_Id=" + student_id, null);
            //since no two courses have the same ref_num (PK) then no need for loop
            if (cursor.moveToNext()) {
                String status = cursor.getString(cursor.getColumnIndex("evaluationState"));
                if (status != null) {
                    mycourses.get(i).setStatus(String.format("%s%s%s", "تم التقييم بنجاح، نسبة الرضا ", status, "%"));
                }//end if
            }//end cursor if
        }//end of student course loop
    }//end fillData()
}//end courseFragment()
