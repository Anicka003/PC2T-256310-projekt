package student_databaze;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpravceDatabaze {
    private static final String DB_FILE = "students.db";
    private static final String CREATE_TABLE_SQL = 
            "CREATE TABLE IF NOT EXISTS studenti (" +
            "id INTEGER PRIMARY KEY, " +
            "jmeno TEXT NOT NULL, " +
            "prijmeni TEXT NOT NULL, " +
            "rokNarozeni INTEGER NOT NULL, " +
            "typ TEXT NOT NULL, " +
            "znamky TEXT)";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
    }

    public void inicializujDatabazi() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            System.err.println("Chyba pri inicializaci databaze: " + e.getMessage());
        }
    }

    public void ulozStudenty(StudentDatabaze databaze) {
        String deleteSQL = "DELETE FROM studenti";
        String insertSQL = "INSERT INTO studenti (id, jmeno, prijmeni, rokNarozeni, typ, znamky) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
            
            deleteStmt.executeUpdate();
            
            for (Student student : databaze.vratVsechnyStudentySerazene()) {
                insertStmt.setInt(1, student.getId());
                insertStmt.setString(2, student.getJmeno());
                insertStmt.setString(3, student.getPrijmeni());
                insertStmt.setInt(4, student.getRokNarozeni());
                insertStmt.setString(5, student.getClass().getSimpleName());
                
                String znamky = student.getZnamky().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(","));
                insertStmt.setString(6, znamky.isEmpty() ? null : znamky);
                
                insertStmt.addBatch();
            }
            
            insertStmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Chyba pri ukladani do databaze: " + e.getMessage());
        }
    }

    public void nactiStudenty(StudentDatabaze databaze) {
        String sql = "SELECT id, jmeno, prijmeni, rokNarozeni, typ, znamky FROM studenti ORDER BY id";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            int maxId = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String jmeno = rs.getString("jmeno");
                String prijmeni = rs.getString("prijmeni");
                int rokNarozeni = rs.getInt("rokNarozeni");
                String typ = rs.getString("typ");
                
                String znamkyStr = rs.getString("znamky");
                List<Integer> znamky = new ArrayList<>();
                if (znamkyStr != null) {
                    for (String z : znamkyStr.split(",")) {
                        znamky.add(Integer.parseInt(z));
                    }
                }
                
                Student student;
                if ("TeleStudent".equals(typ)) {
                    student = new TeleStudent(id, jmeno, prijmeni, rokNarozeni);
                } else if ("KyberStudent".equals(typ)) {
                    student = new KyberStudent(id, jmeno, prijmeni, rokNarozeni);
                } else {
                    continue; 
                }
                
                for (int znamka : znamky) {
                    student.pridejZnamku(znamka);
                }
                
                databaze.pridejStudenta(student);
                
                if (id > maxId) {
                    maxId = id;
                }
            }
            
            if (maxId > 0) {
                databaze.nastavDalsiId(maxId + 1);
            }
        }
            catch (SQLException e) {
            System.err.println("Chyba pri nacitani z databaze: " + e.getMessage());
            }
    }
}