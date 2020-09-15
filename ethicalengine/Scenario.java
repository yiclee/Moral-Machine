/**
 * Class {@code Animal} This class contains all relevant information about a presented scenario.
 * Including the car’s passengers and the pedestrians on the street as well as whether the pedestrians are crossing legally.
 * @author Yi-Ching Lee, yiclee@student.unimelb.edu.au, 1038344
 *
 */
package ethicalengine;
import java.lang.*;
public class Scenario{

  /**
  * @param passengers[] a collection of passengers.
  * @param pedestrians[] a collection of pedestrians.
  * @param isLegalCrossing the scenario is legal crossing or not.
  */
  private Character[] passengers;
  private Character[] pedestrians;
  private boolean isLegalCrossing;

  /**
  * {@link #Scenario(Character[], Character[], boolean)} A constructor with specific settings for a scenario.
  * @param passeners[]
  * @param pedestrians[]
  * @param isLegalCrossing
  */
  public Scenario(Character[] passengers, Character[] pedestrians, boolean isLegalCrossing){
    this.passengers = passengers;
    this.pedestrians = pedestrians;
    this.isLegalCrossing = isLegalCrossing;
  }

  /**
  *{@link #hasYouInCar()} to check whether you are in the car(passenger).
  * @return {@code boolean}
  */
  public boolean hasYouInCar(){
    int i = 0;
    while(i < this.getPassengerCount()){
      if(getPassengers()[i].toString().contains("you")){
        return true;
      }else{
        i++;
      }
    }
    return false;
  }

  /**
  *{@link #hasYouInLane()} to check whether you are in the lane(pedestrian).
  * @return {@code boolean}
  */
  public boolean hasYouInLane(){
    int i = 0;
    while(i < this.getPedestrianCount()){
      if(getPedestrians()[i].toString().contains("you")){
        return true;
      }else{
        i++;
      }
    }
    return false;
  }

  /**
  *{@link #getPassengers()} get passengers collectionfrom a scenario.
  * @return {@code Character[]}
  */
  public Character[] getPassengers(){
    return this.passengers;
  }

  /**
  *{@link #getPedestrains()} get pedestrians collectionfrom a scenario.
  * @return {@code Character[]}
  */
  public Character[] getPedestrians(){
    return this.pedestrians;
  }

  /**
  *{@link #isLegalCrossing()} to check whether the scenario is with green signal light.
  * @return {@code boolean}
  */
  public boolean isLegalCrossing(){
    return this.isLegalCrossing;
  }

  /**
  *{@link #setLegalCrossing()} set a scenario is legal crossing or not.
  * @param isLegalCrossing
  * @return {@code void}
  */
  public void setLegalCrossing(boolean isLegalCrossing){
    this.isLegalCrossing = isLegalCrossing;
  }

  /**
  *{@link #getPassengersCount()} get passengers amount from a scenario.
  * @return {@code int}
  */
  public int getPassengerCount(){
    int count = passengers.length;
    return count;
  }


  /**
  *{@link #getPedestrianCount()} get pedestrians amount from a scenario.
  * @return {@code int}
  */
  public int getPedestrianCount(){
    int count = pedestrians.length;
    return count;
  }

  /**
  * @overwrite
  *{@link #toString()} return a scenario's data.
  * @return {@code String}
  */
  public String toString(){
    Character[] pas = this.getPassengers();
    Character[] ped = this.getPedestrians();
    String scenario = "======================================\n"
    + "# Scenario\n" + "======================================\n";
    if(this.isLegalCrossing()){
      scenario += "Legal Crossing: " + "yes\n";
    }else{
      scenario += "Legal Crossing: " + "no\n";
    }
    scenario += "Passengers " + "(" + this.getPassengerCount() + ")\n";
    for(int i = 0; i < this.getPassengerCount(); i++){
      scenario += "- " + pas[i].toString() + "\n";
    }
    scenario += "Pedestrians " + "(" + this.getPedestrianCount() + ")\n";
    for(int j = 0; j < this.getPedestrianCount()-1; j++){
      scenario += "- " + ped[j].toString() + "\n";
    }
    scenario += "- " + ped[this.getPedestrianCount()-1].toString();
    return scenario;
  }
}
