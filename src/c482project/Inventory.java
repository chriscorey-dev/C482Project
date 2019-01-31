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
        this.products = new ArrayList<>();
        this.allParts = new ArrayList<>();
    }
    
    public ArrayList<Part> getAllParts() {
        return allParts;
    }
    
    public ArrayList<Product> getAllProducts() {
        return products;
    }
    
    public void addProduct(Product product) {
        int newProdID = 1;
        if (products.size() > 0) {
            newProdID = products.get(products.size() - 1).getProductID() + 1;
        }
        product.setProductID(newProdID);
        products.add(product);
    }
    
    // This is the required method specified in the UML diagram...
    public boolean removeProduct(int productID) {
        return products.remove(lookupProduct(productID));
    }
    
    // But this method makes more sense to use... and is closer to how the deletePart method works
    public boolean deleteProduct(Product product) {
        return products.remove(product);
    }
    
    public Product lookupProduct(int productID) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductID() == productID) {
                return products.get(i);
            }
        }
        return null;
    }
    
    // Idk what this does...
    public void updateProduct(int productID) {
        
    }
    
    public void modifyProduct(Product newProd) {
        products.set(products.indexOf(lookupProduct(newProd.getProductID())), newProd);
    }
    
    public void addPart(Part part) {
        int newPartID = 1;
        if (allParts.size() > 0) {
            newPartID = allParts.get(allParts.size() - 1).getPartID() + 1;
        }
        part.setPartID(newPartID);
        allParts.add(part);
    }
    
    public boolean deletePart(Part part) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getAssociatedParts().contains(part)) {
                product.removeAssociatedPart(part.getPartID());
            }
        }
        
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
        allParts.set(allParts.indexOf(lookupPart(newPart.getPartID())), newPart);
    }
    
    // Returns any product with specified part
    public ArrayList<Product> lookupProductsWithPart(Part part) {
        ArrayList<Product> productsWithPart = new ArrayList<>();
        
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getAssociatedParts().contains(part)) {
                productsWithPart.add(product);
            }
        }
        
        return productsWithPart;
    }
}
