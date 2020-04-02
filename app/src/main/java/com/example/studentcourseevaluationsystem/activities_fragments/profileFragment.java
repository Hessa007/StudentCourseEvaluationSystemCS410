package com.example.studentcourseevaluationsystem.activities_fragments;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studentcourseevaluationsystem.R;
import com.example.studentcourseevaluationsystem.database.databaseController;

public class profileFragment extends Fragment {
    SharedPreferences pref;
    databaseController dbcontoller;
    SQLiteDatabase database;
    EditText studentName,studentId,nationalId,nationality,college,program,level,semester;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
       View view=inflater.inflate(R.layout.fragment_profile, container, false);
        studentName=view.findViewById(R.id.student_name);
        studentId=view.findViewById(R.id.student_id);
        nationalId=view.findViewById(R.id.student_nationalId);
        nationality=view.findViewById(R.id.student_nationality);
        college=view.findViewById(R.id.student_college);
        program=view.findViewById(R.id.student_program);
        level=view.findViewById(R.id.student_level);
        semester=view.findViewById(R.id.student_year);
        return view;
    }//end onCreateView

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("الملف الشخصي");
        pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        int student_id = pref.getInt("student_id", 0);
        String firstname = pref.getString("first_name", "null");
        String lastname = pref.getString("last_name", "null");
        dbcontoller = new databaseController(getContext());
        fillProfileData(student_id,firstname,lastname);
    }//end onViewCreated()

    private void fillProfileData(int student_id,String firstName,String lastName) {
        database = dbcontoller.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM students where studentId=" + student_id, null);
        if (cursor.moveToFirst()) {//student exists
            studentId.setText(student_id+"");
            studentName.setText(firstName+" "+lastName);
            nationalId.setText(cursor.getInt(cursor.getColumnIndex("nationalId"))+"");
            nationality.setText(cursor.getString(cursor.getColumnIndex("nationality")));
            college.setText(cursor.getString(cursor.getColumnIndex("college")));
            program.setText(cursor.getString(cursor.getColumnIndex("program")));
            level.setText(cursor.getString(cursor.getColumnIndex("level")));
            semester.setText(cursor.getString(cursor.getColumnIndex("semester")));
        }//end if
    }//end fillProfileData(int student_id)
}//end profile Fragment
