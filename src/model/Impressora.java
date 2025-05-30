package src.model;
public class Impressora {
    private int id;
    private String nome;
    private double consumoWatts;
    private double valorKwH;
    private double percentualLucro;

    public Impressora(int id, String nome, double consumoWatts, double valorKwH, double percentualLucro) {
        this.id = id;
        this.nome = nome;
        this.consumoWatts = consumoWatts;
        this.valorKwH = valorKwH;
        this.percentualLucro = percentualLucro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getConsumoWatts() {
        return consumoWatts;
    }

    public void setConsumoWatts(double consumoWatts) {
        this.consumoWatts = consumoWatts;
    }

    public double getValorKwH() {
        return valorKwH;
    }

    public void setValorKwH(double valorKwH) {
        this.valorKwH = valorKwH;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public void setPercentualLucro(double percentualLucro) {
        this.percentualLucro = percentualLucro;
    }
}
