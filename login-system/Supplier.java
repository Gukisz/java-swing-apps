public class Supplier {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String cnpj;

    public Supplier() {}

    public Supplier(String name, String phone, String email, String address, String cnpj) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.cnpj = cnpj;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
}
