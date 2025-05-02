package student_databaze;

import java.util.ArrayList;
import java.util.List;

public abstract class Student {
    private int id;
    private String jmeno;
    private String prijmeni;
    private int rokNarozeni;
    private List<Integer> znamky;

    public Student(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
        this.znamky = new ArrayList<>();
    }

    public abstract String provedDovednost();

    public double vypoctiPrumer() {
        if (znamky.isEmpty()) {
            return 0.0;
        }
        double suma = 0.0;
        for (int znamka : znamky) {
            suma += znamka;
        }
        return suma / znamky.size();
    }

    public void pridejZnamku(int znamka) {
        if (znamka >= 1 && znamka <= 5) {
            znamky.add(znamka);
        } else {
            throw new IllegalArgumentException("Znamka musi byt v rozmezi 1-5");
        }
    }

    public int getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public int getRokNarozeni() {
        return rokNarozeni;
    }

    public List<Integer> getZnamky() {
        return new ArrayList<>(znamky);
    }

    @Override
    public String toString() {
        return String.format("%d: %s %s (%d), prumer: %.2f", 
            id, jmeno, prijmeni, rokNarozeni, vypoctiPrumer());
    }
}
