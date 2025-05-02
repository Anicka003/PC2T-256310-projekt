package student_databaze;

import java.util.List;
import java.util.Scanner;

public class UzivatelskeRozhrani {
    private StudentDatabaze databaze;
    private SpravceSouboru spravceSouboru;
    private Scanner scanner;
    private SpravceDatabaze spravceDatabaze;

    public UzivatelskeRozhrani() {
        this.databaze = new StudentDatabaze();
        this.spravceSouboru = new SpravceSouboru();
        this.scanner = new Scanner(System.in);
        this.spravceDatabaze = new SpravceDatabaze();
        spravceDatabaze.inicializujDatabazi();
        spravceDatabaze.nactiStudenty(databaze);
    }

    public void zobrazMenu() {
        while (true) {
            System.out.println("\n===== MENU STUDENTSKE DATABAZE =====");
            System.out.println("1. Pridat noveho studenta");
            System.out.println("2. Pridat znamku studentovi");
            System.out.println("3. Odebrat studenta");
            System.out.println("4. Zobrazit informace o studentovi");
            System.out.println("5. Provedeni dovednosti studenta");
            System.out.println("6. Zobrazit vsechny studenty (abecedne)");
            System.out.println("7. Zobrazit prumery oboru");
            System.out.println("8. Zobrazit pocty studentu v oborech");
            System.out.println("9. Ulozit data do souboru");
            System.out.println("10. Nacist data ze souboru");
            System.out.println("0. Konec");
            System.out.print("Volba: ");

            int volba;
            try {
                volba = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Neplatny vstup! Zadejte cislo volby.");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();

            switch (volba) {
                case 1: pridejNovehoStudenta(); break;
                case 2: pridejZnamkuStudentovi(); break;
                case 3: odeberStudenta(); break;
                case 4: zobrazInfoOStudentovi(); break;
                case 5: provedDovednostStudenta(); break;
                case 6: zobrazVsechnyStudenty(); break;
                case 7: zobrazPrumeryOboru(); break;
                case 8: zobrazPoctyStudentu(); break;
                case 9: ulozData(); break;
                case 10: nactiData(); break;
                case 0: 
                	spravceDatabaze.ulozStudenty(databaze);
                    System.out.println("Ukoncuji program...");
                    return;
                default:
                    System.out.println("Neplatna volba! Zadejte cislo 0-10.");
            }
        }
    }

    private void pridejNovehoStudenta() {
        System.out.println("\n=== Pridani noveho studenta ===");
        
        System.out.println("Vyberte obor:");
        System.out.println("1. Telekomunikace");
        System.out.println("2. Kyberneticka bezpecnost");
        System.out.print("Volba (1-2): ");
        
        int typ;
        try {
            typ = scanner.nextInt();
            if (typ < 1 || typ > 2) {
                System.out.println("Neplatna volba oboru!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Neplatny vstup! Musite zadat cislo.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();
        
        System.out.print("Jmeno: ");
        String jmeno = scanner.nextLine().trim();
        
        System.out.print("Prijmeni: ");
        String prijmeni = scanner.nextLine().trim();
        
        System.out.print("Rok narozeni: ");
        int rokNarozeni;
        try {
            rokNarozeni = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Neplatny rok! Musite zadat cislo.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();
        
        int id = databaze.generujNoveId();
        Student student;
        
        if (typ == 1) {
            student = new TeleStudent(id, jmeno, prijmeni, rokNarozeni);
        } else {
            student = new KyberStudent(id, jmeno, prijmeni, rokNarozeni);
        }
        
        databaze.pridejStudenta(student);
        System.out.printf("Student %s %s byl uspesne pridan s ID: %d\n", 
                         jmeno, prijmeni, id);
    }

    private void pridejZnamkuStudentovi() {
        System.out.println("\n=== Pridani znamky studentovi ===");
        System.out.print("Zadejte ID studenta: ");
        
        int id;
        try {
            id = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Neplatne ID! Musite zadat cislo.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();
        
        Student student = databaze.najdiStudenta(id);
        if (student == null) {
            System.out.println("Student s ID " + id + " nebyl nalezen.");
            return;
        }
        
        System.out.print("Zadejte znamku (1-5): ");
        int znamka;
        try {
            znamka = scanner.nextInt();
            if (znamka < 1 || znamka > 5) {
                System.out.println("Znamka musi byt v rozmezi 1-5!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Neplatna znamka! Musite zadat cislo.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();
        
        student.pridejZnamku(znamka);
        System.out.printf("Znamka %d byla uspesne pridana studentovi %s %s (ID: %d). Aktualni prumer: %.2f\n",
                         znamka, student.getJmeno(), student.getPrijmeni(), 
                         student.getId(), student.vypoctiPrumer());
    }

    private void odeberStudenta() {
        System.out.println("\n=== Odebrani studenta ===");
        System.out.print("Zadejte ID studenta k odebrani: ");
        
        int id;
        try {
            id = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Neplatne ID! Musite zadat cislo.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();
        
        Student student = databaze.najdiStudenta(id);
        if (student == null) {
            System.out.println("Student s ID " + id + " nebyl nalezen.");
            return;
        }
        
        if (databaze.odeberStudenta(id)) {
            System.out.printf("Student %s %s (ID: %d) byl uspesne odebran.\n",
                            student.getJmeno(), student.getPrijmeni(), id);
        } else {
            System.out.println("Nepodarilo se odebrat studenta.");
        }
    }

    private void zobrazInfoOStudentovi() {
        System.out.println("\n=== Informace o studentovi ===");
        System.out.print("Zadejte ID studenta: ");
        
        int id;
        try {
            id = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Neplatne ID! Musite zadat cislo.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();
        
        Student student = databaze.najdiStudenta(id);
        if (student == null) {
            System.out.println("Student s ID " + id + " nebyl nalezen.");
            return;
        }
        
        System.out.println("\n=== Detail studenta ===");
        System.out.println("ID: " + student.getId());
        System.out.println("Jmeno: " + student.getJmeno());
        System.out.println("Prijmeni: " + student.getPrijmeni());
        System.out.println("Rok narozeni: " + student.getRokNarozeni());
        System.out.println("Obor: " + (student instanceof TeleStudent ? 
                                     "Telekomunikace" : "Kyberneticka bezpecnost"));
        System.out.println("Pocet znamek: " + student.getZnamky().size());
        System.out.printf("Studijni prumer: %.2f\n", student.vypoctiPrumer());
        
        if (!student.getZnamky().isEmpty()) {
            System.out.print("Znamky: ");
            for (int z : student.getZnamky()) {
                System.out.print(z + " ");
            }
            System.out.println();
        }
    }

    private void provedDovednostStudenta() {
        System.out.println("\n=== Provedeni dovednosti studenta ===");
        System.out.print("Zadejte ID studenta: ");
        
        int id;
        try {
            id = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Neplatne ID! Musite zadat cislo.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();
        
        Student student = databaze.najdiStudenta(id);
        if (student == null) {
            System.out.println("Student s ID " + id + " nebyl nalezen.");
            return;
        }
        
        String vysledek = student.provedDovednost();
        System.out.println("\nVysledek dovednosti:");
        System.out.println(vysledek);
    }

    private void zobrazVsechnyStudenty() {
        List<Student> studenti = databaze.vratVsechnyStudentySerazene();
        
        if (studenti.isEmpty()) {
            System.out.println("\nV databazi nejsou zadni studenti.");
            return;
        }
        
        System.out.println("\n=== Seznam vsech studentu (razeno abecedne) ===");
        System.out.println("Telekomunikacni studenti:");
        for (Student student : studenti) {
            if (student instanceof TeleStudent) {
                System.out.println(student);
            }
        }
        
        System.out.println("\nKyberneticti studenti:");
        for (Student student : studenti) {
            if (student instanceof KyberStudent) {
                System.out.println(student);
            }
        }
    }

    private void zobrazPrumeryOboru() {
        System.out.println("\n=== Studijni prumery oboru ===");
        System.out.printf("Telekomunikace: %.2f\n", 
                        databaze.vratPrumerOboru(TeleStudent.class));
        System.out.printf("Kyberneticka bezpecnost: %.2f\n", 
                        databaze.vratPrumerOboru(KyberStudent.class));
    }

    private void zobrazPoctyStudentu() {
        System.out.println("\n=== Pocty studentu v oborech ===");
        System.out.println("Telekomunikace: " + 
                         databaze.vratPocetStudentuVOboru(TeleStudent.class));
        System.out.println("Kyberneticka bezpecnost: " + 
                         databaze.vratPocetStudentuVOboru(KyberStudent.class));
    }

    private void nactiData() {
        System.out.println("\n=== Nacteni dat ze souboru ===");
        System.out.print("Zadejte nazev souboru (napr. studenti.txt): ");
        String nazevSouboru = scanner.nextLine().trim();
        
        List<Student> nacteniStudenti = spravceSouboru.nactiStudentyZeSouboru(nazevSouboru);
        
        if (!nacteniStudenti.isEmpty()) {
            int maxId = nacteniStudenti.stream()
                .mapToInt(Student::getId)
                .max()
                .orElse(0);
            
            databaze.nastavDalsiId(maxId + 1);

            for (Student student : nacteniStudenti) {
                databaze.pridejStudenta(student);
            }
            
            System.out.println("Nacteno " + nacteniStudenti.size() + " studentu.");
        } else {
            System.out.println("Nebyl nacten zadny student.");
        }
    }

    private void ulozData() {
        System.out.println("\n=== Ulozeni dat do souboru ===");
        System.out.print("Zadejte nazev souboru (napr. studenti.txt): ");
        String nazevSouboru = scanner.nextLine().trim();
        
        List<Student> studenti = databaze.vratVsechnyStudentySerazene();
        spravceSouboru.ulozStudentyDoSouboru(studenti, nazevSouboru);
    }
}
