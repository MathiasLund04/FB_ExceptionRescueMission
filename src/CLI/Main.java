package CLI;

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


    public static void main(String[] args) throws FileNotFoundException {
        service.initialize();

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

        service.printStatus();

        service.eventTrade(input);

        try {
            service.showLog(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}