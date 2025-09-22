package main;

import java.io.*;
import java.util.*;
import java.util.regex.*;

class Rekord {
    int kod;
    String nev;
    String szuletes;
    int fizetes;

    Rekord(int kod, String nev, String szuletes, int fizetes) {
        this.kod = kod;
        this.nev = nev.length() > 25 ? nev.substring(0, 25) : nev;
        this.szuletes = szuletes;
        this.fizetes = fizetes;
    }
        
    @Override
    public String toString() {
        return String.format("%03d;%s;%s;%d", kod, nev, szuletes, fizetes);
    }

    static Rekord fromString(String line) {
        String[] parts = line.split(";");
        return new Rekord(
            Integer.parseInt(parts[0]),
            parts[1],
            parts[2],
            Integer.parseInt(parts[3])
        );
    }

    
    
    static boolean isValidDate(String date) {
        return Pattern.matches("\\d{4}-\\d{2}-\\d{2}", date);
    }
}

public class feladat1 {
    static final String FILE_NAME = "rekordok.txt";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        writeInitialRecords();
        
        listAvailableCodes();
        
        System.out.print("Kód keresése: ");
        int kod = scanner.nextInt();
        searchRecord(kod);

               
        System.out.print("Törlendő kód: ");
        int torlendoKod = scanner.nextInt();
        deleteRecord(torlendoKod);

        sortRecords();
        System.out.println("Rekordok rendezve.");
    }
    
    

	static void writeInitialRecords() throws IOException {
        List<Rekord> records = Arrays.asList(
            new Rekord(110, "Eks Elek", "1980-05-12", 1570234),
            new Rekord(102, "Kalim Pál", "1975-11-03", 2670234),
            new Rekord(104, "Fekete Péter", "1990-07-21", 3670234),
            new Rekord(103, "Fehér Péter", "1988-03-15", 4670234),
            new Rekord(109, "Szürke Peter", "1995-09-30", 5670234),
            new Rekord(106, "Szabo Péter", "1984-03-15", 4670234),
            new Rekord(107, "Szép Péter", "1985-03-15", 4670234),
            new Rekord(108, "Fekete Lajos", "1986-03-15", 4623470),
            new Rekord(105, "Szürke Lahos", "1987-03-15", 4672340),
            new Rekord(111, "Szabo Anna", "1988-03-15", 4623470),
            new Rekord(113, "Kiss Gergely", "2001-04-22", 3670234),
            new Rekord(1119, "Barta Zsuzsanna", "1998-12-05", 2670234),
            new Rekord(118, "Oláh Dániel", "1995-07-19", 1670234),
            new Rekord(112, "Varga Noémi", "2000-03-30", 4670234),
            new Rekord(115, "Takács Áron", "1993-11-11", 5670234)
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Rekord r : records) {
                writer.write(r.toString());
                writer.newLine();
            }
        }
    }
	
	static void listAvailableCodes() throws IOException {
        System.out.println("Elérhető kódok:");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Rekord r = Rekord.fromString(line);
                System.out.print(", " + r.kod);
            }
            System.out.println();
        }
    }
	
    static void searchRecord(int kod) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                Rekord r = Rekord.fromString(line);
                if (r.kod == kod) {
                    System.out.println("Talált rekord:");
                    System.out.println("Kód: " + r.kod);
                    System.out.println("Név: " + r.nev);
                    System.out.println("Születési idő: " + r.szuletes);
                    System.out.println("Fizetés: " + r.fizetes);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Nincs ilyen rekord a fájlban.");
            }
        }
    }

    static void deleteRecord(int kodToDelete) throws IOException {
        List<Rekord> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Rekord r = Rekord.fromString(line);
                if (r.kod != kodToDelete) {
                    records.add(r);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Rekord r : records) {
                writer.write(r.toString());
                writer.newLine();
            }
        }

        System.out.println("Rekord törölve, ha létezett.");
}

    static void sortRecords() throws IOException {
        List<Rekord> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(Rekord.fromString(line));
            }
        }

        records.sort(Comparator.comparingInt(r -> r.kod));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Rekord r : records) {
                writer.write(r.toString());
                writer.newLine();
            }
        }
    }
}
