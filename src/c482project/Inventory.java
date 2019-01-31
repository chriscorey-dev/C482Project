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
    
    // TEMP: probably
    public ArrayList<Part> getAllParts() {
        return allParts;
    }
    
    // TEMP: probably
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
        // Validation
        return products.remove(lookupProduct(productID));
    }
    
    // But this method makes more sense to use... and is closer to how the deletePart method works
    public boolean deleteProduct(Product product) {
        // Validation
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
        Product oldProd = lookupProduct(newProd.getProductID());
        if (oldProd == null) {return;}
        
        oldProd.setAssociatedParts(newProd.getAssociatedParts());
        oldProd.setName(newProd.getName());
        oldProd.setPrice(newProd.getPrice());
        oldProd.setInStock(newProd.getInStock());
        oldProd.setMin(newProd.getMin());
        oldProd.setMax(newProd.getMax());
    }
    
    public void addPart(Part part) {
        // TODO: If the last item was deleted, the IDs don't retain thier iteration and continue from the ID nearest to the end of the list. Small bug, I probably won't fix this.
//        part.setPartID(allParts.size() + 1);
        int newPartID = 1;
        if (allParts.size() > 0) {
            newPartID = allParts.get(allParts.size() - 1).getPartID() + 1;
        }
        part.setPartID(newPartID);
        allParts.add(part);
    }
    
    public boolean deletePart(Part part) {
        // TODO: Validation
        // TODO: Delete product's associatedPart
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
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).lookupAssociatedPart(newPart.getPartID()) == newPart) {
                System.out.println("part found");
            }
        }
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
