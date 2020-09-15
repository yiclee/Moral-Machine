/**
 * Class {@code EthicalEngine} holds the main method and manages the program execution.
 * This class houses the {@code decide(scenario)} method which choose whom to save for any scenario.
 * @author Yi-Ching Lee, yiclee@student.unimelb.edu.au, 1038344
 *
 */
import ethicalengine.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.io.Serializable;
public class EthicalEngine{

  /**
  *{@link enum#Decision} include {@code PASSENGERS} and {@code PEDESTRIANS}.
  * users make decisions (save who?) in each scenario.
  */
  public enum Decision{
    PEDESTRIANS, PASSENGERS
  }

  /**
  *{@link #main(String[])} the main function and coordinates the program flow.
  * @param args users' commands, include {@code -c -r -h -i} or just {@code null}.
  * @return {@code void}
  */
  public static void main(String args[]){
    Scanner sc = new Scanner(System.in);
    String configdoc = null;
    String DEFAULT_USERPATH = "user.log";
    String DEFAULT_PATH = "results.log";
    String rPath = null;
    String file = "";

    if(args.length != 0){
      List<String> option = Arrays.asList(args);
      for(String input: option){
        if(input.equals("--config") || input.equals("--help") || input.equals("--results") || input.equals("--interactive")){
          option.set(option.indexOf(input), input.substring(1, 3));
        }
      }
      /*==================================================== check options =======================================================*/
      if(option.contains("-i") && !option.contains("-c") && !option.contains("-r")){//-i
        //create random scenarios and run with Interaction, save files to user.log if needed
        rPath = DEFAULT_USERPATH;
        Audit test = new Audit();
        test.setAuditType("User");
        ethicalengine.ScenarioGenerator sg = new ethicalengine.ScenarioGenerator();
        int randomCount = ethicalengine.ScenarioGenerator.getRandomRunCounts();
        ethicalengine.Scenario[] scenarios = new ethicalengine.Scenario[randomCount];
        for(int i = 0; i < randomCount; i++){
          scenarios[i] = sg.generate();
        }
        InteractionWithConfig(scenarios, rPath);
      }else if(option.contains("-i") && option.contains("-c") && !option.contains("-r")){//-i-c
        //import config files and run with Interaction, save files to user.log if needed
        rPath = DEFAULT_USERPATH;
        file = option.get(option.indexOf("-c")+1);
        InteractionWithConfig(parseFile(file), rPath);

      }else if(!option.contains("-i") && option.contains("-c") && option.contains("-r")){//-c-r
        //import config, decide by program and save files to -r path
        rPath = option.get(option.indexOf("-r")+1);
        file = option.get(option.indexOf("-c")+1);
        Audit test = new Audit(parseFile(file));
        loadFile("welcome.ascii");
        test.run();
        test.printToFile(rPath);
      }else if(!option.contains("-i") && option.contains("-c") && !option.contains("-r")){//-c
        //import config, decide by program and save files to results.log
        rPath = DEFAULT_PATH;
        file = option.get(option.indexOf("-c")+1);
        Audit test = new Audit(parseFile(file));
        loadFile("welcome.ascii");
        test.run();
        //loadFile("welcome.ascii");
        test.printToFile(rPath);
      }else if(!option.contains("-i") && !option.contains("-c") && option.contains("-r")){//-r
        //create scenarios, decide by program and save files to -r path
        rPath = option.get(option.indexOf("-r")+1);
        ethicalengine.ScenarioGenerator sg = new ethicalengine.ScenarioGenerator();
        int randomCount = ethicalengine.ScenarioGenerator.getRandomRunCounts();
        ethicalengine.Scenario[] scenarios = new ethicalengine.Scenario[randomCount];
        for(int i = 0; i < randomCount; i++){
          scenarios[i] = sg.generate();
        }
        Audit test = new Audit(scenarios);
        test.run();
        loadFile("welcome.ascii");
        test.printToFile(rPath);
      }else if(option.contains("-i") && option.contains("-c") && option.contains("-r")){//-i-c-r
        //import config files and run with Interaction, save file to -r path if needed
        rPath = option.get(option.indexOf("-r")+1);
        file = option.get(option.indexOf("-c")+1);
        InteractionWithConfig(parseFile(file), rPath);
      }else{//invalid input
        printHelp();
      }
      /*==================================================== NO options =======================================================*/
    }else{
      //create scenarios, decide by program and save files results.log
      rPath = DEFAULT_PATH;

      ethicalengine.ScenarioGenerator sg = new ethicalengine.ScenarioGenerator();
      int randomCount = ethicalengine.ScenarioGenerator.getRandomRunCounts();
      ethicalengine.Scenario[] scenarios = new ethicalengine.Scenario[randomCount];
      for(int i = 0; i < randomCount; i++){
        scenarios[i] = sg.generate();
      }
      Audit test = new Audit(scenarios);
      // Audit test = new Audit();
      // test.run(2);
      loadFile("welcome.ascii");
      test.printToFile(rPath);
    }

  }

