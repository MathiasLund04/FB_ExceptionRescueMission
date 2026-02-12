package Service;

import Models.Ship;

import java.io.*;
import java.util.Scanner;


public class Service {
    Scanner input = new Scanner(System.in);
    private static Ship ship;
    private static File file = new File("src/Log");


    public Service(Ship ship,File file) {
        Service.ship = ship;
        Service.file =file;

    }


    public void eventStorm(){

    }
    public void eventTrade(Scanner input){
        System.out.println("EVENT 2 - HANDELSPLADS");
        System.out.println("Du lander på en handelsplads hvor du bliver tilbudt handel og opgraderinger ");
        System.out.println("""
                
                Vælg Handling:\s
                1) Byt reservedele for brændstof
                2) Køb Shield level 1 (koster 4 reservedele)
                3) Spring over""");
        System.out.println(ship.getSpareParts());

        int choice = getChoice(input);
        switch(choice) {
            case 1 -> {
                buyFuel();

                System.out.println("""
                        vil du Køb Shield level 1 (koster 4 reservedele)
                        1) Ja
                        2) Nej
                        """);
                switch (input.nextInt()) {
                    case 1 -> buyShield();
                    case 2 -> System.out.println("Du valgte ikke at købe shield");
                    default -> System.out.println("Ugyldigt valg.");
                }
            }
            case 2 -> {
                buyShield();
                System.out.println("""
                        vil du Køb brændstof
                        1) Ja
                        2) Nej
                        """);

                switch (input.nextInt()) {
                    case 1 -> buyFuel();
                    case 2 -> System.out.println("Du valgte ikke at købe mere brændstof");
                    default -> System.out.println("Ugyldigt valg.");
                }
            }
            case 3 -> System.out.println("Du sprang handlen over.");
            default -> System.out.println("Ugyldigt valg.");
        }

    }
    public void eventEngine(){

    }
    public void checkCriticalStatus(){

    }

    //EVENT 2 METHODS
    public Boolean validateTrade(int amount) throws IllegalArgumentException{
        if(amount < 0){
            System.out.println("Antal reservedele kan ikke være negativt");
            try {
                updateLog("EVENT 2: Ugyldig handel ("+amount+")",file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return true;
        }else if(ship.getSpareParts() < amount){
            System.out.println("Du har ikke nok reservedele");
            try {
                updateLog("EVENT 2: Ugyldig handel ("+amount+"/"+ship.getSpareParts()+")",file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return true;
        }else {
        return false;
        }
    }
    public void buyFuel(){
        int tradedAmount = 0;
        int amount = 0;
        int fuelConvert;
        boolean validCheck = true;
        while (validCheck) {
            System.out.println("hvor mange reservedele vil du bytte? (1:5)");
            amount = input.nextInt();
           if(validateTrade(amount) == false){
               validCheck = false;
               tradedAmount = amount;
           }
        }

        fuelConvert = tradedAmount * 5;
        ship.setFuel(ship.getFuel() + fuelConvert);
        ship.setSpareParts(ship.getSpareParts() - tradedAmount);
        try {
            updateLog("EVENT 2: handel "+amount + " reservedele -> "+tradedAmount+" brændstof" ,file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Handel gennemført \n"+
                tradedAmount + " reservedele -> " + fuelConvert + " Brændstof");
        printStatus();

    }
    public void buyShield(){
        int spareParts = ship.getSpareParts();
        validateTrade(spareParts);
        if(validateTrade(spareParts) == false) {
            ship.setShieldLevel(1);
            ship.setSpareParts(ship.getSpareParts()-4);
            System.out.println("Shield level 1 aktiveret");
            try {
                updateLog("EVENT 2: Shield 1 købt" ,file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        printStatus();
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

    //LAVER ET SHIP OBJEKT OG SETTER START VÆRDIERNE
    public void initialize(){

        ship = new Ship();
        ship.setCaptainName(" ");
        ship.setCaptainName(" ");
        ship.setFuel(100);
        ship.setIntegrity(100);
        ship.setSpareParts(10);
        ship.setShieldLevel(0);
        ship.setRepairKitUsed(false);

    }

    private static int getChoice(Scanner input){
        int choice;
        while (true){
            try {
                choice = input.nextInt();
                break;
            } catch (NumberFormatException e){
                System.out.println("Venligst indtast et tal.");
                input.next();
            }
        }
        return choice;
    }

}
