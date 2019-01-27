package c482project;

public class Inhouse extends Part {
    
    private int machineID;

    public Inhouse(int machineID, int partID, String name, double price, int inStock, int min, int max) {
        this.machineID = machineID;

        setPartID(partID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    public String getCompanyName() {
        return null;
    }

    public void setCompanyName(String companyName) {
    }
}
