package Service;

import Exceptions.CriticalStatusException;
import Exceptions.InvalidTradeException;
import Models.Ship;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Service {
    Scanner input = new Scanner(System.in);
    private static Ship ship;
    private static File file = new File("src/Log");


    public Service(Ship ship,File file) {
        Service.ship = ship;
        Service.file =file;

    }

    // Event nr. 1 - Rejse gennem rumstorm
    public void eventStorm(Scanner input) {
        System.out.println("EVENT 1 - RUMSTORM");
        System.out.println("En voldsom meteor storm blokerer din rute frem. ");

        System.out.println("""
                Vælg handling:
                1) Flyv igennem meteor stormen (Høj risiko for skade)
                2) Tag en længere omvej (Koster 10 fuel, mindre skade)
                """);
        int choice = checkChoice(input);


        try {
            boolean running = true;
        while(running) {
            switch (choice) {
                case 1 -> {
                    flyThroughStorm();
                running = false;}
                case 2 -> {
                    takeDetour();
                    running = false;
                }
                default -> {
                    System.out.print("Ugyldigt valg.\n>");
                    choice = checkChoice(input);
                }
            }
        }
        } catch (Exception e) {
            System.out.println("Fejl under storm-event: " + e.getMessage());
            logStorm("Stormfejl: " + e.getMessage());
        }
        //Efter event
        printStatus();

        checkCriticalStatus();

    }


    // Case 1 - Flyv gennem stormen
    private void flyThroughStorm() {
        int integrityDamage = (int) (Math.random() * 30) + 10; // Skaden kan være mellem 10 til 40

        if (ship.getFuel() < 5)
            if (ship.getShieldLevel() > 0) {
            integrityDamage -= (ship.getShieldLevel() * 2);
            if (integrityDamage < 0) integrityDamage = 0;
        }

        ship.setIntegrity(ship.getIntegrity() - integrityDamage);
        ship.setFuel(ship.getFuel() - 5);

        System.out.println(" Du fløj igennem stormen \n -5 brændstof og tog " + integrityDamage + " skade.");
        logStorm("Storm: -" + integrityDamage);

    }

    // Case 2 - Tage en omvej
    private void takeDetour() {
        if (ship.getFuel() < 10) {
            throw new IllegalArgumentException("Ikke nok brandstof til at tage en længere omvej.");
        }

        ship.setFuel(ship.getFuel() - 10);

        int integrityHealth = (int) (Math.random() * 10) + 1; // Skaden går fra 1 til 10
        ship.setIntegrity(ship.getIntegrity() - integrityHealth);

        System.out.println(" Du tog en omvej, og mistede 10 fuel og tog " + integrityHealth + " skade.");
        logStorm("Omvej: -10 fuel -" + integrityHealth +" HP");
    }

    //EVENT 1 METHODS
    public void logStorm(String message){
        try {
            updateLog("EVENT 1: " + message, file);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Event nr. 2 - Handel og shield
    public void eventTrade(Scanner input){
        System.out.println("EVENT 2 - HANDELSPLADS");
        System.out.println("Du lander på en handelsplads hvor du bliver tilbudt handel og opgraderinger ");
        System.out.println("""
                
                Vælg handling:\s
                1) Byt reservedele for brændstof
                2) Køb Shield level 1 (koster 4 reservedele)
                3) Spring over""");

        int choice = checkChoice(input);
        boolean running = true;
        while (running) {
            switch (choice) {
                case 1 -> {
                    buyFuel();
                    System.out.println("""
                            Vil du købe Shield level 1 (koster 4 reservedele)
                            1) Ja
                            2) Nej
                            """);
                        boolean internalMenu = true;
                    while (internalMenu) {
                        switch (input.nextInt()) {
                            case 1 -> {
                                buyShield();
                                internalMenu = false;
                            }
                            case 2 -> {
                                System.out.println("Du valgte ikke at købe shield");
                                internalMenu = false;
                            }
                            default -> {
                                System.out.println("Ugyldigt valg.");
                                choice = checkChoice(input);
                            }
                        }
                    }
                    running = false;
                }
                case 2 -> {
                    buyShield();
                    System.out.println("""
                            Vil du købe brændstof
                            1) Ja
                            2) Nej
                            """);
                    boolean internalMenu = true;
                    while (internalMenu) {
                        switch (input.nextInt()) {
                            case 1 -> {
                                buyFuel();
                                internalMenu = false;
                            }
                            case 2 -> {
                                System.out.println("Du valgte ikke at købe mere brændstof");
                                internalMenu = false;
                            }
                            default -> {
                                System.out.println("Ugyldigt valg.");
                                choice = checkChoice(input);
                            }
                        }
                    }
                    running = false;
                }
                case 3 -> {
                    System.out.println("Du sprang handlen over.");
                    logTrade("Du sprang over");
                    running = false;
                }
                default -> {
                    System.out.println("Ugyldigt valg.");
                    choice = checkChoice(input);
                }
            }
        }
        checkCriticalStatus();
    }
    //EVENT 2 METHODS
    public Boolean validateTrade(int amount){

        if(amount <= 0){
            logTrade("Ugyldig handel ("+amount+")");
            throw new IllegalArgumentException("Antal reservedele kan ikke være negativt");
        }
        if(amount > ship.getSpareParts()){
            logTrade("Ugyldig handel ("+amount+"/"+ship.getSpareParts()+")");
            throw new InvalidTradeException("Du har ikke nok reservedele");
        }else {
            return true;
        }
    }
    public void logTrade(String message){
        try {
            updateLog("EVENT 2: " + message ,file);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public void buyFuel(){
        int tradedAmount = 0;
        int amount = 0;
        int fuelConvert;
        boolean validCheck = true;
        while (validCheck) {
            try {
                System.out.println("Hvor mange reservedele vil du bytte? (1:5)");
                amount = input.nextInt();

                if(validateTrade(amount)){
                    validCheck = false;
                    tradedAmount = amount;
                }
            }catch (InvalidTradeException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        fuelConvert = tradedAmount * 5;
        ship.setFuel(ship.getFuel() + fuelConvert);
        ship.setSpareParts(ship.getSpareParts() - tradedAmount);

        logTrade("Handel "+amount +" reservedele -> "+fuelConvert+" brændstof" );
        System.out.println("Handel gennemført \n"+
                tradedAmount + " reservedele -> " + fuelConvert + " Brændstof");
        printStatus();

    }
    public void buyShield(){
        int spareParts = ship.getSpareParts();
        try {
            validateTrade(spareParts);
            if (validateTrade(spareParts)) {
                ship.setShieldLevel(1);
                ship.setSpareParts(ship.getSpareParts() - 4);
                System.out.println("Shield level 1 aktiveret");
                logTrade("Shield 1 købt");
            }
        } catch (InvalidTradeException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        printStatus();
    }

    // Event nr. 3 - Genstart af motor og repair kit
    public void eventEngine(Scanner input){
        System.out.println("EVENT 3 - DEFEKT MOTOR");
        System.out.println("Motoren fejler pludseligt\n");

        System.out.println("Vil du bruge repair kit nu? (kan kun bruges én gang) " +
                "\n1) Ja" +
                "\n2) Nej \n>");

        int choice = checkChoice(input);
        boolean running = true;
        while (running) {
             if (choice == 1 && !ship.getRepairKitUsed()) {
                 ship.setRepairKitUsed(true);
                 System.out.println("Repair kit brugt" +
                         "\nIntegritet +20\n");
                 ship.setIntegrity(ship.getIntegrity() + 20);
                 printStatus();
                 logEngine("Repair kit brugt");
                 running = false;
             } else if (choice == 2) {
                 System.out.println("Repair kit blev ikke brugt\n");
                 logEngine("Repair kit ikke brugt");
                 running = false;
             } else {
                 System.out.println("Venligst indtast et tal");
                 choice = checkChoice(input);
             }

        }
        restartEngine();
        System.out.println("\n");
        printStatus();
        System.out.println("\nMISSION FULDFØRT\n");

    }

    //EVENT 3 METODER
    public void restartEngine(){
        int starting;
        int unluckyTries = 0;
        boolean success = false;
        boolean failed = false;

        System.out.println("Forsøger at genstarte...");

        for (int i = 0; i <= 3; i++) {
            try {
                starting = (int) (Math.random() * 10) + 1;

                if (starting <= 5) {
                    System.out.println("Genstart lykkedes" +
                            "\nMotor kører igen");
                    logEngine("Motor genstart lykkedes");

                    success = true;
                    break;
                } else {
                    System.out.println("Genstart mislykkedes" +
                            "\n Integritet -15");
                    ship.setIntegrity(ship.getIntegrity() - 15);
                    logEngine("Motorfejl, genstart fejlede (forsøg " + (i+1) + ")");
                    unluckyTries++;
                }
                checkCriticalStatus(unluckyTries);

            } catch (CriticalStatusException e) {
                failed = true;
                logEngine("Motor genstart mislykkedes");
                throw e;
            } finally {
                if (!success && i < 2 && !failed) {
                    System.out.println("Forsøger at genstarte...");
                }
            }
        }
    }
    public void logEngine(String message){
        try {
            updateLog("EVENT 3: " + message ,file);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Check critical status
    public void checkCriticalStatus(){
        if (ship.getFuel() < 10) {
            throw new CriticalStatusException("Kritisk tilstand! Mangel på brændstof");
        }
        if (ship.getIntegrity() < 20){
            throw new CriticalStatusException("Kritisk tilstand! Farlig lav integritet");
        }
    }
    public void checkCriticalStatus(int unluckyTries){
        checkCriticalStatus();
        if (unluckyTries >= 2){
            throw new CriticalStatusException("Motor er i kritisk tilstand og kan ikke genstartes");
        }
    }

    public void printStatus(){
        System.out.println("----------------------------------------");
        System.out.println("STATUS");
        System.out.println("Brændstof: " + ship.getFuel() +
                "\nIntegritet: " + ship.getIntegrity() +
                "\nReservedele: " + ship.getSpareParts() +
                "\nShield: " + ship.getShieldLevel()
        );
        if (ship.getRepairKitUsed()){
            System.out.println("Repair Kit: Brugt");
        } else {
            System.out.println("Repair Kit: Ikke brugt");
        }

        System.out.println("----------------------------------------");
    }

    //Metoder til fil
    public void updateLog(String message, File file) throws FileNotFoundException {

        if (file.exists()) {
            try(FileWriter writer = new FileWriter(file,true)) {
                writer.write("\n"+ message);
            } catch (FileNotFoundException e){
                throw new FileNotFoundException("Fil " + file.getName() + " ikke fundet.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void showLog(File file) throws FileNotFoundException {

        try (FileReader reader = new FileReader(file);
            Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("Fil " + file.getName() + " ikke fundet.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void resetLog(File file) throws FileNotFoundException {
        if (file.exists()) {
            try (FileWriter write = new FileWriter(file, false)){
                write.write("EVENTLOG\n");
            } catch (FileNotFoundException e){
                throw new FileNotFoundException("Fil " + file.getName() + " ikke fundet.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //LAVER ET SHIP OBJEKT OG SETTER START VÆRDIERNE
    public void initialize(){
        try {
            resetLog(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ship = new Ship();
        ship.setCaptainName(" ");
        ship.setCaptainName(" ");
        ship.setFuel(100);
        ship.setIntegrity(100);
        ship.setSpareParts(10);
        ship.setShieldLevel(0);
        ship.setRepairKitUsed(false);

    }


    private static int checkChoice(Scanner input){
        int choice;
        while (true){
            try {
                choice = input.nextInt();
                input.nextLine();
                return choice;
            } catch (NumberFormatException | NullPointerException | InputMismatchException e) {
                System.out.print("Venligst indtast et tal \n> ");
                input.nextLine();
            }
        }
    }

}
