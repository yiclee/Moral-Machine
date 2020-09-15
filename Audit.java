/**
 * Class {@code Audut} is an inspection of the algorithm.
 * Create a specific number of random scenarios, allow EthicalEngine to decide on each outcome.
 * Summarize the results for each characteristic in a so-called statistic of projected survival.
 * @author Yi-Ching Lee, yiclee@student.unimelb.edu.au, 1038344
 */
import ethicalengine.*;
import java.util.*;
import java.lang.*;
import java.io.*;
public class Audit{

  /**
  * @param DEFAULT_NAME audit name default set as "Unspecified".
  */
  public String DEFAULT_NAME = "Unspecified";

  /**
  * Create a {@code random} of {@code Random} class to support the random methods.
  */
  public Random random = new Random();

  /**
  * @param name audit name
  * @param feature[] a collection of all the characteristics in the audit.
  * @param statistics[][] store all stats of each characteristic.
  * @param scenarios[] store all scenarios of an audit in this collection.
  * @param ageSum the age sum of all survived person in the audit.
  * @param aliveCount store how many character survives.
  * @param avgAge the average age og all survived characters.
  * @param run records how many scenarios run.
  */
  private String name;
  private String[] feature = new String[30];
  private Float[][] statistics = new Float[30][3];
  private Scenario[] scenarios;
  private float ageSum = 0F;
  private float aliveCount = 0F;
  private float avgAge = 0F;
  private int run = 0;


  /**
  * {@link #Audit()} constructor of {@code Audit}.
  */
  public Audit(){
    name = DEFAULT_NAME;
  }

  /**
  *{@link #Audit(Scenario[])}  constructor of {@code Audit} with imported scenarios.
  * @param scenarios
  */
  public Audit(Scenario[] scenarios){
    name = DEFAULT_NAME;
    this.scenarios = scenarios;
  }

