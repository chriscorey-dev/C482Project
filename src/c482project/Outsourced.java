package c482project;

public class Outsourced extends Part {
    
    private String companyName;
    
    public Outsourced(String companyName, int partID, String name, double price, int inStock, int min, int max) {
        this.companyName = companyName;

        setPartID(partID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getMachineID() {
        return -1;
    }

    public void setMachineID(int machineID) {
    }
}
