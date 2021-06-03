import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.commons.dbcp.BasicDataSource;

public class Main {

  private static final String createPersonTable =
    "CREATE TABLE IF NOT EXISTS persons_table (" +
      "id varchar(8) NOT NULL PRIMARY KEY," +
      "gender varchar(10) NOT NULL," +
      "firstname varchar(255) NOT NULL," +
      "surname varchar(255) NOT NULL," +
      "CONSTRAINT CHK_gender CHECK (gender IN ('MALE', 'FEMALE'))" +
      ")";
  private static final String createRelationsModel =
    "CREATE TABLE IF NOT EXISTS relations_table (" +
      "subject_id varchar(8) NOT NULL," +
      "object_id varchar(8) NOT NULL," +
      "relation_type varchar(50) NOT NULL," +
      "CONSTRAINT CHK_relation_type CHECK (relation_type " +
      "IN ('MOTHER', 'FATHER', 'SISTER', 'BROTHER'," +
      " 'SON', 'DAUGHTER', 'HUSBAND', 'WIFE'))" +
      ")";

  private static final BasicDataSource ds = new BasicDataSource();

  public static void main(String[] args) {
    int PROCESSORS_COUNT = Integer.parseInt(args[0]);
    ds.setUrl("jdbc:postgresql://localhost:5432/persons");
    ds.setUsername("postgres");
    ds.setPassword("postgres");
    ds.setMaxOpenPreparedStatements(100);

    try (Connection connection = ds.getConnection()) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("DROP TABLE relations_table");
      statement.executeUpdate("DROP TABLE persons_table");

      statement.executeUpdate(createPersonTable);
      statement.executeUpdate(createRelationsModel);

      XMLStreamReader xmlr = XMLInputFactory
        .newInstance()
        .createXMLStreamReader("output.xml", new FileInputStream("output.xml"));
      Person person = null;

      BlockingQueue<Person> people = new LinkedBlockingQueue<>();

      List<Thread> threadList = new ArrayList<>();
      for (int i = 0; i < PROCESSORS_COUNT; i++) {
        threadList.add(
          new Thread(new Worker(people, ds.getConnection())));
      }
      threadList.forEach(Thread::start);
      while (xmlr.hasNext()) {
        xmlr.next();
        if (xmlr.isStartElement()) {
          switch (xmlr.getLocalName()) {
            case "person":
              person = new Person();
              person.setId(xmlr.getAttributeValue(0));
              person.setFirstname(xmlr.getAttributeValue(1));
              person.setSurname(xmlr.getAttributeValue(2));
              break;
            case "gender":
              xmlr.next();
              person.setGender(xmlr.getText().trim());
              break;
            case "mother":
              person.setMother(xmlr.getAttributeValue(0));
              break;
            case "father":
              person.setFather(xmlr.getAttributeValue(0));
              break;
            case "spouse":
              person.setSpouseId(xmlr.getAttributeValue(0));
              break;
            case "sister":
              person.getSisters().add(xmlr.getAttributeValue(0));
              break;
            case "brother":
              person.getBrothers().add(xmlr.getAttributeValue(0));
              break;
            case "son":
              person.getSons().add(xmlr.getAttributeValue(0));
              break;
            case "daughter":
              person.getDaughters().add(xmlr.getAttributeValue(0));
              break;
          }
        } else if (xmlr.isEndElement()) {
          if (xmlr.getLocalName().equals("person")) {
            assert person != null;
            people.add(person);
          }
        }
      }
      threadList.forEach(Thread::interrupt);
      threadList.forEach(x -> {
        try {
          x.join();
        } catch (InterruptedException ignored) {
          assert false;
        }
      });
      statement.executeUpdate(
        "ALTER TABLE relations_table " +
          "ADD FOREIGN KEY (subject_id) REFERENCES persons_table(id), "+
          "ADD FOREIGN KEY (object_id) REFERENCES persons_table(id)");

    } catch (SQLException e) {
      System.err.println("Connection failure.");
      e.printStackTrace();
    } catch (IOException | XMLStreamException e) {
      System.err.println("Can't parse xml");
      e.printStackTrace();
    }
    try {
      ds.close();
    } catch (SQLException throwables) {
      assert false;
    }

  }


}
