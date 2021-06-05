package com.orm.example.entity;

public class IClass {
    private String no;
    private String name;
    private int stuNum;

    public IClass() {
    }

    public IClass(String no, String name, int stuNum) {
        this.no = no;
        this.name = name;
        this.stuNum = stuNum;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStuNum() {
        return stuNum;
    }

    public void setStuNum(int stuNum) {
        this.stuNum = stuNum;
    }

    public String attributeString(){
        return no+name+stuNum;
    }

    @Override
    public String toString() {
        return "IClass{" +
                "no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", stuNum=" + stuNum +
                '}';
    }
}