package Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Service {

    public void eventStorm(){

    }
    public void eventtrade(){

    }
    public void eventEngine(){

    }
    public void checkCriticalStatus(){


    }

    public void updateLog(String message){

    }

    public StringBuilder showLog(File file) throws FileNotFoundException {

        StringBuilder msg = new StringBuilder();

        try (FileReader reader = new FileReader(file);
            Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                msg.append(line);
            }
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("Fil " + file.getName() + " ikke fundet.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return msg;
    }

}
