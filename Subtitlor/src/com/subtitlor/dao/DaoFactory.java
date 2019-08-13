package com.subtitlor.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.subtitlor.conf.SubtitlorConfiguration;

public class DaoFactory {
    private String url;
    private String username;
    private String password;

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        }

        DaoFactory instance = new DaoFactory(
                "jdbc:mysql://localhost:" + SubtitlorConfiguration.DATABASE_PORT
                + "/" + SubtitlorConfiguration.DATABASE_NAME + "?"
                + "useUnicode=true"
                + "&characterEncoding=UTF-8"
                + "&useJDBCCompliantTimezoneShift=true"
                + "&useLegacyDatetimeCode=false"
                + "&serverTimezone=UTC", 
                SubtitlorConfiguration.DATABASE_LOGIN, 
                SubtitlorConfiguration.DATABASE_PWD);
        return instance;
    }

    public Connection getConnection() throws SQLException {
    	Connection connexion = DriverManager.getConnection(url, username, password);
        connexion.setAutoCommit(false);
        return connexion; 
    }

    public BlockDao getBlockDao() {
        return new BlockDaoImpl(this);
    }
}