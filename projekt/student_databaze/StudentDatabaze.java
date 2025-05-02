package student_databaze;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDatabaze {
    private Map<Integer, Student> studenti = new HashMap<>();
    private int dalsiId = 1;

    public void pridejStudenta(Student student) {
        if (studenti.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student s ID " + student.getId() + " jiz existuje!");
        }
        studenti.put(student.getId(), student);
    }

    public boolean odeberStudenta(int id) {
        return studenti.remove(id) != null;
    }

    public Student najdiStudenta(int id) {
        return studenti.get(id);
    }

    public List<Student> vratVsechnyStudentySerazene() {
        return studenti.values().stream()
            .sorted(Comparator.comparing(Student::getPrijmeni))
            .collect(Collectors.toList());
    }

    public double vratPrumerOboru(Class<? extends Student> typObory) {
        List<Student> studentiVOboru = studenti.values().stream()
            .filter(typObory::isInstance)
            .collect(Collectors.toList());
        
        if (studentiVOboru.isEmpty()) {
            return 0.0;
        }
        
        double suma = 0.0;
        for (Student student : studentiVOboru) {
            suma += student.vypoctiPrumer();
        }
        
        return suma / studentiVOboru.size();
    }

    public int vratPocetStudentuVOboru(Class<? extends Student> typObory) {
        return (int) studenti.values().stream()
            .filter(typObory::isInstance)
            .count();
    }

    public int generujNoveId() {
        return dalsiId++;
    }
    
    public void nastavDalsiId(int id) {
        this.dalsiId = id;
    }

}