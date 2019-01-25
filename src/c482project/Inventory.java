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
    
    public void modifyProduct(Part newPart) {
        Part oldPart = lookupPart(newPart.getPartID());
        if (oldPart == null) {return;}
        
        oldPart.setName(newPart.getName());
        oldPart.setPrice(newPart.getPrice());
        oldPart.setInStock(newPart.getInStock());
        oldPart.setMin(newPart.getMin());
        oldPart.setMax(newPart.getMax());
    }
    
    public void addPart(Part part) {
//        part.setPartID(allParts.size() + 1);
        int newPartID = 1;
        if (allParts.size() > 0) {
            newPartID = allParts.get(allParts.size() - 1).getPartID() + 1;
        }
        part.setPartID(newPartID);
        allParts.add(part);
    }
    
    public boolean deletePart(Part part) {
        return allParts.remove(part);
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
    
    public void modifyPart(Part newPart) {
        Part oldPart = lookupPart(newPart.getPartID());
        if (oldPart == null) {return;}
        
        oldPart.setName(newPart.getName());
        oldPart.setPrice(newPart.getPrice());
        oldPart.setInStock(newPart.getInStock());
        oldPart.setMin(newPart.getMin());
        oldPart.setMax(newPart.getMax());
    }
}
