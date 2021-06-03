import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable {

  private static final String insertPerson =
    "INSERT INTO persons_table AS pt " +
      "VALUES (?, ?, ?, ?)";

  private static final String insertRelation =
    "INSERT INTO relations_table " +
      "VALUES (?, ?, ?) ";

  private boolean isInterrupted = false;
  private final BlockingQueue<Person> personQueue;
  private final Connection connection;
  private PreparedStatement psInsertPerson;
  private PreparedStatement psInsertRel;

  public Worker(BlockingQueue<Person> personQueue,
                Connection connection) {
    this.personQueue = personQueue;
    this.connection = connection;
    try {
      psInsertPerson = connection.prepareStatement(insertPerson);
      psInsertRel = connection.prepareStatement(insertRelation);
    } catch (SQLException throwables) {
      assert false;
    }
  }

  @Override
  public void run() {
    while (!isInterrupted || personQueue.size() > 0) {
      Person person = null;
      try {
        person = personQueue.take();
      } catch (InterruptedException ignored) {
        isInterrupted = true;
      }
      try {
        if (person == null) {
          continue;
        }
        psInsertPerson.setString(1, person.getId());
        psInsertPerson.setString(2, person.getGender());
        psInsertPerson.setString(3, person.getFirstname());
        psInsertPerson.setString(4, person.getSurname());
        psInsertPerson.addBatch();
        if (person.getMother() != null) {
          psInsertRel.setString(1, person.getMother());
          psInsertRel.setString(2, person.getId());
          psInsertRel.setString(3, "MOTHER");
          psInsertRel.addBatch();
        }
        if (person.getFather() != null) {
          psInsertRel.setString(1, person.getFather());
          psInsertRel.setString(2, person.getId());
          psInsertRel.setString(3, "FATHER");
          psInsertRel.addBatch();
        }
        if (person.getSpouseId() != null) {
          psInsertRel.setString(1, person.getSpouseId());
          psInsertRel.setString(2, person.getId());
          if (person.getGender().equals("MALE")) {
            psInsertRel.setString(3, "WIFE");
          } else {
            psInsertRel.setString(3, "HUSBAND");
          }
          psInsertRel.addBatch();
        }
        if (person.getSisters() != null) {
          for (String sis : person.getSisters()) {
            psInsertRel.setString(1, sis);
            psInsertRel.setString(2, person.getId());
            psInsertRel.setString(3, "SISTER");
            psInsertRel.addBatch();
          }
        }
        if (person.getBrothers() != null) {
          for (String bro : person.getBrothers()) {
            psInsertRel.setString(1, bro);
            psInsertRel.setString(2, person.getId());
            psInsertRel.setString(3, "BROTHER");
            psInsertRel.addBatch();
          }
        }
        if (person.getSons() != null) {
          for (String son : person.getSons()) {
            psInsertRel.setString(1, son);
            psInsertRel.setString(2, person.getId());
            psInsertRel.setString(3, "SON");
            psInsertRel.addBatch();
          }
        }
        if (person.getDaughters() != null) {
          for (String daughter : person.getDaughters()) {

            psInsertRel.setString(1, daughter);
            psInsertRel.setString(2, person.getId());
            psInsertRel.setString(3, "DAUGHTER");
            psInsertRel.addBatch();
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
    try {
      psInsertPerson.executeBatch();
      psInsertRel.executeBatch();
      psInsertRel.close();
      psInsertPerson.close();
      connection.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      System.exit(1);
    }
  }
}
