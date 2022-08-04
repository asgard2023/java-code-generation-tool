package org.ccs.generator.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    private String driverClassName = "";
    private String url = "";
    private String username = "";
    private String password = "";

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        try {
            Class.forName(driverClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        Connection conn = null;
        Properties props = new Properties();
        try {
            DriverManager.setLoginTimeout(20);// 超时时间2"
            props.setProperty("user", username);
            props.setProperty("password", password);
            //设置可以获取remarks信息
            props.setProperty("remarks", "true");
            //设置可以获取tables remarks信息
            props.setProperty("useInformationSchema", "true");
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConn(Connection conn) {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

}
