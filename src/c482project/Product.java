package c482project;

import java.util.ArrayList;

public class Product {
    
    private ArrayList<Part> associatedParts;
    private String name;
    private double price;
    private int productID, inStock, min, max;

    public Product(ArrayList<Part> associatedParts, int productID, String name, double price, int inStock, int min, int max) {
        this.associatedParts = associatedParts;
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    public Product(int productID) {
        this.productID = productID;
    }
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public Part lookupAssociatedParts(int partID) {
        for (int i = 0; i < associatedParts.size() - 1; i++) {
            if (associatedParts.get(i).getPartID() == partID) {
                return associatedParts.get(i);
            }
        }
        
        return null;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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
