package com.jhon.rain.common.data;


import java.sql.Connection;
import java.sql.DriverManager;


public class DBUtil {


  public static Connection getConn()
          throws Exception {
    String url = "jdbc:mysql://localhost/rainbow-seckill?characterEncoding=utf-8&useSSL=false";
    String username = "root";
    String password = "root";
    String driver = "com.mysql.jdbc.Driver";
    Class.forName(driver);
    return DriverManager.getConnection(url, username, password);
  }
}
