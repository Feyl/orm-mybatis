package com.orm.example.mapper;

import com.orm.example.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface StudentMapper {
//    @Select("select s.*,c.name className from student s join class c on s.class_no=c.no")
    List<Student> selectAll();
//    @Select("select s.*,c.name className from student s join class c on s.classNo=c.no limit #{startNo},#{pageSize}")
    List<Student> selectList(@Param("startNo") int startNo, @Param("pageSize") int pageSize);
    @Insert("insert into student values(#{no},#{name},#{department},#{major},#{classNo},#{admissionDate})")
    Integer insertStu(String no,String name, String department, String major, String classNo, Date admissionDate);
//    boolean insertStu(Student stu,String classNo);
    @Update("update student set name=#{name},department=#{department},major=#{major},class_no=#{classNo},admission_date=#{admissionDate} where no=#{no}")
    Integer updateByNo(String name, String department, String major, String classNo, Date admissionDate, String no);
//    boolean updateByNo(Student stu,String classNo);
    @Delete("delete from student where no = #{no}")
    Integer deleteByNo(String no);
    @Select("select count(no) stuNum from student")
    Long queryStuNum();
}