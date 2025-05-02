package student_databaze;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SpravceSouboru {
    
    public void ulozStudentyDoSouboru(List<Student> studenti, String nazevSouboru) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nazevSouboru))) {
            for (Student student : studenti) {
                String typ = student instanceof TeleStudent ? "T" : "K";
                String znamky = student.getZnamky().stream()
                                    .map(Object::toString)
                                    .reduce("", (a, b) -> a.isEmpty() ? b : a + "," + b);
                
                writer.println(String.format("%d;%s;%s;%d;%s;%s",
                    student.getId(),
                    student.getJmeno(),
                    student.getPrijmeni(),
                    student.getRokNarozeni(),
                    typ,
                    znamky));
            }
            System.out.println("Data byla uspesne ulozena do souboru: " + nazevSouboru);
        } catch (IOException e) {
            System.err.println("Chyba pri ukladani do souboru: " + e.getMessage());
        }
    }

    public List<Student> nactiStudentyZeSouboru(String nazevSouboru) {
        List<Student> studenti = new ArrayList<>();
        File soubor = new File(nazevSouboru);
        
        if (!soubor.exists()) {
            System.err.println("Soubor " + nazevSouboru + " neexistuje!");
            return studenti;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(nazevSouboru))) {
            String radek;
            while ((radek = reader.readLine()) != null) {
                String[] casti = radek.split(";");
                if (casti.length < 6) continue;

                int id = Integer.parseInt(casti[0]);
                String jmeno = casti[1];
                String prijmeni = casti[2];
                int rokNarozeni = Integer.parseInt(casti[3]);
                String typ = casti[4];
                String[] znamkyArray = casti[5].split(",");

                Student student;
                if (typ.equals("T")) {
                    student = new TeleStudent(id, jmeno, prijmeni, rokNarozeni);
                } else {
                    student = new KyberStudent(id, jmeno, prijmeni, rokNarozeni);
                }

                for (String z : znamkyArray) {
                    if (!z.isEmpty()) {
                        student.pridejZnamku(Integer.parseInt(z));
                    }
                }

                studenti.add(student);
            }
            System.out.println("Data byla uspesne nactena ze souboru: " + nazevSouboru);
        } catch (IOException e) {
            System.err.println("Chyba pri nacitani ze souboru: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Chyba ve formatu souboru: " + e.getMessage());
        }
        
        return studenti;
    }
}

