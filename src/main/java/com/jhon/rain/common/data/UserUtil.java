package com.jhon.rain.common.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhon.rain.common.utils.EncryptUtil;
import com.jhon.rain.entity.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUtil {

  private static void createUser(int count) throws Exception {
    List<User> users = new ArrayList<User>(count);
    //生成用户
    for (int i = 0; i < count; i++) {
      User user = new User();
      user.setMobile("130000000" + i);
      user.setLoginCount(1);
      user.setNickname("user" + i);
      user.setRegisterDate(new Date());
      user.setSalt("1a2b3c");
      user.setPassword(EncryptUtil.inputPassToDbPass("123456", user.getSalt()));
      users.add(user);
    }
    System.out.println("create user");
    //插入数据库
    /*
    Connection conn = DBUtil.getConn();
    String sql = "INSERT INTO t_user(login_count, nickname, register_date, salt, password, mobile)VALUES(?,?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    for (int i = 0; i < users.size(); i++) {
      User user = users.get(i);
      pstmt.setInt(1, user.getLoginCount());
      pstmt.setString(2, user.getNickname());
      pstmt.setTimestamp(3, new Timestamp(user.getRegisterDate().getTime()));
      pstmt.setString(4, user.getSalt());
      pstmt.setString(5, user.getPassword());
      pstmt.setString(6, user.getMobile());
      pstmt.addBatch();
    }
    pstmt.executeBatch();
    pstmt.close();
    conn.close();
    System.out.println("insert to db");
    */

    //登录，生成token
    String urlString = "http://localhost:9090/login/doLogin";
    File file = new File("E:/IdeaWork/rainbow-seckill/tokens.txt");
    if (file.exists()) {
      file.delete();
    }
    RandomAccessFile raf = new RandomAccessFile(file, "rw");
    file.createNewFile();
    raf.seek(0);
    for (int i = 0; i < users.size(); i++) {
      User user = users.get(i);
      URL url = new URL(urlString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      OutputStream out = connection.getOutputStream();
      String params = "mobile=" + user.getMobile() + "&password=" + EncryptUtil.inputPassToFormPass("123456");
      out.write(params.getBytes());
      out.flush();
      InputStream inputStream = connection.getInputStream();
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      byte buff[] = new byte[1024];
      int len = 0;
      while ((len = inputStream.read(buff)) >= 0) {
        bout.write(buff, 0, len);
      }
      inputStream.close();
      bout.close();
      String response = new String(bout.toByteArray());
      JSONObject jo = JSON.parseObject(response);
      String token = jo.getString("data");
      System.out.println("create token : " + user.getMobile());

      String row = user.getMobile() + "," + token;
      raf.seek(raf.length());
      raf.write(row.getBytes());
      raf.write("\r\n".getBytes());
      System.out.println("write to file : " + user.getMobile());
    }
    raf.close();

    System.out.println("over");

  }

  public static void main(String[] args) throws Exception {
    createUser(5000);
  }
}
