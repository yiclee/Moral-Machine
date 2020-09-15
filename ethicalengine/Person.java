/**
 * Class {@code Person} represents a human in the scenarios.
 * scenarios are inhabited by people who exhibit a number of characteristics (e.g., age,gender, body type, profession etc.).
 * @author Yi-Ching Lee, yiclee@student.unimelb.edu.au, 1038344
 *
 */
package ethicalengine;
public class Person extends Character{

  /**
  *{@link enum#Profession} include {@code DOCTOR, CEO, CRIMINAL, HOMELESS, ENGINEER, DESIGNER, UNEMPLOYED, NONE, UNKNOWN}.
  */
  public enum Profession{
    DOCTOR, CEO, CRIMINAL, HOMELESS, ENGINEER, DESIGNER, UNEMPLOYED, NONE, UNKNOWN
  }

  /**
  *{@link enum#AgeCategory} include {@code BABY, CHILD, ADULT, SENIOR, UNKNOWN}.
  */
  public enum AgeCategory{
    BABY, CHILD, ADULT, SENIOR, UNKNOWN
  }

  /**
  * @param DEFAULT_PROFESSION default setting is {@code UNKNOWN}}.
  * @param DEFAULT_ISPREGNANT default setting is {@code false}} (not pregnant).
  * @param DEFAULT_ISYOU default setting is {@code false}} (not you).
  */
  public Profession DEFAULT_PROFESSION = Profession.UNKNOWN;
  public boolean DEFAULT_ISPREGNANT = false;
  public boolean DEFAULT_ISYOU = false;

  /**
  * @param profession
  * @param isPregnant
  * @param isYou
  */
  private Profession profession;
  private boolean isPregnant;
  private boolean isYou;

  /**
  * {@link #Person()} A constructor with default values and parameters inherited from {@code Character}.
  */
  public Person(){
    super();
    profession = DEFAULT_PROFESSION;
    isPregnant = DEFAULT_ISPREGNANT;
    isYou = DEFAULT_ISYOU;
  }

  /**
  * {@link #Person(int, Gender, BodyType)} A constructor with specific settings.
  * @param age
  * @param gender
  * @param bodyType
  */
  public Person(int age, Gender gender, BodyType bodyType){
    super(age, gender, bodyType);
  }

  /**
  * {@link #Person(int, Profession, Gender, BodyType, boolean)} A constructor with specific settings.
  * @param age
  * @param profession
  * @param gender
  * @param bodytype
  * @param isPet
  */
  public Person(int age, Profession profession, Gender gender, BodyType bodytype, boolean isPregnant){
    super(age, gender, bodytype);
    this.profession = profession;
    this.isPregnant = isPregnant;
    this.isYou = DEFAULT_ISYOU;
  }

  /**
  * {@link #Person(Perosn)} A constructor to copy an person.
  * @param otherPerosn Another person.
  */
  public Person(Person otherPerson){
    super(otherPerson.getAge(), otherPerson.getGender(), otherPerson.getBodyType());
    this.profession = otherPerson.getProfession();
    this.isPregnant = otherPerson.isPregnant();
    this.isYou = otherPerson.isYou();
  }

  /**
  *{@link #getAgeCategory()} get a called person's age category.
  * @return {@code AgeCategory} the person's age category.
  */
  public AgeCategory getAgeCategory(){
    if(this.getAge() >= 0 && this.getAge() <= 4){
      return AgeCategory.BABY;
    }else if(this.getAge() >= 5 && this.getAge() <= 16){
      return AgeCategory.CHILD;
    }else if(this.getAge() >= 17 && this.getAge() <= 68){
      return AgeCategory.ADULT;
    }else{
      return AgeCategory.SENIOR;
    }
  }

  /**
  *{@link #getProfession()} get a called person's profession.
  * @return {@code Profession} the person's job category.
  */
  public Profession getProfession(){
    if(this.getAgeCategory() != AgeCategory.ADULT){
      return Profession.NONE;
    }else{
      return this.profession;
    }
  }

