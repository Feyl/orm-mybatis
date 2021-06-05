package com.orm.example;



import com.orm.example.mapper.AdminMapper;
import com.orm.example.mapper.ClassMapper;
import com.orm.example.mapper.StudentMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.Date;

public class TemporaryTest {
    AdminMapper adminMapper;
    ClassMapper classMapper;
    StudentMapper studentMapper;

    @Before
    public void init(){
        // 使用弱引用创建SqlSessionFactoryBuilder,保证下次GC时回收该对象。
        WeakReference<SqlSessionFactoryBuilder> builder = new WeakReference<>(new SqlSessionFactoryBuilder());
        String mapxmlPath = "mapper";
        SqlSessionFactory factory = builder.get().build(mapxmlPath);
        adminMapper = factory.getMapper(AdminMapper.class);
        classMapper = factory.getMapper(ClassMapper.class);
        studentMapper = factory.getMapper(StudentMapper.class);
    }

    @Test
    public void TestAdmin(){
//        System.out.println(adminMapper.loginQuery("1", "111"));
//        adminMapper.updatePassword("1","111");
    }

    @Test
    public void TestClass(){
//        classMapper.insertClass(new IClass("119230501","人工智能",0));
//        classMapper.deleteByNo("119230501");
//        System.out.println(classMapper.selectNameList());
//        System.out.println(classMapper.selectAll());
//        System.out.println(classMapper.selectList(0,9));
//        System.out.println(classMapper.selectNoByName("软件工程二班"));
//        System.out.println(classMapper.updateStuNumByClassNo("119230202", 1));
//        System.out.println(classMapper.queryStuNumByNo("119230202"));
//        System.out.println(classMapper.queryClassNum());
//        System.out.println(classMapper.updateInfoByNo(new IClass("119230202", "软件工程", 0)));
    }

    @Test
    public void TestStudent(){
//        System.out.println(studentMapper.selectAll());
//        System.out.println(studentMapper.selectList(0, 9));
//        System.out.println(studentMapper.queryStuNum());
//        System.out.println(studentMapper.deleteByNo("11923020208"));
//        studentMapper.insertStu("119230211","doker","人工智能","CS","119230202",new Date());
        System.out.println(studentMapper.updateByNo("Doker", "人工智能", "CS", "119230202", new Date(), "119230211"));
    }
}
