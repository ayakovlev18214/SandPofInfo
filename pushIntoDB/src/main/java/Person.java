import java.util.ArrayList;
import java.util.List;

public class Person {
  private String id;
  private String firstname;
  private String surname;
  private String gender;
  private String spouseId;

  private String mother;
  private String father;

  private List<String> sisters = new ArrayList<>();
  private List<String> brothers = new ArrayList<>();

  private List<String> sons = new ArrayList<>();
  private List<String> daughters = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getSpouseId() {
    return spouseId;
  }

  public void setSpouseId(String spouseId) {
    this.spouseId = spouseId;
  }

  public String getMother() {
    return mother;
  }

  public void setMother(String mother) {
    this.mother = mother;
  }

  public String getFather() {
    return father;
  }

  public void setFather(String father) {
    this.father = father;
  }

  public List<String> getSisters() {
    return sisters;
  }

  public void setSisters(List<String> sisters) {
    this.sisters = sisters;
  }

  public List<String> getBrothers() {
    return brothers;
  }

  public void setBrothers(List<String> brothers) {
    this.brothers = brothers;
  }

  public List<String> getSons() {
    return sons;
  }

  public void setSons(List<String> sons) {
    this.sons = sons;
  }

  public List<String> getDaughters() {
    return daughters;
  }

  public void setDaughters(List<String> daughters) {
    this.daughters = daughters;
  }
}