  /**
  *{@link #decide(Scenario)} import configuration and decide who to save .
  * @param scenario
  * @return {@code Decision} which {@code PEDESTRINAS} or {@code PASSENGERS} servive.
  */
  public static Decision decide(Scenario scenario){
    Decision decision = null;
    int pas = 0;
    int ped = 0;

    if(scenario.isLegalCrossing()){
      ped++;
    }else{
      pas++;
    }

    if(scenario.getPassengers().length > scenario.getPedestrians().length){
      pas++;
    }else{
      ped++;
    }
    //check scenario.getPassengers()
    for(int i = 0; i < scenario.getPassengers().length; i++){
      if(scenario.getPassengers()[i].toString().contains("you")){
        pas += 2;
      }
      if(scenario.getPassengers()[i].toString().contains("pregnant")){
        pas += 2;
      }
      if(scenario.getPassengers()[i].toString().contains("engineer")){
        pas ++;
      }
      if(scenario.getPassengers()[i].toString().contains("child")){
        pas ++;
      }
      if(scenario.getPassengers()[i].toString().contains("dog")){
        pas ++;
      }
    }
    //check getPedestrians()
    for(int j = 0; j < scenario.getPedestrians().length; j++){
      if(scenario.getPedestrians()[j].toString().contains("you")){
        ped += 2;
      }
      if(scenario.getPedestrians()[j].toString().contains("pregnant")){
        ped += 2;
      }
      if(scenario.getPedestrians()[j].toString().contains("engineer")){
        ped ++;
      }
      if(scenario.getPedestrians()[j].toString().contains("child")){
        ped ++;
      }
      if(scenario.getPedestrians()[j].toString().contains("dog")){
        ped ++;
      }
    }
    //make a final decision
    if(pas > ped){
      decision = Decision.PASSENGERS;
    }else{
      decision = Decision.PEDESTRIANS;
    }
    return decision;
  }

