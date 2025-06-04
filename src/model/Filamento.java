package src.model;
public class Filamento {

    private int id;
    private String tipo;
    private int precoKg;
    private double densidade;

    public Filamento(int id, String tipo, int precoKg, double densidade) {
        this.id = id;
        this.tipo = tipo;
        this.precoKg = precoKg;
        this.densidade = densidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPrecoKg() {
        return precoKg;
    }

    public void setPrecoKg(int precoKg) {
        this.precoKg = precoKg;
    }

    public double getDensidade() {
        return densidade;
    }

    public void setDensidade(double densidade) {
        this.densidade = densidade;
    }
 
}
