package Models;

public class Ship {

    private String shipName;
    private String captainName;
    private int fuel;
    private int integrity;
    private int spareParts;
    private int shieldLevel;
    private boolean repairKitUsed;

    public Ship(){}

    public Ship(String shipName, String captainName) {
        this.shipName = shipName;
        this.captainName = captainName;
        this.fuel = 100;
        this.integrity = 100;
        this.spareParts = 10;
        this.shieldLevel = 0;
        this.repairKitUsed = false;

    }

    public String getShipName() {
        return shipName;
    }
    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getCaptainName() {
        return captainName;
    }
    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }
    public int getFuel() {
        return fuel;
    }

    public void setIntegrity(int integrity) {
        this.integrity = integrity;
    }
    public int getIntegrity() {
        return integrity;
    }

    public void setSpareParts(int spareParts) {
        this.spareParts = spareParts;
    }
    public int getSpareParts() {
        return spareParts;
    }

    public void setShieldLevel(int shieldLevel) {
        this.shieldLevel = shieldLevel;
    }
    public int getShieldLevel() {
        return shieldLevel;
    }

    public void setRepairKitUsed(boolean repairKitUsed) {
        this.repairKitUsed = repairKitUsed;
    }
    public boolean getRepairKitUsed() {
        return repairKitUsed;
    }


}
