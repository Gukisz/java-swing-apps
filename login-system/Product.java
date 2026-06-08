public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String supplier;

    public Product() {}

    public Product(String name, String description, double price, int stock, String supplier) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.supplier = supplier;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
}
