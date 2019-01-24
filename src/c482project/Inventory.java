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
    
    // TEMP: 
    public ArrayList<Part> getAllParts() {
        return allParts;
    }
    
    public void addProduct(Product product) {
        
    }
    
    public boolean removeProduct(int productID) {
        return false;
    }
    
    public Product lookupProduct(int productID) {
        return new Product(productID);
    }
    
    // Idk what this does...
    public void updateProduct(int productID) {
        
    }
    
    public void addPart(Part part) {
        allParts.add(part);
    }
    
    public void deletePart(Part part) {
        
    }
    
    public Part lookupPart(int partID) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getPartID() == partID) {
                return allParts.get(i);
            }
        }
        return null;
    }
    
    // Idk what this does...
    public void updatePart(int partID) {
        
    }
    
    public void changePart(Part newPart) {
        Part oldPart = lookupPart(newPart.getPartID());
        if (oldPart == null) {return;}
        
        oldPart.setName(newPart.getName());
        oldPart.setPrice(newPart.getPrice());
        oldPart.setInStock(newPart.getInStock());
        oldPart.setMin(newPart.getMin());
        oldPart.setMax(newPart.getMax());
    }
    
    public void changeProduct(Part newPart) {
        Part oldPart = lookupPart(newPart.getPartID());
        if (oldPart == null) {return;}
        
        oldPart.setName(newPart.getName());
        oldPart.setPrice(newPart.getPrice());
        oldPart.setInStock(newPart.getInStock());
        oldPart.setMin(newPart.getMin());
        oldPart.setMax(newPart.getMax());
    }
}
