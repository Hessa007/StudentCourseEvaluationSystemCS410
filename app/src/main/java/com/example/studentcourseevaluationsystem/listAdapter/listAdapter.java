package com.example.studentcourseevaluationsystem.listAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.studentcourseevaluationsystem.R;

import java.util.ArrayList;

public class listAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<course> courses = new ArrayList<course>();

    public listAdapter(Context mContext, ArrayList<course> courses) {
        this.mContext = mContext;
        this.courses=courses;
    }//end constructor

    @Override
    public int getCount() {
        return courses.size();
    }//end getCount()

    @Override
    public Object getItem(int i) {
        return courses.get(i);
    }//end getItem()

    @Override
    public long getItemId(int i) {
        return 0;
    }//end getItemId()

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.course_card, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.courseName.setText(String.format("%s%s-%s", courses.get(position).getCode(), courses.get(position).getNumber(), courses.get(position).getName()));
        viewHolder.courseTeacher.setText(courses.get(position).getTeacher());
        viewHolder.courseSection.setText(String.format("%s-%d", courses.get(position).getSection(), courses.get(position).getRef_num()));
        viewHolder.courseStatus.setText(courses.get(position).getStatus());
        return convertView;
    }//end getView()

    private class ViewHolder {
        TextView courseName;
        TextView courseSection;
        TextView courseTeacher;
        TextView courseStatus;

        public ViewHolder(View view) {
            courseName = view.findViewById(R.id.courseNameTV);
            courseSection = view.findViewById(R.id.courseSectionTV);
            courseTeacher=view.findViewById(R.id.courseTeacherTV);
            courseStatus=view.findViewById(R.id.courseStatueTV);
        }//end constructor
    }//end viewHolder()
}//end class

