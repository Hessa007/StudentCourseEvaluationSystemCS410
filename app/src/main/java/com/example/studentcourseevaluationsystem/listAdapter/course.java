package com.example.studentcourseevaluationsystem.listAdapter;

public class course {

    private int ref_num;
    private String code;
    private String number;
    private String name;
    private String section;
    private String teacher;
    private  String status;

    public course(int ref_num, String code, String number, String name, String section, String teacher, String status) {
        this.ref_num = ref_num;
        this.code = code;
        this.number = number;
        this.name = name;
        this.section = section;
        this.teacher = teacher;
        this.status = status;
    }

    public int getRef_num() {
        return ref_num;
    }

    public void setRef_num(int ref_num) {
        this.ref_num = ref_num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
