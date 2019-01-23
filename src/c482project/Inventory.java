package c482project;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Product> products;
    private ArrayList<Part> allParts;
    
    Inventory(ArrayList<Product> products, ArrayList<Part> allParts) {
        this.products = products;
        this.allParts = allParts;
    }
    
    Inventory() {
        this.products = new ArrayList<Product>();
        this.allParts = new ArrayList<Part>();
    }
    
    // Temp
    public ArrayList<Part> getAllParts() {
        return allParts;
    }
    
    public void addProduct(Product product) {
        
    }
    
    public boolean removeProduct(int productId) {
        return false;
    }
    
    public Product lookupProduct(int productId) {
        return new Product(productId);
    }
    
    public void updateProduct(int productId) {
        
    }
    
    public void addPart(Part part) {
        allParts.add(part);
    }
    
    public void deletePart(Part part) {
        
    }
    
    public Part lookupPart(int partId) {
       return new Part(partId);
    }
    
    public void updatePart(int partId) {
        
    }
}
