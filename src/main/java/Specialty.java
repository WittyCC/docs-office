import java.util.List;
import org.sql2o.*;
import java.util.Arrays;

public class Specialty {
  private String name;
  private int id;

  public Specialty(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public void add() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO specialtys(name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public static Specialty find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM specialtys where id=:id";
      Specialty specialty = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Specialty.class);
      return specialty;
    }
  }

    public static List<Specialty> all() {
    String sql = "SELECT name FROM specialtys";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Specialty.class);
    }
  }

  public List<Doctor> getDoctors() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM doctors WHERE specialtyId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Doctor.class);
    }
  }

  @Override
  public boolean equals(Object otherSpecialty) {
    if (!(otherSpecialty instanceof Specialty)) {
      return false;
    } else {
      Specialty newSpecialty = (Specialty) otherSpecialty;
      return this.getName().equals(newSpecialty.getName());
    }
  }
}