  /**
  *{@link #run(int)} generate specific amount of scenarios and test it with {@link #decide(Scenario)}.
  * @param runs
  * @return {@code void}
  */
  public void run(int runs){
    run += runs;
    ScenarioGenerator machine = new ScenarioGenerator();

    for(int x = 0; x<3; x++){
      for(int y = 0; y<30; y++){
        statistics[y][x] = 0F;
      }
    }


    for(int n = 0; n < runs; n++){
      //create scenarios
      Scenario test = machine.generate();
      //check--
      //System.out.println(test.toString());
      int pedCount = test.getPedestrianCount();
      int pasCount = test.getPassengerCount();
      EthicalEngine.Decision decision = EthicalEngine.decide(test);

      ethicalengine.Character[] pas = test.getPassengers();
      ethicalengine.Character[] ped = test.getPedestrians();

      //save all features
      //update features
      if(decision == EthicalEngine.Decision.PASSENGERS){
        //update light data
        if(test.isLegalCrossing()){
          int num = 0;
          if(Arrays.asList(feature).contains("green") == false){
            while(num < feature.length){
              if(feature[num] == null){
                feature[num] = "green";
                statistics[num][0] = statistics[num][0] + test.getPassengerCount();
                statistics[num][1] = statistics[num][1] + test.getPassengerCount() + test.getPedestrianCount();
                statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
                break;
              }
              num++;
            }
          }else{
            int num1 = Arrays.asList(feature).indexOf("green");
            statistics[num1][0] += test.getPassengerCount();
            statistics[num1][1] += test.getPassengerCount() + test.getPedestrianCount();
            statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
          }
        }else{
          int num = 0;
          if(Arrays.asList(feature).contains("red") == false){
            while(num < feature.length){
              if(feature[num] == null){
                feature[num] = "red";
                statistics[num][0] += test.getPassengerCount();
                statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
                statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
                break;
              }
              num++;
            }
          }else{
            int num1 = Arrays.asList(feature).indexOf("red");
            statistics[num][0] += test.getPassengerCount();
            statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
            statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
          }
        }
        //passengers survive
        for(int i = 0; i < pas.length; i++){
          if (pas[i].getClass().getName().contains("Person")){//Pas is person
            //add person
            if(Arrays.asList(feature).contains("person") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "person";
                  statistics[count1][0] += 1;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{

              int index1 = Arrays.asList(feature).indexOf("person");
              statistics[index1][0] += 1;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            ageSum += pas[i].getAge();
            aliveCount ++;
            String[] temp = pas[i].toString().split(" ");
            for(int j = 0; j < temp.length; j++){
              if(Arrays.asList(feature).contains(temp[j]) == false){
                int count = 0;
                while(count < feature.length){
                  if(feature[count] == null){
                    feature[count] = temp[j];
                    statistics[count][0] += 1;
                    statistics[count][1] += 1;
                    statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                    break;
                  }
                  count++;
                }
              }else{

                int index = Arrays.asList(feature).indexOf(temp[j]);
                statistics[index][0] += 1;
                statistics[index][1] += 1;
                statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
              }
            }
          }else{//Pas is Animal
            //add animal
            if(Arrays.asList(feature).contains("animal") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "animal";
                  statistics[count1][0] += 1;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{

              int index1 = Arrays.asList(feature).indexOf("animal");
              statistics[index1][0] += 1;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            // add pet
            if(pas[i].toString().contains("pet")){
              //add pet
              if(Arrays.asList(feature).contains("pet") == false){
                int count1 = 0;
                while(count1 < feature.length){
                  if(feature[count1] == null){
                    feature[count1] = "pet";
                    statistics[count1][0] += 1;
                    statistics[count1][1] += 1;
                    statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                    break;
                  }
                  count1++;
                }
              }else{

                int index1 = Arrays.asList(feature).indexOf("pet");
                statistics[index1][0] += 1;
                statistics[index1][1] += 1;
                statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
              }
              //add species
              String[] temp2 = pas[i].toString().split(" is ");
              if(Arrays.asList(feature).contains(temp2[0]) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2[0];
                    statistics[count2][0] += 1;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2[0]);
                statistics[index2][0] += 1;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }else{
              String temp2 = pas[i].toString();
              if(Arrays.asList(feature).contains(temp2) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2;
                    statistics[count2][0] += 1;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2);
                statistics[index2][0] += 1;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }
          }
        }

        //get ped data----------------------------------------------------------------------------
        for(int i = 0; i < ped.length; i++){

          if (ped[i].getClass().getName().contains("Person")){//Pas is person
            if(Arrays.asList(feature).contains("person") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "person";
                  statistics[count1][0] += 0;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{

              int index1 = Arrays.asList(feature).indexOf("person");
              statistics[index1][0] += 0;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            String[] temp = ped[i].toString().split(" ");
            for(int j = 0; j < temp.length; j++){
              if(Arrays.asList(feature).contains(temp[j]) == false){
                int count = 0;
                while(count < feature.length){
                  if(feature[count] == null){
                    feature[count] = temp[j];
                    statistics[count][0] += 0;
                    statistics[count][1] += 1;
                    statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                    break;
                  }
                  count++;
                }
              }else{
                int index = Arrays.asList(feature).indexOf(temp[j]);
                statistics[index][0] += 0;
                statistics[index][1] += 1;
                statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
              }
            }
          }else{//Ped is Animal
            //add animal
            if(Arrays.asList(feature).contains("animal") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "animal";
                  statistics[count1][0] += 0;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("animal");
              statistics[index1][0] += 0;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            //add pet
            if(ped[i].toString().contains("pet")){
              //add pet
              if(Arrays.asList(feature).contains("pet") == false){
                int count1 = 0;
                while(count1 < feature.length){
                  if(feature[count1] == null){
                    feature[count1] = "pet";
                    statistics[count1][0] += 0;
                    statistics[count1][1] += 1;
                    statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                    break;
                  }
                  count1++;
                }
              }else{
                int index1 = Arrays.asList(feature).indexOf("pet");
                statistics[index1][0] += 0;
                statistics[index1][1] += 1;
                statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
              }
              //add species
              String[] temp2 = ped[i].toString().split(" is ");
              if(Arrays.asList(feature).contains(temp2[0]) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2[0];
                    statistics[count2][0] += 0;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2[0]);
                statistics[index2][0] += 0;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }else{//not pet
              String temp2 = ped[i].toString();
              if(Arrays.asList(feature).contains(temp2) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2;
                    statistics[count2][0] += 0;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2);
                statistics[index2][0] += 0;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }
          }
        }
      }else{//pedestrians survive=======================================================================================
        //update light data
        if(test.isLegalCrossing()){
          int num = 0;
          if(Arrays.asList(feature).contains("green") == false){
            while(num < feature.length){
              if(feature[num] == null){
                feature[num] = "green";
                statistics[num][0] += test.getPedestrianCount();
                statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
                statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
                break;
              }
              num++;
            }
          }else{
            int num1 = Arrays.asList(feature).indexOf("green");
            statistics[num1][0] += test.getPedestrianCount();
            statistics[num1][1] += test.getPassengerCount() + test.getPedestrianCount();
            statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
          }
        }else{
          int num = 0;
          if(Arrays.asList(feature).contains("red") == false){
            while(num < feature.length){
              if(feature[num] == null){
                feature[num] = "red";
                statistics[num][0] += test.getPedestrianCount();
                statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
                statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
                break;
              }
              num++;
            }
          }else{
            int num1 = Arrays.asList(feature).indexOf("red");
            statistics[num1][0] += test.getPedestrianCount();
            statistics[num1][1] += test.getPassengerCount() + test.getPedestrianCount();
            statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
          }
        }

        //update other features
        for(int i = 0; i < ped.length; i++){
          if (ped[i].getClass().getName().contains("Person")){ //Ped is person
            //add person
            if(Arrays.asList(feature).contains("person") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "person";
                  statistics[count1][0] += 1;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              //  System.out.println("===============2.1==================");
              int index1 = Arrays.asList(feature).indexOf("person");
              statistics[index1][0] += 1;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            ageSum += ped[i].getAge();
            aliveCount ++;
            String[] temp = ped[i].toString().split(" ");
            for(int j = 0; j < temp.length; j++){
              if(Arrays.asList(feature).contains(temp[j]) == false){
                int count = 0;
                while(count < feature.length){
                  if(feature[count] == null){
                    feature[count] = temp[j];
                    statistics[count][0] += 1;
                    statistics[count][1] += 1;
                    statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                    break;
                  }
                  count++;
                }
              }else{
                int index = Arrays.asList(feature).indexOf(temp[j]);
                statistics[index][0] += 1;
                statistics[index][1] += 1;
                statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
              }
            }
          }else{                                         //Ped is Animal
            //add animal
            if(Arrays.asList(feature).contains("animal") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "animal";
                  statistics[count1][0] += 1;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("animal");
              statistics[index1][0] += 1;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }

            if(ped[i].toString().contains("pet")){
              //add pet
              if(Arrays.asList(feature).contains("pet") == false){
                int count1 = 0;
                while(count1 < feature.length){
                  if(feature[count1] == null){
                    feature[count1] = "pet";
                    statistics[count1][0] += 1;
                    statistics[count1][1] += 1;
                    statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                    break;
                  }
                  count1++;
                }
              }else{
                int index1 = Arrays.asList(feature).indexOf("pet");
                statistics[index1][0] += 1;
                statistics[index1][1] += 1;
                statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
              }
              //add species
              String[] temp2 = ped[i].toString().split(" is ");
              if(Arrays.asList(feature).contains(temp2[0]) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2[0];
                    statistics[count2][0] += 1;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2[0]);
                statistics[index2][0] += 1;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }else{
              String temp2 = ped[i].toString();
              if(Arrays.asList(feature).contains(temp2) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2;
                    statistics[count2][0] += 1;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2);
                statistics[index2][0] += 1;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }
          }
        }
        //get pas data--------------------------------------------------------------------
        for(int i = 0; i < pas.length; i++){
          if (pas[i].getClass().getName().contains("Person")){//Pas is person
            if(Arrays.asList(feature).contains("person") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "person";
                  statistics[count1][0] += 0;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("person");
              statistics[index1][0] += 0;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            String[] temp = pas[i].toString().split(" ");
            for(int j = 0; j < temp.length; j++){
              if(Arrays.asList(feature).contains(temp[j]) == false){
                int count = 0;
                while(count < feature.length){
                  if(feature[count] == null){
                    feature[count] = temp[j];
                    statistics[count][0] += 0;
                    statistics[count][1] += 1;
                    statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                    break;
                  }
                  count++;
                }
              }else{
                int index = Arrays.asList(feature).indexOf(temp[j]);
                statistics[index][0] += 0;
                statistics[index][1] += 1;
                statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
              }
            }
          }else{//Pas is Animal
            //add animal
            if(Arrays.asList(feature).contains("animal") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "animal";
                  statistics[count1][0] += 0;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("animal");
              statistics[index1][0] += 0;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            if(pas[i].toString().contains("pet")){
              //add pet
              if(Arrays.asList(feature).contains("pet") == false){
                int count1 = 0;
                while(count1 < feature.length){
                  if(feature[count1] == null){
                    feature[count1] = "pet";
                    statistics[count1][0] += 0;
                    statistics[count1][1] += 1;
                    statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                    break;
                  }
                  count1++;
                }
              }else{
                int index1 = Arrays.asList(feature).indexOf("pet");
                statistics[index1][0] += 0;
                statistics[index1][1] += 1;
                statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
              }
              //add species
              String[] temp2 = pas[i].toString().split(" is ");
              if(Arrays.asList(feature).contains(temp2[0]) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2[0];
                    statistics[count2][0] += 0;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2[0]);
                statistics[index2][0] += 0;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }else{
              String temp2 = pas[i].toString();
              if(Arrays.asList(feature).contains(temp2) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2;
                    statistics[count2][0] += 0;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2);
                statistics[index2][0] += 0;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }
          }
        }
      }
    }
  }

  /**
  *{@link #run()} generate random amount of scenarios and test it with {@link #decide(Scenario)}.
  * @return {@code void}
  */
  public void run(){
    //run all specific scenariosrun
    Scenario[] config = this.scenarios;
    System.out.println(config.length);
    run += config.length;
    for(int x = 0; x<3; x++){
      for(int y = 0; y<30; y++){
        statistics[y][x] = 0F;
      }
    }
    // for(int l = 0; l < config.length; l++){
    //   System.out.println(config[l].toString());
    // }

    for(int n = 0; n < config.length; n++){
      //create scenarios
      Scenario test = config[n];
      int pedCount = test.getPedestrianCount();
      int pasCount = test.getPassengerCount();
      EthicalEngine.Decision decision = EthicalEngine.decide(test);

      ethicalengine.Character[] pas = test.getPassengers();
      ethicalengine.Character[] ped = test.getPedestrians();

      //save and update all features
      if(decision == EthicalEngine.Decision.PASSENGERS){
        //update light data
        if(test.isLegalCrossing()){//green
          int num = 0;
          //add green
          if(Arrays.asList(feature).contains("green") == false){
            while(num < feature.length){
              if(feature[num] == null){
                feature[num] = "green";
                statistics[num][0] = statistics[num][0] + test.getPassengerCount();
                statistics[num][1] = statistics[num][1] + test.getPassengerCount() + test.getPedestrianCount();
                statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
                break;
              }
              num++;
            }
          }else{
            int num1 = Arrays.asList(feature).indexOf("green");
            statistics[num1][0] += test.getPassengerCount();
            statistics[num1][1] += test.getPassengerCount() + test.getPedestrianCount();
            statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
          }
        }else{
          int num = 0;
          if(Arrays.asList(feature).contains("red") == false){
            while(num < feature.length){
              if(feature[num] == null){
                feature[num] = "red";
                statistics[num][0] += test.getPassengerCount();
                statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
                statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
                break;
              }
              num++;
            }
          }else{
            int num1 = Arrays.asList(feature).indexOf("red");
            statistics[num][0] += test.getPassengerCount();
            statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
            statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
          }
        }
        //passengers survive
        for(int i = 0; i < pas.length; i++){
          if (pas[i].getClass().getName().contains("Person")){//Pas is person
            ageSum += pas[i].getAge();
            aliveCount ++;
            //add person
            if(Arrays.asList(feature).contains("person") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "person";
                  statistics[count1][0] += 1;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              //  System.out.println("===============2.1==================");
              int index1 = Arrays.asList(feature).indexOf("person");
              statistics[index1][0] += 1;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }

            String[] temp = pas[i].toString().split(" ");
            for(int j = 0; j < temp.length; j++){
              if(Arrays.asList(feature).contains(temp[j]) == false){
                int count = 0;
                while(count < feature.length){
                  if(feature[count] == null){
                    feature[count] = temp[j];
                    statistics[count][0] += 1;
                    statistics[count][1] += 1;
                    statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                    break;
                  }
                  count++;
                }
              }else{
                int index = Arrays.asList(feature).indexOf(temp[j]);
                statistics[index][0] += 1;
                statistics[index][1] += 1;
                statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
              }
            }
          }else{//Pas is Animal
            //add animal
            if(Arrays.asList(feature).contains("animal") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "animal";
                  statistics[count1][0] += 1;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("animal");
              statistics[index1][0] += 1;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            if(pas[i].toString().contains("pet")){

              //add pet
              if(Arrays.asList(feature).contains("pet") == false){
                int count1 = 0;
                while(count1 < feature.length){
                  if(feature[count1] == null){
                    feature[count1] = "pet";
                    statistics[count1][0] += 1;
                    statistics[count1][1] += 1;
                    statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                    break;
                  }
                  count1++;
                }
              }else{
                int index1 = Arrays.asList(feature).indexOf("pet");
                statistics[index1][0] += 1;
                statistics[index1][1] += 1;
                statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
              }
              //add species
              String[] temp2 = pas[i].toString().split(" is ");
              if(Arrays.asList(feature).contains(temp2[0]) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2[0];
                    statistics[count2][0] += 1;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2[0]);
                statistics[index2][0] += 1;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }else{
              String temp2 = pas[i].toString();
              if(Arrays.asList(feature).contains(temp2) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2;
                    statistics[count2][0] += 1;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2);
                statistics[index2][0] += 1;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }
          }
        }

        //get ped data----------------------------------------------------------------------------
        for(int i = 0; i < ped.length; i++){

          if (ped[i].getClass().getName().contains("Person")){//Pas is person
            //add person
            if(Arrays.asList(feature).contains("person") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "person";
                  statistics[count1][0] += 0;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("person");
              statistics[index1][0] += 0;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }

            String[] temp = ped[i].toString().split(" ");
            for(int j = 0; j < temp.length; j++){
              if(Arrays.asList(feature).contains(temp[j]) == false){
                int count = 0;
                while(count < feature.length){
                  if(feature[count] == null){
                    feature[count] = temp[j];
                    statistics[count][0] += 0;
                    statistics[count][1] += 1;
                    statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                    break;
                  }
                  count++;
                }
              }else{
                int index = Arrays.asList(feature).indexOf(temp[j]);
                statistics[index][0] += 0;
                statistics[index][1] += 1;
                statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
              }
            }
          }else{//Ped is Animal
            //add animal
            if(Arrays.asList(feature).contains("animal") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "animal";
                  statistics[count1][0] += 0;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("animal");
              statistics[index1][0] += 0;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            if(ped[i].toString().contains("pet")){
              //add pet
              if(Arrays.asList(feature).contains("pet") == false){
                int count1 = 0;
                while(count1 < feature.length){
                  if(feature[count1] == null){
                    feature[count1] = "pet";
                    statistics[count1][0] += 0;
                    statistics[count1][1] += 1;
                    statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                    break;
                  }
                  count1++;
                }
              }else{
                int index1 = Arrays.asList(feature).indexOf("pet");
                statistics[index1][0] += 0;
                statistics[index1][1] += 1;
                statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
              }
              //add species
              String[] temp2 = ped[i].toString().split(" is ");
              if(Arrays.asList(feature).contains(temp2[0]) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2[0];
                    statistics[count2][0] += 0;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2[0]);
                statistics[index2][0] += 0;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }else{//not pet
              String temp2 = ped[i].toString();
              if(Arrays.asList(feature).contains(temp2) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2;
                    statistics[count2][0] += 0;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2);
                statistics[index2][0] += 0;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }
          }
        }
      }else{//pedestrians survive=======================================================================================
        //update light data
        if(test.isLegalCrossing()){
          int num = 0;
          if(Arrays.asList(feature).contains("green") == false){
            while(num < feature.length){
              if(feature[num] == null){
                feature[num] = "green";
                statistics[num][0] += test.getPedestrianCount();
                statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
                statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
                break;
              }
              num++;
            }
          }else{
            int num1 = Arrays.asList(feature).indexOf("green");
            statistics[num1][0] += test.getPedestrianCount();
            statistics[num1][1] += test.getPassengerCount() + test.getPedestrianCount();
            statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
          }
        }else{
          int num = 0;
          if(Arrays.asList(feature).contains("red") == false){
            while(num < feature.length){
              if(feature[num] == null){
                feature[num] = "red";
                statistics[num][0] += test.getPedestrianCount();
                statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
                statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
                break;
              }
              num++;
            }
          }else{
            int num1 = Arrays.asList(feature).indexOf("red");
            statistics[num1][0] += test.getPedestrianCount();
            statistics[num1][1] += test.getPassengerCount() + test.getPedestrianCount();
            statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
          }
        }

        //update other features
        for(int i = 0; i < ped.length; i++){
          if (ped[i].getClass().getName().contains("Person")){ //Ped is person
            //add person
            if(Arrays.asList(feature).contains("person") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "person";
                  statistics[count1][0] += 1;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("person");
              statistics[index1][0] += 1;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }

            ageSum += ped[i].getAge();
            aliveCount ++;
            String[] temp = ped[i].toString().split(" ");
            for(int j = 0; j < temp.length; j++){
              if(Arrays.asList(feature).contains(temp[j]) == false){
                int count = 0;
                while(count < feature.length){
                  if(feature[count] == null){
                    feature[count] = temp[j];
                    statistics[count][0] += 1;
                    statistics[count][1] += 1;
                    statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                    break;
                  }
                  count++;
                }
              }else{
                int index = Arrays.asList(feature).indexOf(temp[j]);
                statistics[index][0] += 1;
                statistics[index][1] += 1;
                statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
              }
            }
          }else{                                         //Ped is Animal
            //add animal
            if(Arrays.asList(feature).contains("animal") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "animal";
                  statistics[count1][0] += 1;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("animal");
              statistics[index1][0] += 1;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }

            if(ped[i].toString().contains("pet")){
              //add pet
              if(Arrays.asList(feature).contains("pet") == false){
                int count1 = 0;
                while(count1 < feature.length){
                  if(feature[count1] == null){
                    feature[count1] = "pet";
                    statistics[count1][0] += 1;
                    statistics[count1][1] += 1;
                    statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                    break;
                  }
                  count1++;
                }
              }else{
                int index1 = Arrays.asList(feature).indexOf("pet");
                statistics[index1][0] += 1;
                statistics[index1][1] += 1;
                statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
              }
              //add species
              String[] temp2 = ped[i].toString().split(" is ");
              if(Arrays.asList(feature).contains(temp2[0]) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2[0];
                    statistics[count2][0] += 1;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2[0]);
                statistics[index2][0] += 1;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }else{
              String temp2 = ped[i].toString();
              if(Arrays.asList(feature).contains(temp2) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2;
                    statistics[count2][0] += 1;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2);
                statistics[index2][0] += 1;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }
          }
        }
        //get pas data--------------------------------------------------------------------
        for(int i = 0; i < pas.length; i++){
          if (pas[i].getClass().getName().contains("Person")){//Pas is person
            //add person
            if(Arrays.asList(feature).contains("person") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "person";
                  statistics[count1][0] += 0;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("person");
              statistics[index1][0] += 0;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }

            String[] temp = pas[i].toString().split(" ");
            for(int j = 0; j < temp.length; j++){
              if(Arrays.asList(feature).contains(temp[j]) == false){
                int count = 0;
                while(count < feature.length){
                  if(feature[count] == null){
                    feature[count] = temp[j];
                    statistics[count][0] += 0;
                    statistics[count][1] += 1;
                    statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                    break;
                  }
                  count++;
                }
              }else{
                int index = Arrays.asList(feature).indexOf(temp[j]);
                statistics[index][0] += 0;
                statistics[index][1] += 1;
                statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
              }
            }
          }else{//Pas is Animal
            //add animal
            if(Arrays.asList(feature).contains("animal") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "animal";
                  statistics[count1][0] += 0;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("animal");
              statistics[index1][0] += 0;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }

            if(pas[i].toString().contains("pet")){
              //add pet
              if(Arrays.asList(feature).contains("pet") == false){
                int count1 = 0;
                while(count1 < feature.length){
                  if(feature[count1] == null){
                    feature[count1] = "pet";
                    statistics[count1][0] += 0;
                    statistics[count1][1] += 1;
                    statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                    break;
                  }
                  count1++;
                }
              }else{
                int index1 = Arrays.asList(feature).indexOf("pet");
                statistics[index1][0] += 0;
                statistics[index1][1] += 1;
                statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
              }
              //add species
              String[] temp2 = pas[i].toString().split(" is ");
              if(Arrays.asList(feature).contains(temp2[0]) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2[0];
                    statistics[count2][0] += 0;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2[0]);
                statistics[index2][0] += 0;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }else{
              String temp2 = pas[i].toString();
              if(Arrays.asList(feature).contains(temp2) == false){
                int count2 = 0;
                while(count2 < feature.length){
                  if(feature[count2] == null){
                    feature[count2] = temp2;
                    statistics[count2][0] += 0;
                    statistics[count2][1] += 1;
                    statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                    break;
                  }
                  count2++;
                }
              }else{
                int index2 = Arrays.asList(feature).indexOf(temp2);
                statistics[index2][0] += 0;
                statistics[index2][1] += 1;
                statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
              }
            }
          }
        }
      }
    }
  }

  /**
  *{@link #interactRun(Decision, int)} run all statistics with decision from user for a scenario.
  * @param decision
  * @param order the scenario order
  * @return {@code void}
  */
  public void interactRun(EthicalEngine.Decision decision, int order){
    run += 1;
    if(run == 1){
      for(int x = 0; x<3; x++){
        for(int y = 0; y<30; y++){
          statistics[y][x] = 0F;
        }
      }
    }

    //import one scenario
    Scenario test = this.scenarios[order];
    int pedCount = test.getPedestrianCount();
    int pasCount = test.getPassengerCount();

    ethicalengine.Character[] pas = test.getPassengers();
    ethicalengine.Character[] ped = test.getPedestrians();

    //save and update all features
    if(decision == EthicalEngine.Decision.PASSENGERS){
      //update light data
      if(test.isLegalCrossing()){
        int num = 0;
        if(Arrays.asList(feature).contains("green") == false){
          while(num < feature.length){
            if(feature[num] == null){
              feature[num] = "green";
              statistics[num][0] = statistics[num][0] + test.getPassengerCount();
              statistics[num][1] = statistics[num][1] + test.getPassengerCount() + test.getPedestrianCount();
              statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
              break;
            }
            num++;
          }
        }else{
          int num1 = Arrays.asList(feature).indexOf("green");
          statistics[num1][0] += test.getPassengerCount();
          statistics[num1][1] += test.getPassengerCount() + test.getPedestrianCount();
          statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
        }
      }else{
        int num = 0;
        if(Arrays.asList(feature).contains("red") == false){
          while(num < feature.length){
            if(feature[num] == null){
              feature[num] = "red";
              statistics[num][0] += test.getPassengerCount();
              statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
              statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
              break;
            }
            num++;
          }
        }else{
          int num1 = Arrays.asList(feature).indexOf("red");
          statistics[num][0] += test.getPassengerCount();
          statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
          statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
        }
      }
      //passengers survive
      for(int i = 0; i < pas.length; i++){
        if (pas[i].getClass().getName().contains("Person")){//Pas is person
          // add person
          if(Arrays.asList(feature).contains("person") == false){
            int count1 = 0;
            while(count1 < feature.length){
              if(feature[count1] == null){
                feature[count1] = "person";
                statistics[count1][0] += 1;
                statistics[count1][1] += 1;
                statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                break;
              }
              count1++;
            }
          }else{
            int index1 = Arrays.asList(feature).indexOf("person");
            statistics[index1][0] += 1;
            statistics[index1][1] += 1;
            statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
          }
          ageSum += pas[i].getAge();
          aliveCount ++;
          String[] temp = pas[i].toString().split(" ");
          for(int j = 0; j < temp.length; j++){
            if(Arrays.asList(feature).contains(temp[j]) == false){
              int count = 0;
              while(count < feature.length){
                if(feature[count] == null){
                  feature[count] = temp[j];
                  statistics[count][0] += 1;
                  statistics[count][1] += 1;
                  statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                  break;
                }
                count++;
              }
            }else{
              int index = Arrays.asList(feature).indexOf(temp[j]);
              statistics[index][0] += 1;
              statistics[index][1] += 1;
              statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
            }
          }
        }else{//Pas is Animal
          //add animal
          if(Arrays.asList(feature).contains("animal") == false){
            int count1 = 0;
            while(count1 < feature.length){
              if(feature[count1] == null){
                feature[count1] = "animal";
                statistics[count1][0] += 1;
                statistics[count1][1] += 1;
                statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                break;
              }
              count1++;
            }
          }else{
            int index1 = Arrays.asList(feature).indexOf("animal");
            statistics[index1][0] += 1;
            statistics[index1][1] += 1;
            statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
          }
          if(pas[i].toString().contains("pet")){
            //add pet
            if(Arrays.asList(feature).contains("pet") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "pet";
                  statistics[count1][0] += 1;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("pet");
              statistics[index1][0] += 1;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            //add species
            String[] temp2 = pas[i].toString().split(" is ");
            if(Arrays.asList(feature).contains(temp2[0]) == false){
              int count2 = 0;
              while(count2 < feature.length){
                if(feature[count2] == null){
                  feature[count2] = temp2[0];
                  statistics[count2][0] += 1;
                  statistics[count2][1] += 1;
                  statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                  break;
                }
                count2++;
              }
            }else{
              int index2 = Arrays.asList(feature).indexOf(temp2[0]);
              statistics[index2][0] += 1;
              statistics[index2][1] += 1;
              statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
            }
          }else{
            String temp2 = pas[i].toString();
            if(Arrays.asList(feature).contains(temp2) == false){
              int count2 = 0;
              while(count2 < feature.length){
                if(feature[count2] == null){
                  feature[count2] = temp2;
                  statistics[count2][0] += 1;
                  statistics[count2][1] += 1;
                  statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                  break;
                }
                count2++;
              }
            }else{
              int index2 = Arrays.asList(feature).indexOf(temp2);
              statistics[index2][0] += 1;
              statistics[index2][1] += 1;
              statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
            }
          }
        }
      }

      //get ped data----------------------------------------------------------------------------
      for(int i = 0; i < ped.length; i++){

        if (ped[i].getClass().getName().contains("Person")){//Pas is person
          //add person
          if(Arrays.asList(feature).contains("person") == false){
            int count1 = 0;
            while(count1 < feature.length){
              if(feature[count1] == null){
                feature[count1] = "person";
                statistics[count1][0] += 0;
                statistics[count1][1] += 1;
                statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                break;
              }
              count1++;
            }
          }else{
            int index1 = Arrays.asList(feature).indexOf("person");
            statistics[index1][0] += 0;
            statistics[index1][1] += 1;
            statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
          }
          String[] temp = ped[i].toString().split(" ");
          for(int j = 0; j < temp.length; j++){
            if(Arrays.asList(feature).contains(temp[j]) == false){
              int count = 0;
              while(count < feature.length){
                if(feature[count] == null){
                  feature[count] = temp[j];
                  statistics[count][0] += 0;
                  statistics[count][1] += 1;
                  statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                  break;
                }
                count++;
              }
            }else{
              int index = Arrays.asList(feature).indexOf(temp[j]);
              statistics[index][0] += 0;
              statistics[index][1] += 1;
              statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
            }
          }
        }else{//Ped is Animal
          // add Animal
          if(Arrays.asList(feature).contains("animal") == false){
            int count1 = 0;
            while(count1 < feature.length){
              if(feature[count1] == null){
                feature[count1] = "animal";
                statistics[count1][0] += 0;
                statistics[count1][1] += 1;
                statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                break;
              }
              count1++;
            }
          }else{
            int index1 = Arrays.asList(feature).indexOf("animal");
            statistics[index1][0] += 0;
            statistics[index1][1] += 1;
            statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
          }
          if(ped[i].toString().contains("pet")){
            //add pet
            if(Arrays.asList(feature).contains("pet") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "pet";
                  statistics[count1][0] += 0;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("pet");
              statistics[index1][0] += 0;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            //add species
            String[] temp2 = ped[i].toString().split(" is ");
            if(Arrays.asList(feature).contains(temp2[0]) == false){
              int count2 = 0;
              while(count2 < feature.length){
                if(feature[count2] == null){
                  feature[count2] = temp2[0];
                  statistics[count2][0] += 0;
                  statistics[count2][1] += 1;
                  statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                  break;
                }
                count2++;
              }
            }else{
              int index2 = Arrays.asList(feature).indexOf(temp2[0]);
              statistics[index2][0] += 0;
              statistics[index2][1] += 1;
              statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
            }
          }else{//not pet
            String temp2 = ped[i].toString();
            if(Arrays.asList(feature).contains(temp2) == false){
              int count2 = 0;
              while(count2 < feature.length){
                if(feature[count2] == null){
                  feature[count2] = temp2;
                  statistics[count2][0] += 0;
                  statistics[count2][1] += 1;
                  statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                  break;
                }
                count2++;
              }
            }else{
              int index2 = Arrays.asList(feature).indexOf(temp2);
              statistics[index2][0] += 0;
              statistics[index2][1] += 1;
              statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
            }
          }
        }
      }
    }else{//pedestrians survive=======================================================================================
      //update light data
      if(test.isLegalCrossing()){
        int num = 0;
        if(Arrays.asList(feature).contains("green") == false){
          while(num < feature.length){
            if(feature[num] == null){
              feature[num] = "green";
              statistics[num][0] += test.getPedestrianCount();
              statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
              statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
              break;
            }
            num++;
          }
        }else{
          int num1 = Arrays.asList(feature).indexOf("green");
          statistics[num1][0] += test.getPedestrianCount();
          statistics[num1][1] += test.getPassengerCount() + test.getPedestrianCount();
          statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
        }
      }else{
        int num = 0;
        if(Arrays.asList(feature).contains("red") == false){
          while(num < feature.length){
            if(feature[num] == null){
              feature[num] = "red";
              statistics[num][0] += test.getPedestrianCount();
              statistics[num][1] += test.getPassengerCount() + test.getPedestrianCount();
              statistics[num][2] = (float)(Math.floor(statistics[num][0]/statistics[num][1]*10))/10;
              break;
            }
            num++;
          }
        }else{
          int num1 = Arrays.asList(feature).indexOf("red");
          statistics[num1][0] += test.getPedestrianCount();
          statistics[num1][1] += test.getPassengerCount() + test.getPedestrianCount();
          statistics[num1][2] = (float)(Math.floor(statistics[num1][0]/statistics[num1][1]*10))/10;
        }
      }
      //update other features
      for(int i = 0; i < ped.length; i++){
        if (ped[i].getClass().getName().contains("Person")){ //Ped is person
          //add person
          if(Arrays.asList(feature).contains("person") == false){
            int count1 = 0;
            while(count1 < feature.length){
              if(feature[count1] == null){
                feature[count1] = "person";
                statistics[count1][0] += 1;
                statistics[count1][1] += 1;
                statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                break;
              }
              count1++;
            }
          }else{
            int index1 = Arrays.asList(feature).indexOf("person");
            statistics[index1][0] += 1;
            statistics[index1][1] += 1;
            statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
          }
          ageSum += ped[i].getAge();
          aliveCount ++;
          String[] temp = ped[i].toString().split(" ");
          for(int j = 0; j < temp.length; j++){
            if(Arrays.asList(feature).contains(temp[j]) == false){
              int count = 0;
              while(count < feature.length){
                if(feature[count] == null){
                  feature[count] = temp[j];
                  statistics[count][0] += 1;
                  statistics[count][1] += 1;
                  statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                  break;
                }
                count++;
              }
            }else{
              int index = Arrays.asList(feature).indexOf(temp[j]);
              statistics[index][0] += 1;
              statistics[index][1] += 1;
              statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
            }
          }
        }else{                                         //Ped is Animal
          //add animal
          if(Arrays.asList(feature).contains("animal") == false){
            int count1 = 0;
            while(count1 < feature.length){
              if(feature[count1] == null){
                feature[count1] = "animal";
                statistics[count1][0] += 1;
                statistics[count1][1] += 1;
                statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                break;
              }
              count1++;
            }
          }else{
            int index1 = Arrays.asList(feature).indexOf("animal");
            statistics[index1][0] += 1;
            statistics[index1][1] += 1;
            statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
          }
          if(ped[i].toString().contains("pet")){
            //add pet
            if(Arrays.asList(feature).contains("pet") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "pet";
                  statistics[count1][0] += 1;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("pet");
              statistics[index1][0] += 1;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            //add species
            String[] temp2 = ped[i].toString().split(" is ");
            if(Arrays.asList(feature).contains(temp2[0]) == false){
              int count2 = 0;
              while(count2 < feature.length){
                if(feature[count2] == null){
                  feature[count2] = temp2[0];
                  statistics[count2][0] += 1;
                  statistics[count2][1] += 1;
                  statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                  break;
                }
                count2++;
              }
            }else{
              int index2 = Arrays.asList(feature).indexOf(temp2[0]);
              statistics[index2][0] += 1;
              statistics[index2][1] += 1;
              statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
            }
          }else{
            String temp2 = ped[i].toString();
            if(Arrays.asList(feature).contains(temp2) == false){
              int count2 = 0;
              while(count2 < feature.length){
                if(feature[count2] == null){
                  feature[count2] = temp2;
                  statistics[count2][0] += 1;
                  statistics[count2][1] += 1;
                  statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                  break;
                }
                count2++;
              }
            }else{
              int index2 = Arrays.asList(feature).indexOf(temp2);
              statistics[index2][0] += 1;
              statistics[index2][1] += 1;
              statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
            }
          }
        }
      }
      //get pas data--------------------------------------------------------------------
      for(int i = 0; i < pas.length; i++){
        if (pas[i].getClass().getName().contains("Person")){//Pas is person
          //add person
          if(Arrays.asList(feature).contains("person") == false){
            int count1 = 0;
            while(count1 < feature.length){
              if(feature[count1] == null){
                feature[count1] = "person";
                statistics[count1][0] += 0;
                statistics[count1][1] += 1;
                statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                break;
              }
              count1++;
            }
          }else{
            int index1 = Arrays.asList(feature).indexOf("person");
            statistics[index1][0] += 0;
            statistics[index1][1] += 1;
            statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
          }
          String[] temp = pas[i].toString().split(" ");
          for(int j = 0; j < temp.length; j++){
            if(Arrays.asList(feature).contains(temp[j]) == false){
              int count = 0;
              while(count < feature.length){
                if(feature[count] == null){
                  feature[count] = temp[j];
                  statistics[count][0] += 0;
                  statistics[count][1] += 1;
                  statistics[count][2] = (float)(Math.floor(statistics[count][0]/statistics[count][1]*10))/10;
                  break;
                }
                count++;
              }
            }else{
              int index = Arrays.asList(feature).indexOf(temp[j]);
              statistics[index][0] += 0;
              statistics[index][1] += 1;
              statistics[index][2] = (float)(Math.floor(statistics[index][0]/statistics[index][1]*10))/10;
            }
          }
        }else{//Pas is Animal
          //add animal
          if(Arrays.asList(feature).contains("animal") == false){
            int count1 = 0;
            while(count1 < feature.length){
              if(feature[count1] == null){
                feature[count1] = "animal";
                statistics[count1][0] += 0;
                statistics[count1][1] += 1;
                statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                break;
              }
              count1++;
            }
          }else{
            int index1 = Arrays.asList(feature).indexOf("animal");
            statistics[index1][0] += 0;
            statistics[index1][1] += 1;
            statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
          }
          if(pas[i].toString().contains("pet")){
            //add pet
            if(Arrays.asList(feature).contains("pet") == false){
              int count1 = 0;
              while(count1 < feature.length){
                if(feature[count1] == null){
                  feature[count1] = "pet";
                  statistics[count1][0] += 0;
                  statistics[count1][1] += 1;
                  statistics[count1][2] = (float)(Math.floor(statistics[count1][0]/statistics[count1][1]*10))/10;
                  break;
                }
                count1++;
              }
            }else{
              int index1 = Arrays.asList(feature).indexOf("pet");
              statistics[index1][0] += 0;
              statistics[index1][1] += 1;
              statistics[index1][2] = (float)(Math.floor(statistics[index1][0]/statistics[index1][1]*10))/10;
            }
            //add species
            String[] temp2 = pas[i].toString().split(" is ");
            if(Arrays.asList(feature).contains(temp2[0]) == false){
              int count2 = 0;
              while(count2 < feature.length){
                if(feature[count2] == null){
                  feature[count2] = temp2[0];
                  statistics[count2][0] += 0;
                  statistics[count2][1] += 1;
                  statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                  break;
                }
                count2++;
              }
            }else{
              int index2 = Arrays.asList(feature).indexOf(temp2[0]);
              statistics[index2][0] += 0;
              statistics[index2][1] += 1;
              statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
            }
          }else{
            String temp2 = pas[i].toString();
            if(Arrays.asList(feature).contains(temp2) == false){
              int count2 = 0;
              while(count2 < feature.length){
                if(feature[count2] == null){
                  feature[count2] = temp2;
                  statistics[count2][0] += 0;
                  statistics[count2][1] += 1;
                  statistics[count2][2] = (float)(Math.floor(statistics[count2][0]/statistics[count2][1]*10))/10;
                  break;
                }
                count2++;
              }
            }else{
              int index2 = Arrays.asList(feature).indexOf(temp2);
              statistics[index2][0] += 0;
              statistics[index2][1] += 1;
              statistics[index2][2] = (float)(Math.floor(statistics[index2][0]/statistics[index2][1]*10))/10;
            }
          }
        }
      }
    }
  }

  /**
  *{@link #setAuditType(String)} set the name of the audit.
  * @param name
  * @return {@code void}
  */
  public void setAuditType(String name){
    this.name = name;
  }

  /**
  *{@link #getAuditType()} get the name of the audit.
  * @return {@code String}
  */
  public String getAuditType(){
    return this.name;
  }

  /**
  *{@link #sortByValues(HashMap)} sort components in a hashmap by values.
  * @param map
  * @return {@code void}
  */
  private HashMap sortByValues(HashMap map) {
       List list = new LinkedList(map.entrySet());
       Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
               return ((Comparable) ((Map.Entry) (o2)).getValue())
                  .compareTo(((Map.Entry) (o1)).getValue());
            }
       });
       for(int i = 0; i < list.size(); i++){
          for(int j = 0; j < list.size() - 1 - i; j++){
              String n1 = (String)((Map.Entry)list.get(j)).getKey();
              String n2 = (String)((Map.Entry)list.get(j+1)).getKey();
              Map.Entry temp;
              if(((Map.Entry)list.get(j)).getValue().equals(((Map.Entry)list.get(j+1)).getValue())){
                if(n1.compareTo(n2)>0){
                  Collections.swap(list, j, j+1);
                }
              }
            }
          }
       HashMap sortedHashMap = new LinkedHashMap();
       for (Iterator it = list.iterator(); it.hasNext();) {
              Map.Entry entry = (Map.Entry) it.next();
              sortedHashMap.put(entry.getKey(), entry.getValue());
       }
       return sortedHashMap;
  }

  /**
  *@overwrite
  *{@link #toString()} return the statistics of an audit.
  * @return {@code String}
  */
  public String toString(){
    String summary = ("======================================\n" + "# " + name + " Audit\n" + "======================================\n"+
    "- % SAVED AFTER " + run + " RUNS\n");
    if(run > 0){
      HashMap<String, Float> result = new HashMap<String, Float>();

      for(int i = 0; i < feature.length; i++){
        if(feature[i] != null){
          result.put(feature[i], statistics[i][2]);
        }
      }

      Map<String, Float> map = sortByValues(result);
      Set set2 = map.entrySet();
      Iterator iterator2 = set2.iterator();
      while(iterator2.hasNext()) {
        Map.Entry me2 = (Map.Entry)iterator2.next();
        summary += me2.getKey() + ": " + me2.getValue() + "\n";
      }
      avgAge = (float)(Math.floor(ageSum/aliveCount*10))/10;
      summary += "--\n" + "average age: " + avgAge + "\n";
    }else{
      summary = "no audit available\n";
    }
    return summary;
  }

  /**
  *@overwrite
  *{@link #printStatistic()} print the statistics on the screen.
  * @return {@code void}
  */
  public void printStatistic(){
    System.out.print(this.toString());
  }

  /**
  *@overwrite
  *{@link #printToFile()} print the statistics on the screen and save the stats to a specific file.
  * @param filepath
  * @return {@code void}
  */
  public void printToFile(String filepath){
    printStatistic();
    try{
      File file = new File(filepath);
      if(file.exists()){
        FileWriter fr = new FileWriter(file, true);
        BufferedWriter br = new BufferedWriter(fr);
        br.write(this.toString() );

        br.close();
        fr.close();
      }else{
        FileWriter fr = new FileWriter(file, true);
        fr.write(this.toString());

        fr.close();
      }
     }catch (FileNotFoundException e){
      System.out.println("Could not open file: " + filepath);
    }catch(IOException e){
      System.out.println("Could not write the file.");
      e.printStackTrace();
    }
  }
}
