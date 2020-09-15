/**
 * Class {@code Animal} be the basis of your simulation and shall be used to create a variety of scenarios.
 * To guarantee a balanced set of scenarios, it is crucial to randomize as many elements as possible.
 * @author Yi-Ching Lee, yiclee@student.unimelb.edu.au, 1038344
 *
 */
package ethicalengine;
import java.util.*;
import java.lang.*;
public class ScenarioGenerator{

  /**
  * @param DEFAULT_MIN default minimum number setting is 1.
  * @param DEFAULT_MAX default maximun number setting is 5.
  * @param DEFAULT_SEED default seed setting is 1.
  */
  public int DEFAULT_MIN = 1;
  public int DEFAULT_MAX = 5;
  public int DEFAULT_SEED = 1;

  /**
  * @param seed
  * @param passengerCountMinimum
  * @param passengerCountMaximum;
  * @param pedestrianCountMinimum
  * @param pedestrianCountMaximum;
  */
  private long seed;
  private int passengerCountMinimum;
  private int passengerCountMaximum;
  private int pedestrianCountMinimum;
  private int pedestrianCountMaximum;

  /**
  * Create a {@code random} of {@code Random} class to support the random methods.
  */
  public Random random = new Random();

  /**
  * {@link #ScenarioGenerator()}  this constructor set the seed to a truly random number.
  */
  public ScenarioGenerator(){
    random.setSeed(random.nextInt());
    passengerCountMinimum = DEFAULT_MIN;
    passengerCountMaximum = DEFAULT_MAX;
    pedestrianCountMinimum = DEFAULT_MIN;
    pedestrianCountMaximum = DEFAULT_MAX;
  }

  /**
  * {@link #ScenarioGenerator()}  this constructor set the seed to a specific number.
  */
  public ScenarioGenerator(long seed){
    random.setSeed(seed);
  }

  /**
  * {@link #ScenarioGenerator()}  this constructor set all settings with given values.
  */
  public ScenarioGenerator(long seed, int passengerCountMinimum, int passengerCountMaximum,
  int pedestrianCountMinimum, int pedestrianCountMaximum){
    random.setSeed(seed);
    this.passengerCountMinimum = passengerCountMinimum;
    this.passengerCountMaximum = passengerCountMaximum;
    this.pedestrianCountMinimum = pedestrianCountMinimum;
    this.pedestrianCountMaximum = pedestrianCountMaximum;
  }

  /**
  *{@link #getRandomRunCounts()} create a random number within 0 to 10.
  * @return {@code int}
  */
  public static int getRandomRunCounts(){
    Random r = new Random();
    int count = r.nextInt((10 - 0) + 1) + 0;
    return count;
  }

  /**
  *{@link #setPassengerCountMin(int)} set the minimum number of passengers.
  * @param min Species type.
  * @return {@code void}
  */
  public void setPassengerCountMin(int min){
    this.passengerCountMinimum = min;
  }

  /**
  *{@link #setPassengerCountMin(int)} set the maximum number of passengers.
  * @param min Species type.
  * @return {@code void}
  */
  public void setPassengerCountMax(int max){
    this.passengerCountMaximum = max;
  }

  /**
  *{@link #setPedestrainCountMin(int)} set the minimum number of passengers.
  * @param min Species type.
  * @return {@code void}
  */
  public void setPedestrianCountMin(int min){
    this.pedestrianCountMinimum = min;
  }

  /**
  *{@link #setPedestrainCountMax(int)} set the maximum number of pedestrian.
  * @param max Species type.
  * @return {@code void}
  */
  public void setPedestrianCountMax(int max){
    this.pedestrianCountMaximum = max;
  }

  /**
  *{@link #getRandomPerson()} create a random person.
  * @return {@code Person}
  */
  public Person getRandomPerson(){
    int age = random.nextInt((100 - 0) + 1) + 0;
    Person.Profession profession = null;
    if(age >= 17 && age <= 68){
      profession = Person.Profession.values()[random.nextInt(Person.Profession.values().length)];
    }else{
      profession = Person.Profession.NONE;
    }
    Character.Gender gender = Character.Gender.values()[random.nextInt(Character.Gender.values().length - 1)];
    Character.BodyType bodytype = Character.BodyType.values()[random.nextInt(Character.BodyType.values().length - 1)];
    boolean isPregnant;
    if(gender == Character.Gender.FEMALE){
      if(random.nextInt() % 2 == 0){
        isPregnant = true;
      }else{
        isPregnant = false;
      }
    }else{
      isPregnant = false;
    }
    Person person = new Person(age, profession, gender, bodytype, isPregnant);
    return person;
  }

  /**
  *{@link #getRandomAnimal()} create a random animal.
  * @return {@code Animal}
  */
  public Animal getRandomAnimal(){
    int age = random.nextInt((100 - 0) + 1) + 0;
    Character.Gender gender = Character.Gender.values()[random.nextInt(Character.Gender.values().length - 1)];
    Character.BodyType bodytype = Character.BodyType.values()[random.nextInt(Character.BodyType.values().length - 1)];
    String type = Animal.Species.values()[random.nextInt(Animal.Species.values().length - 1)].name();
    boolean isPet;
    if(random.nextInt() % 2 == 0){
      isPet = true;
    }else{
      isPet = false;
    }
    Animal animal = new Animal(type, age, gender, bodytype, isPet);
    return animal;
  }

  /**
  *{@link #generate()} generate a random scenario.
  * @return {@code Scenario}
  */
  public Scenario generate(){
    int pasCount = random.nextInt((this.passengerCountMaximum - this.passengerCountMinimum) + 1) + this.passengerCountMinimum;
    int pedCount = random.nextInt((this.pedestrianCountMaximum - this.pedestrianCountMinimum) + 1) + this.pedestrianCountMinimum;
    ethicalengine.Character[] passengers = new Character[pasCount];
    for(int i = 0; i < pasCount; i++){
      if(random.nextInt() % 2 == 0){
        passengers[i] = getRandomPerson();
      }else{
        passengers[i] = getRandomAnimal();
      }
    }
    ethicalengine.Character[] pedestrians = new Character[pedCount];
    for(int j = 0; j < pedCount; j++){
      if(random.nextInt() % 2 == 0){
        pedestrians[j] = getRandomPerson();
      }else{
        pedestrians[j] = getRandomAnimal();
      }
    }
    boolean isLegalCrossing = true;
    if(random.nextInt() % 2 == 0){
      isLegalCrossing = true;
    }else{
      isLegalCrossing = false;
    }
    Scenario scenario = new Scenario(passengers, pedestrians, isLegalCrossing);
    return scenario;
  }

}
