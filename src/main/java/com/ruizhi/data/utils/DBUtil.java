package com.ruizhi.data.utils;

import lombok.Data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

/**
 * 数据库连接工具类
 * Created by lvjie on 2020/7/27
 */
@Data
public class DBUtil {

    /**
     * 数据库类型 mysql/oracle
     */
    private String srcTyp;

    /**
     * 驱动
     */
    private String driver;

    /**
     * url
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private Connection con;

    public DBUtil() {
        this.driver = "com.mysql.jdbc.Driver";
        this.url = "jdbc:mysql://127.0.0.1:3306/data_resource?serverTimezone=Asia/Shanghai";
        this.username = "root";
        this.password = "root";
    }

    public DBUtil(String srcTyp,String ipAdr,String prt,String usr,String pwd,String istNam) {
        if (srcTyp.equalsIgnoreCase("mysql")) {
            this.driver = "com.mysql.jdbc.Driver";
            this.url = "jdbc:mysql://"+ipAdr+":"+prt+"/"+istNam+"?serverTimezone=Asia/Shanghai";
        } else if (srcTyp.equalsIgnoreCase("oracle")) {
            this.driver = "com.mysql.jdbc.Driver";
        }
        this.username = usr;
        this.password = pwd;
    }

    public Connection getConnection() throws Exception {
        Class.forName(driver);
        con = DriverManager.getConnection(url, username, password);
        return con;
    }

}
