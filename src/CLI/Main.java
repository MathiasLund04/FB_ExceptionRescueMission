package CLI;

import Models.Ship;
import Service.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);
    public static Ship ship;
    public static File file;
    public static Service service = new Service();


    public static void main(String[] args) throws FileNotFoundException {
        initialize();
        System.out.println("========================================");
        System.out.println(" RUMEVENTYR - EXCEPTION RESCUE MISSION ");
        System.out.println("========================================\n");
        System.out.println("Velkommen Kaptajn");
        System.out.print("Indtast dit navn: \n> ");
        String captainName = input.nextLine();
        System.out.print("Indtast navnet på skibet: \n> ");
        String shipName = input.nextLine();
        System.out.println(" ");
        ship = new Ship(shipName, captainName);
        service.updateLog("Start velkommen Kaptajn " + captainName + " på skibet " + shipName,file);

        printStatus();

        try {
            service.showLog(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initialize(){
        file = new File("src/Log");


        ship = new Ship();
        ship.setCaptainName(" ");
        ship.setCaptainName(" ");
        ship.setFuel(100);
        ship.setIntegrity(100);
        ship.setSpareParts(10);
        ship.setShieldLevel(0);
        ship.setRepairKitUsed(false);

    }

    private static void printStatus(){
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
}