  /**
  * @param filename import ascii file and load at the start of the program.
  * @return {@code void} and shows the welcome message on the screen.
  */
  public static void loadFile(String filename){
    try{
      File fr = new File("welcome.ascii");
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fr), "ASCII"));
      String text="";
      while (br.ready()) {
        text += br.readLine() + "\n";
      //System.out.println(br.readLine());
      }
      System.out.print(text);
      //fr.close();
    }catch (FileNotFoundException e){
      System.out.println("Could not open file: " + filename);
    }catch(IOException e){
      System.out.println("Could not read from file.");
      e.printStackTrace();
    }
  }

  /**
  *{@link #parseFile(String)} parse the impoted config file and seperate it into scenarios.
  * @param configFile import a configuration file.
  * @return {@code Scenario[]} all scenarios which are parsed from the imported file.
  */
  public static Scenario[] parseFile(String configFile){
    ethicalengine.Character[] passengers = null;
    ethicalengine.Character[] pedestrians = null;
    boolean isLegalCrossing = false;
    String file = "";
    try{
        String row;
        FileReader fr = new FileReader(configFile);
        BufferedReader br = new BufferedReader(fr);
        while ((row = br.readLine()) != null){
          String[] data = row.split(",");
          for(String i: data){
            file += i + "*";
          }
          file += ",";
        }
        String[] record = file.split(",");//all rows             //split into rows

        int scenarioNum = 0;
        for(int i = 0; i < record.length; i++){         //num of scenarios
          if(record[i].contains("scenario")){
            scenarioNum++;
          }
        }

        ethicalengine.Scenario[] config = new ethicalengine.Scenario[scenarioNum];
        int scenarioCount = 0;
        int i = 1;//row num

        while(scenarioCount < scenarioNum && i < record.length){

          String[] light = record[i].split("\\*");
          if(light[0].contains("scenario:green")){//check light
            isLegalCrossing = true;
          }else{
            isLegalCrossing = false;
          }

          List<ethicalengine.Character> pasList = new ArrayList<ethicalengine.Character>();
          List<ethicalengine.Character> pedList = new ArrayList<ethicalengine.Character>();

          List<Object> object = addCharacter(pasList, pedList, record, i+1);
          List<ethicalengine.Character> newPas = (List<ethicalengine.Character>)object.get(0);
          List<ethicalengine.Character> newPed = (List<ethicalengine.Character>)object.get(1);
          pasList = newPas;
          pedList = newPed;
          i = (int)object.get(2);



          passengers = new ethicalengine.Character[pasList.size()];
          for(int size = 0; size < pasList.size(); size++){
            passengers[size] = pasList.get(size);
          }

          pedestrians = new ethicalengine.Character[pedList.size()];
          for(int size = 0; size < pedList.size(); size++){
            pedestrians[size] = pedList.get(size);
          }
                                 //============a new scenario=============//
          ethicalengine.Scenario scenario = new Scenario(passengers, pedestrians, isLegalCrossing);
                        //============put a new scenario into config=============//
          config[scenarioCount] = scenario;
          //renew scenario settings
          passengers = null;
          pedestrians = null;
          isLegalCrossing = false;
          scenarioCount++;
          i++;
        }
    fr.close();

    return config;
    }catch(FileNotFoundException e){
      System.out.println("ERROR: could not find config file.");
    }catch(IOException e){
      System.out.println("Could not read from file.");
      e.printStackTrace();
    }
    return null;
  }

  /**
  *{@link #addCharacter(List, List, String ,int)} extract the config file and get the passenger and pedestrian lists of a scenario.
  * @param List<Character> lists of passeners and pedestrinans get the characters from scenarios.
  * @return {@code List<Object>} will be used to parse scenarios.
  * @throws InvalidDataFormatExceptionthe number of values in one row is less than or exceeds 10 values.
  * @throws NumberFormatException value can not cast into an existing data type.
  * @throws InvalidCharacteristicException  program does not accommodate a specific value.
  */
  public static List<Object> addCharacter(List<ethicalengine.Character> pasList, List<ethicalengine.Character> pedList, String[] record, int i){
    do{//run records
          String[] temp = record[i].split("\\*");
          while(CheckDataFormat(temp, i) < 0){
            try{
              if(CheckDataFormat(temp, i) < 0 && temp[0].contains("scenario")==false){
                throw new InvalidDataFormatException("WARNING: invalid data format in config file in line ", i);
              }
            }catch(InvalidDataFormatException e){
              System.out.print(e+"\n") ;
            }
            i++;
            temp = record[i].split("\\*");
          }
          if(temp[0].equals("person")){
            int age;
            try{
              age = Integer.parseInt(temp[2]);
            }catch(NumberFormatException e){
              age = 25;
              System.out.print("WARNING: invalid number format in config file in line " + (i+1)+"\n");
            }
            ethicalengine.Person.Profession profession;
            if(temp[4].contains(" ")){
              profession = ethicalengine.Person.Profession.NONE;
            }else{
              profession = ethicalengine.Person.findProfession(temp[4]);
            }
            ethicalengine.Character.Gender gender;
            if(temp[1].equals("male")){
              gender = ethicalengine.Character.Gender.MALE;
            }else{
              gender = ethicalengine.Character.Gender.FEMALE;
            }
            try{
              if(ethicalengine.Character.findBodyType(temp[3]) == null){
                i++;
                throw new InvalidCharacteristicException("WARNING: invalid data format in config file in line ", i);
              }
            }catch(InvalidCharacteristicException e){
              temp[3] = "UNSPECIFIED";
              System.out.print(e+"\n");
            }
            ethicalengine.Character.BodyType n = ethicalengine.Character.findBodyType(temp[3]);
            ethicalengine.Character.BodyType bodyType = n;
            boolean isPregnant;
            if(temp[5].equals("true")){
              isPregnant = true;
            }else{
              isPregnant = false;
            }

            ethicalengine.Person person = new Person(age, profession, gender, bodyType, isPregnant);

            if(temp[6].equals("true")){
              person.setAsYou(true);
            }else{
              person.setAsYou(false);
            }

            if(temp[9].contains("passenger")){
              pasList.add(person);
            }else{
              pedList.add(person);
            }
          }else{//---------------------------------------------------if it's an animal----------------------------------------------------//
            String type = temp[7].toUpperCase();
            int age = Integer.parseInt(temp[2]);
            ethicalengine.Character.Gender gender;
            if(temp[1].contains("male")){
              gender = ethicalengine.Character.Gender.MALE;
            }else{
              gender = ethicalengine.Character.Gender.FEMALE;
            }

            ethicalengine.Character.BodyType bodyType = null;
            boolean isPet;
            if(temp[8].contains("true")){
              isPet = true;
            }else{
              isPet = false;
            }
            ethicalengine.Animal animal = new Animal(type, age, gender, bodyType, isPet);
            if(temp[9].contains("passenger")){
              pasList.add(animal);
            }else{
              pedList.add(animal);
            }
          }
      i++;
    }while(i < record.length && record[i].contains("scenario") == false); //end do loop
      i--;
      List<Object> list = new ArrayList<Object>();
      list.add(pasList);
      list.add(pedList);
      list.add(i);
      return list;
    }

  /**
  *{@link #CheckDataFormat(String[] ,int)} check the data amount in a row is 10 precisely.
  * @param rows[lineCount] lists of passeners and pedestrinans get the characters from scenarios.
  * @return {@code 0} or {@code -1}  will be used to parse scenarios.
  */
  public static int CheckDataFormat(String[] rows, int lineCount){
    if(rows.length < 10 || rows.length > 10) {
      return -1;
    }
    return 0;
  }

  /**
  *{@link #printHelp()} if users have the need or invid command, help messages will show.
  * @return {@code void} print the 'help' message on the screen directly.
  */
  public static void printHelp(){
    System.out.print("EthicalEngine - COMP90041 - Final Project\n\n");
    System.out.print("Usage: java EthicalEngine [arguments]\n\n");
    System.out.print("Arguments:\n");
    System.out.print("   -c or --config Optional: path to config file\n");
    System.out.print("   -h or --help Print Help (this message) and exit\n");
    System.out.print("   -r or --results Optional: path to results log file\n");
    System.out.print("   -i or --interactive Optional: launches interactive mode\n");
  }

  /**
  *{@link #InteractionWithConfig(Scenario[], String)} this function allows users to interact with scenarios (make decisions).
  * Save the result into target file if users consent.
  * @param importConfig Scenarios from the configuration file.
  * @param path target file path to save the result
  * @return {@code void}
  */
  public static void InteractionWithConfig(Scenario[] importConfig, String path){

    Scanner sc = new Scanner(System.in);
    String answer = null;
    boolean validInput = false;
    do{
      loadFile("welcome.ascii");
      System.out.print("Do you consent to have your decisions saved to a file? (yes/no)\n");
      try{
        answer = sc.nextLine();
        if(!answer.equals("yes") && !answer.equals("no")){
          throw new InvalidInputException("Invalid response. Do you consent to have your decisions saved to a file? (yes/no)");
        }
        validInput = true;
      }catch(InvalidInputException e){
        System.out.println(e);
        validInput = false;
      }
    }while(validInput == false);
          //=====================present scenarios============================
    Audit user = new Audit(importConfig);
    user.setAuditType("User");
    int storyCount = 0;
    boolean continueRun = false;

    do{
          boolean inRange = true;
          int tempRange;

          if(storyCount + 3 < importConfig.length){
            tempRange = storyCount + 3;
          }else{
            tempRange = importConfig.length;
          }
          while(storyCount < tempRange){
            System.out.print(importConfig[storyCount].toString());
            System.out.print("\nWho should be saved? (passenger(s) [1] or pedestrian(s) [2])\n");
            String choice = sc.nextLine();
            Decision saveWho = null;
            if(choice.equals("passenger") || choice.equals("passengers") || choice.equals("1")){
              saveWho = Decision.PASSENGERS;
            }else{
              saveWho = Decision.PEDESTRIANS;
            }
            user.interactRun(saveWho, storyCount);
            storyCount++;
          }

          //check user consent
          if(answer.equals("yes")){
            user.printToFile(path);
          }else{
            user.printStatistic();
          }
          if(storyCount < importConfig.length){
            inRange =  true;
          }else{
            inRange = false;
          }
          //end session
          if(inRange != true){
            continueRun = false;
            System.out.print("That's all. Press Enter to quit.\n");
            String enter = sc.nextLine();
            if(enter.equals("")){
              System.exit(0);
            }
          }else{
            System.out.print("Would you like to continue? (yes/no)\n");
            String ans = sc.nextLine();
            if(ans.equals("yes")){
              continueRun = true;
            }else{
              continueRun = false;
              System.out.print("That's all. Press Enter to quit.\n");
              String enter = sc.nextLine();
              if(enter.equals("")){
                System.exit(0);
              }
            }
          }
    }while(continueRun);
  }

  /**
  *{@code InvalidDataFormatException} return a WARNING message when there is an invalid data format error in config file.
  * @return {@link #toString()} return a WARNING message.
  */
  static class InvalidDataFormatException extends Exception{
    String str1;
    int count;

    InvalidDataFormatException(String str2, int i) {
      str1=str2;
      count = i;
    }
    public String toString(){
      return ("WARNING: invalid data format in config file in line " + (count + 1)) ;
    }
  }

  /**
  *{@code InvalidCharacteristicException} return a WARNING message when there is an invalid characteristic in config file.
  * @return {@link #toString()} return a WARNING message.
  */
  static class InvalidCharacteristicException extends Exception{
    String str1;
    int count;

    InvalidCharacteristicException(String str2, int i) {
      str1=str2;
      count = i;
    }
    public String toString(){
      return ("WARNING: invalid characteristic in config file in line " + (count + 4)) ;
    }
  }

  /**
  *{@code InvalidInputException} return a WARNING message when there is an Invalid response/input.
  * @return {@link #toString()} return a WARNING message.
  */
  static class InvalidInputException extends Exception{
    String str1;

    InvalidInputException(String str2) {
      str1=str2;
    }
    public String toString(){
      return ("Invalid response. Do you consent to have your decisions saved to a file? (yes/no)") ;
    }
  }

}
