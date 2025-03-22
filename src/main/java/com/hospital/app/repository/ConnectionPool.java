package com.hospital.app.repository;

import com.hospital.app.exceptions.DBException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
/**
 * This class is one of the strategies for organizing connections in the application, using HikariCP
 *
 * Please see the {@link com.zaxxer.hikari.HikariDataSource} class for true identity
 * @author Sinkevych Olena
 *
 */
public class ConnectionPool {
    private static HikariDataSource dataSource = null;
    private static ConnectionPool connectionPool = null;

    /**
     * <p>This method is used to initialize object ConnectionPool
     * </p>
     *
     */
    private ConnectionPool() throws DBException {
        Properties p=new Properties ();
        try {
            ClassLoader classLoader = ConnectionPool.class.getClassLoader();
            File file = new File(classLoader.getResource(Constants.SETTINGS_FILE).getFile());
            FileInputStream inputStream = new FileInputStream(file);
            p.load (inputStream);
        } catch (IOException e) {
            throw new DBException(e.getMessage());
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(p.getProperty("url"));
        config.setUsername(p.getProperty("user"));
        config.setPassword(p.getProperty("password"));
        config.setDriverClassName("org.postgresql.Driver");
        config.addDataSourceProperty("currentSchema", "hospital_schema");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("minimumIdle", p.getProperty("minimumIdle"));
        config.addDataSourceProperty("maximumPoolSize", p.getProperty("maximumPoolSize"));

        dataSource = new HikariDataSource(config);
    }

    public static synchronized void getInstance() throws DBException {
        if(connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
    }

    public static Connection getConnection() throws SQLException, DBException {
        getInstance();
        return dataSource.getConnection();
    }
}
