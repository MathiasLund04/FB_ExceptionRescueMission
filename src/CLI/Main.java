package CLI;

import Exceptions.CriticalStatusException;
import Models.Ship;
import Service.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Main {
    public static Scanner input = new Scanner(System.in);
    private static Ship ship = new Ship();
    private static final File file= new File("src/Log");
    public static Service service = new Service(ship,file);


    public static void main(String[] args) {
        String captainName;
        String shipName;
        service.initialize();

        System.out.println("========================================");
        System.out.println(" RUMEVENTYR - EXCEPTION RESCUE MISSION ");
        System.out.println("========================================\n");
        System.out.println("Velkommen Kaptajn");
        do {

            System.out.print("Indtast dit navn: \n> ");
            captainName = input.nextLine().trim();
            if (captainName.isEmpty()) {
                System.out.println("Feltet må ikke være tomt ");
            }
        }   while(captainName.isEmpty());

        do {
                System.out.print("Indtast navnet på skibet: \n> ");
                shipName = input.nextLine().trim();
            if (shipName.isEmpty()) {
                System.out.println("Feltet må ikke være tomt ");
            }
        }   while(shipName.isEmpty());

            System.out.println(" ");
            ship = new Ship(shipName, captainName);

        try {
            service.updateLog("START: velkommen Kaptajn " + captainName + " på skibet " + shipName, file);
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

        service.printStatus();
        try {
            // Event 1
            service.eventStorm(input);

            // Event 2
            service.eventTrade(input);

            // Event 3
            service.eventEngine(input);

            //Sejrs-besked
            System.out.println("Du overlevede rejsen gennem galaksen\n");
        } catch (CriticalStatusException e){
            System.out.println("Du overlevede ikke... " +
                    "\nGAME OVER\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Fejl under missionen: " + e.getMessage() +
                    "\nGAME OVER\n");
        } catch (Exception e) {
            System.out.println("Uventet fejl: " + e.getMessage() +
                    "\nGAME OVER\n");
            e.printStackTrace();
        }

        try {
            service.showLog(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
