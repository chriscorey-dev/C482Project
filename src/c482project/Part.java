package c482project;

public class Part {
    private String name;
    private double price;
    private int partID, inStock, min, max;

    public Part(int partID, String name, double price, int inStock, int min, int max) {
        this.partID = partID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    public Part(String name, double price, int inStock, int min, int max) {
//        this.setName(name);
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    // this might need to be deleted
    public Part(int partID) {
        this.partID = partID;
        // Return part based off ID?
    }

    public Part(int partID, String name) {
        this.partID = partID;
        this.name = name;
    }

    public Part(String name) {
        this.name = name;
    }

    public Part() {
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
