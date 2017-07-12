import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class DoctorTest {

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
  public void Doctor_instantiatesCorrectly_true() {
    Doctor newDoctor = new Doctor("John Doe", 1);
    assertEquals(true, newDoctor instanceof Doctor);
  }

  @Test
  public void getId_doctorInstantiatesName() {
    Doctor newDoctor = new Doctor("John Doe", 1);
    assertEquals("John Doe", newDoctor.getName());
  }

  // @Test
  // public void getId_doctorsInstantiateWithAnId() {
  //   Doctor newDoctor = new Doctor("John Doe", "obstetrics");
  //   assertTrue(newDoctor.getId() > 0);
  // }

  @Test
  public void save_newDoctorSavedIntoDB_true() {
    Doctor newDoctor = new Doctor("Dr. Pepper", 1);
    newDoctor.save();
    Doctor savedDoctor = Doctor.find(newDoctor.getId());
    assertEquals(savedDoctor.getId(), newDoctor.getId());
  }

  @Test
  public void all_returnsAllInstancesOfDoctor_true() {
    Doctor firstDoctor = new Doctor("Dr. Jane Doe", 1);
    firstDoctor.save();
    Doctor secondDoctor = new Doctor("Dr. Witty Chang", 2);
    secondDoctor.save();
    assertEquals(true, Doctor.all().get(0).equals(firstDoctor));
    assertEquals(true, Doctor.all().get(1).equals(secondDoctor));
  }

  @Test
  public void save_assignsIdToObject() {
    Doctor myDoctor = new Doctor("Dr. Pepper", 1);
    myDoctor.save();
    Doctor savedDoctor = Doctor.all().get(0);
    assertEquals(myDoctor.getId(), savedDoctor.getId());
  }

  @Test
  public void getPatients_retrievesALlPatientsFromDatabase_tasksList() {
    Doctor myDoctor = new Doctor("Dr. Thoreson", 1);
    myDoctor.save();
    Patient firstPatient = new Patient("Patient 1", "birthday1", myDoctor.getId());
    firstPatient.save();
    Patient secondPatient = new Patient("Patient 2", "birthday2", myDoctor.getId());
    secondPatient.save();
    Patient[] patients = new Patient[] { firstPatient, secondPatient };
    assertTrue(myDoctor.getPatients().containsAll(Arrays.asList(patients)));
  }
}
