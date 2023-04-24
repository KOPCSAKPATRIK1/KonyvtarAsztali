package com.example.konyvtarasztali;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Adatbazis {
    private Connection conn;
    private static final String DB_DRIVER = "mysql";
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "books";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public Adatbazis() throws SQLException {
        String url = String.format("jdbc:%s://%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_NAME);
        conn = DriverManager.getConnection(url, DB_USER, DB_PASS);
    }

    public List<Konyv> getAll() throws SQLException {
        List<Konyv> konyvek = new ArrayList<>();
        String sql = "SELECT * FROM books";
        Statement stat = conn.createStatement();
        ResultSet resultSet = stat.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            int publish_year = resultSet.getInt("publish_year");
            int page_count = resultSet.getInt("page_count");
            Konyv konyv = new Konyv(id, title, author, publish_year, page_count);
            konyvek.add(konyv);
        }

        return konyvek;
    }

    public boolean DeleteKonyv(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        return statement.executeUpdate() > 0;
    }
}
