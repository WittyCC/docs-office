import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class PatientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/docs_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deletePatientsQuery = "DELETE FROM patients *;";
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      String deleteSpecialtysQuery = "DELETE FROM specialtys *;";
      con.createQuery(deletePatientsQuery).executeUpdate();
      con.createQuery(deleteDoctorsQuery).executeUpdate();
      con.createQuery(deleteSpecialtysQuery).executeUpdate();
    }
  }

  @Test
  public void Patient_instantiatesCorrectly_true() {
    Patient newPatient = new Patient("John Doe", "09-03-1980", 1);
    assertEquals(true, newPatient instanceof Patient);
  }

  @Test
  public void getId_patientInstantiatesName() {
    Patient newPatient = new Patient("John Doe", "09-03-1980", 1);
    assertEquals("John Doe", newPatient.getName());
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Patient firstPatient = new Patient("Jane", "birthday", 1);
    Patient secondPatient = new Patient("Jane", "birthday", 1);
    assertTrue(firstPatient.equals(secondPatient));
  }

  @Test
  public void save_savesDoctorIdIntoDB_true() {
    Doctor myDoctor = new Doctor("Dr. No", 1);
    myDoctor.add();
    Patient myPatient = new Patient("Mr. Green", "birthday goes here", myDoctor.getId());
    myPatient.add();
    Patient savedPatient = Patient.find(myPatient.getId());
    assertEquals(savedPatient.getDoctorId(), myDoctor.getId());
  }

  // @Test
  // public void getId_patientsInstantiateWithAnId() {
  //   Patient newPatient = new Patient("John Doe", "09-03-1980", 1);
  //   assertTrue(newPatient.getId() > 0);
  // }

  // @Test
  // public void add_newPatientAddedIntoDB_true() {
  //
  //   Patient newPatient = new Patient("John Doe", "09-03-1980", 1);
  //   newPatient.add();
  //   Patient savedPatient = Patient.find(newPatient.getId());
  //   assertEquals(savedPatient.getId(), newPatient.getId());
  // }
}