  /**
  *{@link #findProfession(String)} match the specific profession and return it.
  * @param name import a (@code String) name of one's job.
  * @return {@code Profession}
  */
  public static Profession findProfession(String name){
    String temp_job = name.toUpperCase();
    for(Profession profession : Profession.values()){
     if(profession.name().equals(temp_job)){
      return profession;
     }
    }
    return null;
  }

  /**
  *{@link #isPregnant()} to check whether a person is pregnant.
  * @return {@code boolean}
  */
  public boolean isPregnant(){
    if(this.getGender() != Gender.FEMALE){
      return false;
    }else{
      return this.isPregnant;
    }
  }

  /**
  *{@link #setPregnant()} set a called person is pregnant or not.
  * @param pregnant
  * @return {@code void}
  */
  public void setPregnant(boolean pregnant){
    if(this.getGender() != Gender.FEMALE){
      this.isPregnant = false;
    }else{
      this.isPregnant = pregnant;
    }
  }

  /**
  *{@link #isYou()} to check whether a person is you.
  * @return {@code boolean}
  */
  public boolean isYou(){
    return isYou;
  }

  /**
  *{@link #setAsYou(boolean)} set a called person is you.
  * @param ifYou
  * @return {@code void}
  */
  public void setAsYou(boolean isYou){
    this.isYou = isYou;
  }

  /**
  * @overwrite
  *{@link #toString()} return a person's data.
  * @return {@code String}
  */
  public String toString(){
    String person = null;
    if(this.isYou() == true){                                   //is you
      if(this.getAgeCategory() == AgeCategory.ADULT){            //is adult
        if(this.getGender() == Gender.FEMALE){                      //is female
          String body = this.getBodyType().name();
          String job = this.getProfession().name();
          if(this.isPregnant()){
            person = "you " + body.toLowerCase() + " adult " + job.toLowerCase() + " female pregnant";
          }else{
            person = "you " + body.toLowerCase() + " adult " + job.toLowerCase() + " female";
          }
        }else{                                                    //not female
          String body = this.getBodyType().name();
          String job = this.getProfession().name();
          person = "you " + body.toLowerCase() + " adult " + job.toLowerCase() + " male";
        }
      }else{                                                     //not adult
        if(this.getGender() == Gender.FEMALE){                      //is female
          String body = this.getBodyType().name();
          String agetype = this.getAgeCategory().name();
          if(this.isPregnant()){
            person = "you " + body.toLowerCase() + " " + agetype.toLowerCase() + " female pregnant";
          }else{
            person = "you " + body.toLowerCase() + " " + agetype.toLowerCase() + " female";
          }
        }else{                                                    //not female
          String body = this.getBodyType().name();
          String gen = this.getGender().name();
          String agetype = this.getAgeCategory().name();
          person = "you " + body.toLowerCase() + " " + agetype.toLowerCase() + " male";
        }
      }
    }else{                                                       //not you
      if(this.getAgeCategory() == AgeCategory.ADULT){            //is adult
        if(this.getGender() == Gender.FEMALE){                      //is female
          String body = this.getBodyType().name();
          String job = this.getProfession().name();
          if(this.isPregnant()){
            person = body.toLowerCase() + " adult " + job.toLowerCase() + " female pregnant";
          }else{
            person = body.toLowerCase() + " adult " + job.toLowerCase() + " female";
          }

        }else{                                                    //not female
          String body = this.getBodyType().name();
          String job = this.getProfession().name();
          person = body.toLowerCase() + " adult " + job.toLowerCase() + " male";
        }
      }else{                                                     //not adult
        if(this.getGender() == Gender.FEMALE){                      //is female
          String body = this.getBodyType().name();
          String agetype = this.getAgeCategory().name();
          if(this.isPregnant()){
            person = body.toLowerCase() + " " + agetype.toLowerCase() + " female pregnant";
          }else{
            person = body.toLowerCase() + " " + agetype.toLowerCase() + " female";
          }

        }else{                                                    //not female
          String body = this.getBodyType().name();
          String gen = this.getGender().name();
          String agetype = this.getAgeCategory().name();
          person = body.toLowerCase() + " " + agetype.toLowerCase() + " male";
        }
      }
    }
    return person;
  }

}
