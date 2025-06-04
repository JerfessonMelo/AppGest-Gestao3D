package src.model;
public class Impressora {
    private int id;
    private String nome;
    private double consumoWatts;
    private double valorKwH;

    public Impressora(int id, String nome, double consumoWatts, double valorKwH) {
        this.id = id;
        this.nome = nome;
        this.consumoWatts = consumoWatts;
        this.valorKwH = valorKwH;
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

}
