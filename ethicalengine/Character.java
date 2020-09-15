/**
 * Class {@code Character} is an Abstract Class from which all character types inherit.
 * Two concrete classes {@code Person} and  {@code Animal} that directly inherit from this class.
 * @author Yi-Ching Lee, yiclee@student.unimelb.edu.au, 1038344
 *
 */
package ethicalengine;
public abstract class Character{

  /**
  *{@link enum#Gender} include {@code FEMALE, MALE, UNKNOWN}.
  */
  public enum Gender{
    FEMALE, MALE, UNKNOWN
  }
  /**
  *{@link enum#BodyType} include {@code AVERAGE, ATHLETIC, OVERWEIGHT, UNSPECIFIED}.
  */
  public enum BodyType{
    AVERAGE, ATHLETIC, OVERWEIGHT, UNSPECIFIED
  }

  /**
  * @param DEFAULT_AGE default age is 0.
  * @param DEFAULT_GENDER default gender is {@code UNKNOWN}.
  * @param DEFAULT_BODYTYPE default body type is {@code UNSPECIFIED}.
  */
  public int DEFAULT_AGE = 0;
  public Gender DEFAULT_GENDER = Gender.UNKNOWN;
  public BodyType DEFAULT_BODYTYPE = BodyType.UNSPECIFIED;

  /**
  * @param age
  * @param gender
  * @param bodyType
  * These are all basic information of a character.
  */
  private int age;
  private Gender gender;
  private BodyType bodyType;

  /**
  * {@link #Character()} A constructor with default values.
  */
  public Character(){
    age = DEFAULT_AGE;
    gender = DEFAULT_GENDER;
    bodyType = DEFAULT_BODYTYPE;
  }

  /**
  * {@link #Character(int, Gender, BosyType)} A constructor with specific values.
  */
  public Character(int age, Gender gender, BodyType bodyType){
    this.age = age;
    this.gender = gender;
    this.bodyType = bodyType;
  }

  /**
  * {@link #Character(Character)} A constructor to copy a character.
  * @param c Another Character.
  */
  public Character(Character c){
    this.age = c.getAge();
    this.gender = c.getGender();
    this.bodyType = c.getBodyType();
  }

  /**
  *{@link #getAge()} get a called character's age.
  * @return {@code int} the character's age.
  */
  public int getAge(){
    return this.age;
  }

  /**
  *{@link #getGender()} get a called character's gender.
  * @return {@code Gender} the character's gender.
  */
  public Gender getGender(){
    return this.gender;
  }

  /**
  *{@link #getBodyType()} get the called character's body type.
  * @return {@code BodyType} this character's body type.
  */
  public BodyType getBodyType(){
    return this.bodyType;
  }

  /**
  *{@link #setAge(int)} set a character's age.
  * @param age
  * @return {@code void}
  */
  public void setAge(int age){
    this.age = age;
  }

  /**
  *{@link #setGender(gender)} set a character's gender.
  * @param gender
  * @return {@code void}
  */
  public void setGender(Gender gender){
    this.gender = gender;
  }

  /**
  *{@link #setBodyType(BodyType)} get the called character's body type.
  * @param bodyType
  * @return {@code void}
  */
  public void setBodyType(BodyType bodyType){
    this.bodyType = bodyType;
  }

  /**
  *{@link #findBodyType(String)} get the called character's body type.
  * @param body
  * @return {@code BodyType}
  */
  public static BodyType findBodyType(String body){
    String temp_body = body.toUpperCase();
    for(BodyType bodyType : BodyType.values()){
     if(bodyType.name().equals(temp_body)){
      return bodyType;
     }
    }
    return null;
  }
}
