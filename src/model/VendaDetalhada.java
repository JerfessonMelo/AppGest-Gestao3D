package src.model;
    
public class VendaDetalhada {
    private int id;
    private String cliente;
    private String filamento;
    private double gramasUtilizadas;
    private int minutos;
    private double precoTotal;
    private String statusPagamento;

    public VendaDetalhada(int id, String cliente, String filamento, double gramasUtilizadas, int minutos, double precoTotal,
            String statusPagamento) {
        this.id = id;
        this.cliente = cliente;
        this.filamento = filamento;
        this.gramasUtilizadas = gramasUtilizadas;
        this.minutos = minutos;
        this.precoTotal = precoTotal;
        this.statusPagamento = statusPagamento;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    public String getFilamento() {
        return filamento;
    }
    public void setFilamento(String filamento) {
        this.filamento = filamento;
    }
    public double getgramasUtilizadas() {
        return gramasUtilizadas;
    }
    public void setgramasUtilizadas(double gramasUtilizadas) {
        this.gramasUtilizadas = gramasUtilizadas;
    }
    public int getMinutos() {
        return minutos;
    }
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
    public double getPrecoTotal() {
        return precoTotal;
    }
    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }
    public String getStatusPagamento() {
        return statusPagamento;
    }
    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

}
