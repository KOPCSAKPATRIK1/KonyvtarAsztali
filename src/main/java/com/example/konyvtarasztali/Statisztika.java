package com.example.konyvtarasztali;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Statisztika {
    private static List<Konyv> _konyvek;
    private static Adatbazis _db;

    public static void main(String[] args) {
        try {
            Beolvas();
            System.out.printf("500 oldalnál hosszabb könyvek száma: %d\n", Hosszabb500());
            System.out.printf("%s 1950-nél régebbi könyv\n", Regebbi1950()? "Van" : "Nincs");
            System.out.printf("A leghosszabb könyv:\n%s\n", Leghosszabb());
            System.out.printf("A legtöbb könyvvel rendelkező szerző: %s\n", Legtobb());
            String cim = Cim();
            int hanyszor = Hanyszor(cim);
            if (hanyszor > 0) {
                System.out.printf("A megadott köny %dx szer lett kölcsönözve\n", hanyszor);
            } else {
                System.out.println("Nincs ilyen könyv");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void Beolvas() throws SQLException {
        _konyvek = new Adatbazis().getAll();
    }

   private static long Hosszabb500() {
        return _konyvek.stream().filter(konyv -> konyv.getPage_count() > 500).count();
   }

   private static boolean Regebbi1950() {
        return _konyvek.stream().anyMatch(konyv -> konyv.getPublish_year() < 1950);
   }

   private static Konyv Leghosszabb() {
        return _konyvek.stream().max(Comparator.comparingInt(Konyv::getPage_count)).get();
   }

   private static String Legtobb() {
        return _konyvek.stream()
                .collect(Collectors.groupingBy(Konyv::getAuthor, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .get().getKey();
   }

    private static String Cim() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Adjon meg egy könyv címet: ");
        return sc.nextLine();
    }

    private static int Hanyszor(String cim) {
        int szamlalo = 0;
        for (Konyv konyv: _konyvek) {
            if (konyv.getTitle().equals(cim)) {
                szamlalo++;
            }
        }
        return szamlalo;
    }


}
