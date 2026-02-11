package Service;

import CLI.Main;

import java.io.*;
import java.util.Scanner;

public class Service {
    Scanner input = new Scanner(System.in);
    public void eventStorm(){

    }
    public void eventTrade(){
        int choice = getChoice(input);
        int amount = 0;
        System.out.println("EVENT 2 - HANDELSPLADS");
        System.out.println("Du lander på en handelsplads hvor du bliver tilbudt handel og opgraderinger ");
        System.out.println("""
                
                Vælg Handling:\s
                1. Byt reservedele for brændstof
                2. Køb Shield level 1 (koster 4 reservedele)
                3. Spring over""");

        switch(choice) {
            case 1:
                while (validateTrade(amount) == true) {
                    System.out.println("hvor mange reservedele vil du bytte? (1:5)");
                    amount = input.nextInt();
                     validateTrade(amount);
                }
                tradeCalculate(amount);
                break;
            case 2:
                int spareParts = Main.ship.getSpareParts();
                validateTrade(spareParts);
                if(validateTrade(spareParts) == false) {
                    Main.ship.setShieldLevel(1);
                    break;
                }
                System.out.println("");

            case 3:
                break;
        }

    }
    public void eventEngine(){

    }
    public void checkCriticalStatus(){

    }

    public Boolean validateTrade(int amount){
        if(amount < 0){
            throw new IllegalArgumentException("Antal reservedele kan ikke være negativt");
        }else if(amount > Main.ship.getSpareParts()){
            throw new IllegalArgumentException("Du har ikke nok reservedele");
        }else {
        return false;
        }
    }

    public void tradeCalculate(int amount){
        int fuelConvert;

        fuelConvert = amount * 5;
        Main.ship.setFuel(Main.ship.getFuel() + fuelConvert);
    }


    public void updateLog(String message,File file) throws FileNotFoundException {

        if (file.exists()) {
            try(FileWriter writer = new FileWriter(file,true)) {
                writer.write("\n"+message);
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
