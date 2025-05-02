package student_databaze;

import java.util.Map;
import java.util.HashMap;

public class TeleStudent extends Student {
    private static final Map<Character, String> MORSEOVKA = new HashMap<>();
    
    static {
        MORSEOVKA.put('A', ".-");
        MORSEOVKA.put('B', "-...");
        MORSEOVKA.put('C', "-.-.");
        MORSEOVKA.put('D', "-..");
        MORSEOVKA.put('E', ".");
        MORSEOVKA.put('F', "..-.");
        MORSEOVKA.put('G', "--.");
        MORSEOVKA.put('H', "....");
        MORSEOVKA.put('I', "..");
        MORSEOVKA.put('J', ".---");
        MORSEOVKA.put('K', "-.-");
        MORSEOVKA.put('L', ".-..");
        MORSEOVKA.put('M', "--");
        MORSEOVKA.put('N', "-.");
        MORSEOVKA.put('O', "---");
        MORSEOVKA.put('P', ".--.");
        MORSEOVKA.put('Q', "--.-");
        MORSEOVKA.put('R', ".-.");
        MORSEOVKA.put('S', "...");
        MORSEOVKA.put('T', "-");
        MORSEOVKA.put('U', "..-");
        MORSEOVKA.put('V', "...-");
        MORSEOVKA.put('W', ".--");
        MORSEOVKA.put('X', "-..-");
        MORSEOVKA.put('Y', "-.--");
        MORSEOVKA.put('Z', "--..");
    }

    public TeleStudent(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public String provedDovednost() {
        String celeJmeno = (getJmeno() + " " + getPrijmeni()).toUpperCase();
        StringBuilder morseuvKod = new StringBuilder();
        
        for (char c : celeJmeno.toCharArray()) {
            String znak = MORSEOVKA.get(c);
            if (znak != null) {
                morseuvKod.append(znak).append(" ");
            }
        }
        
        return morseuvKod.toString().trim();
    }
}
