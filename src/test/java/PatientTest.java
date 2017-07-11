import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class PatientTest {

  @Test
  public void Patient_instantiatesCorrectly_true() {
    Patient newPatient = new Patient("John Doe", 09-03-1980);
    assertEquals(true, newPatient instanceof Patient);
  }

  @Test
  public void getId_patientInstantiatesWithAnId_1() {
    // Patient.clear();
    Patient newPatient = new Patient("John Doe", 09-03-1980);
    assertEquals("John Doe", newPatient.getName());
  }
}
