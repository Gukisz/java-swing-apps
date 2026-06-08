public class Client {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String cpf;

    public Client() {}

    public Client(String name, String phone, String email, String cpf) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.cpf = cpf;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}
