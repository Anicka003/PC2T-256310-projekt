package student_databaze;

public class KyberStudent extends Student {
    public KyberStudent(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public String provedDovednost() {
        String celeJmeno = getJmeno() + getPrijmeni();
        int hash = 7;

        for (char c : celeJmeno.toCharArray()) {
        	hash = hash * 31 + c;
        }

        return Integer.toHexString(hash);
    }
}