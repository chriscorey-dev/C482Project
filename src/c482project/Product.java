package c482project;

import java.util.ArrayList;

public class Product {
    
    private ArrayList<Part> associatedParts;
    private int productId;
    private String name;
    private double price;
    private int inStock, min, max;

    public Product(ArrayList<Part> associatedParts, int productId, String name, double price, int inStock, int min, int max) {
        this.associatedParts = associatedParts;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    public Product(int productId) {
        this.productId = productId;
    }
    
    public void addAssociatedPart(Part part) {
        
    }

    public ArrayList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ArrayList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
