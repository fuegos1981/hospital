package com.epam.hospital.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {
    private static HikariDataSource dataSource = null;
    private static ConnectionPool connectionPool = null;
    private ConnectionPool(){
        Properties p=new Properties ();
        try {
            ClassLoader classLoader = ConnectionPool.class.getClassLoader();
            File file = new File(classLoader.getResource(Constants.SETTINGS_FILE).getFile());
            FileInputStream inputStream = new FileInputStream(file);
            p.load (inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(p.getProperty("url"));
        config.setUsername(p.getProperty("user"));
        config.setPassword(p.getProperty("password"));
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("minimumIdle", p.getProperty("minimumIdle"));
        config.addDataSourceProperty("maximumPoolSize", p.getProperty("maximumPoolSize"));

        dataSource = new HikariDataSource(config);
    }

    public static synchronized ConnectionPool getInstance() {
        if(connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}