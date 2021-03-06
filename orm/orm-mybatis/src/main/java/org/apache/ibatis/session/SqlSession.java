package org.apache.ibatis.session;


import org.apache.ibatis.executor.Executor;

import java.lang.reflect.Method;
import java.sql.Connection;

public class SqlSession {
    private SqlSessionFactory sqlSessionFactory;
    private Connection connection;
    private Executor executor;
    boolean isUse = false;


    public SqlSession(SqlSessionFactory sqlSessionFactory, Connection conn, boolean isUse) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.connection = conn;
        this.executor = this.sqlSessionFactory.getExecutor(this);
        this.isUse = isUse;
    }

    public <T> T run(Method method, Object[] args){
        this.isUse = true;
        return executor.run(method,args,connection);
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection conn) {
        this.connection = conn;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        isUse = use;
    }
}
