package com.example.konyvtarasztali;

import java.sql.SQLException;
import java.util.List;

public class Statisztika {
    private static List<Konyv> _konyvek;
    private static Adatbazis _db;

    public static void main(String[] args) {
        try {
            Beolvas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void Beolvas() throws SQLException {
        _konyvek = new Adatbazis().getAll();
    }

    private static void Feladatok() {

    }
}
