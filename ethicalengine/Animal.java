/**
 * Class {@code Animal} represents animals in the scenarios.
 * scenarios are inhabited by people who exhibit a number of characteristics (e.g., species, is a pet or not.).
 * @author Yi-Ching Lee, yiclee@student.unimelb.edu.au, 1038344
 *
 */
package ethicalengine;
public class Animal extends Character{

  /**
  *{@link enum#Species} include {@code CAT, DOG, BIRD, PIG, HORSE, UNKNOWN}.
  */
  public enum Species{
    CAT, DOG, BIRD, PIG, HORSE, UNKNOWN
  }

  /**
  * @param DEFAULT_SPECIES String, default setting is unknown.
  * @param DEFAULT_ISPET boolean, default setting is false(not a pet).
  */
  public String DEFAULT_SPECIES = "unknown";
  public boolean DEFAULT_ISPET = false;

  /**
  * @param species
  * @param isPet
  */
  private String species;
  private boolean isPet;

  /**
  * {@link #Animal()} A constructor with default values and parameters inherited from {@code Character}.
  */
  public Animal(){
    super();
    species = DEFAULT_SPECIES;
    isPet = DEFAULT_ISPET;
  }

  /**
  * {@link #Animal(String)} A constructor with a specific species and parameters inherited from {@code Character}.
  * @param type
  */
  public Animal(String type){
    super();
    this.species = type;
    this.isPet = DEFAULT_ISPET;
  }

  /**
  * {@link #Animal(int, Gender, BodyType)} A constructor with specific settings.
  * @param age
  * @param gender
  * @param bodyType
  */
  public Animal(int age, Gender gender, BodyType bodyType){
    super(age, gender, bodyType);
  }

  /**
  * {@link #Animal(String, int, Gender, BodyType, boolean)} A constructor with specific settings.
  * @param type
  * @param age
  * @param gender
  * @param bodytype
  * @param isPet
  */
  public Animal(String type, int age, Gender gender, BodyType bodytype, boolean isPet){
    super(age, gender, bodytype);
    this.species = type;
    this.isPet = isPet;
  }

  /**
  * {@link #Animal(Animal)} A constructor to copy an animal.
  * @param otherAnimal Another animal.
  */
  public Animal(Animal otherAnimal){
    super();
    this.species = otherAnimal.getSpecies();
    this.isPet = otherAnimal.isPet();
  }

  /**
  *{@link #getSpecies()} get a called animal's species.
  * @return {@code String} the animal's species.
  */
  public String getSpecies(){
    return this.species;
  }

  /**
  *{@link #setSpecies()} set a called animal's species.
  * @param type Species type.
  * @return {@code void}
  */
  public void setSpecies(String type){
    this.species = type.toLowerCase();
  }

  /**
  *{@link #isPet()} check whether an animal is a pet.
  * @return {@code boolean}
  */
  public boolean isPet(){
    return this.isPet;
  }

  /**
  *{@link #setPet()} set a called animal a pet or not a pet.
  * @param isPet
  * @return {@code void}
  */
  public void setPet(boolean isPet){
    this.isPet = isPet;
  }

  /**
  * @overwrite
  *{@link #toString()} return an animal's data.
  * @return {@code String}
  */
  public String toString(){
    String animal = this.getSpecies().toLowerCase();
    if(this.isPet()){                  //if is pet
      animal += " is pet";
      return animal;
    }else{                             //not a pet
      return animal;
    }
  }
}
