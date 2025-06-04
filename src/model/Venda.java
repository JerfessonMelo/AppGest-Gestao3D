package src.model;
public class Venda {

    private int id;
    private Cliente cliente;
    private Filamento filamento;
    private String statusPagamento;
    private double gramasUtilizadas;
    private int minutosImpressao;
    private double precoTotal;
    private double percentualLucro; 

    public Venda(Cliente cliente, Filamento filamento, String statusPagamento, double gramasUtilizadas,
            int minutosImpressao, double precoTotal) {
        this.cliente = cliente;
        this.filamento = filamento;
        this.statusPagamento = statusPagamento;
        this.gramasUtilizadas = gramasUtilizadas;
        this.minutosImpressao = minutosImpressao;
        this.precoTotal = precoTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Filamento getFilamento() {
        return filamento;
    }

    public void setFilamento(Filamento filamento) {
        this.filamento = filamento;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public double getGramasUtilizadas() {
        return gramasUtilizadas;
    }

    public void setGramasUtilizadas(int gramasUtilizadas) {
        this.gramasUtilizadas = gramasUtilizadas;
    }

    public int getMinutosImpressao() {
        return minutosImpressao;
    }

    public void setMinutosImpressao(int minutosImpressao) {
        this.minutosImpressao = minutosImpressao;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(int precoTotal) {
        this.precoTotal = precoTotal;
    }

    public void setGramasUtilizadas(double gramasUtilizadas) {
        this.gramasUtilizadas = gramasUtilizadas;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public void setPercentualLucro(double percentualLucro) {
        this.percentualLucro = percentualLucro;
    }
}
