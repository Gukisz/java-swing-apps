package model;

public class ServiceOrder {
    private int id;
    private int clientId;
    private String clientName; // apenas para exibicao
    private String equipment;
    private String defect;
    private String service;
    private String technician;
    private double value;
    private String status; // Aberta, Em andamento, Finalizada, Cancelada

    public ServiceOrder() {}

    public ServiceOrder(int clientId, String equipment, String defect, String service, String technician, double value, String status) {
        this.clientId = clientId;
        this.equipment = equipment;
        this.defect = defect;
        this.service = service;
        this.technician = technician;
        this.value = value;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }

    public String getDefect() { return defect; }
    public void setDefect(String defect) { this.defect = defect; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String getTechnician() { return technician; }
    public void setTechnician(String technician) { this.technician = technician; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
