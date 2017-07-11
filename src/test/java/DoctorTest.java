import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

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
      con.createQuery(deletePatientsQuery).executeUpdate();
      con.createQuery(deleteDoctorsQuery).executeUpdate();
    }
  }

  @Test
  public void Doctor_instantiatesCorrectly_true() {
    Doctor newDoctor = new Doctor("John Doe", "orthopedics");
    assertEquals(true, newDoctor instanceof Doctor);
  }

  @Test
  public void getId_doctorInstantiatesName() {
    Doctor newDoctor = new Doctor("John Doe", "pediatrics");
    assertEquals("John Doe", newDoctor.getName());
  }

  // @Test
  // public void getId_doctorsInstantiateWithAnId() {
  //   Doctor newDoctor = new Doctor("John Doe", "obstetrics");
  //   assertTrue(newDoctor.getId() > 0);
  // }

  @Test
  public void add_newDoctorAddedIntoDB_true() {
    Doctor newDoctor = new Doctor("Dr. Pepper", "dentistry");
    newDoctor.add();
    Doctor savedDoctor = Doctor.find(newDoctor.getId());
    assertEquals(savedDoctor.getId(), newDoctor.getId());
  }
}
