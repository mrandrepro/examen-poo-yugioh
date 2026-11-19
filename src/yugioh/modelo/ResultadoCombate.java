package yugioh.modelo;

public class ResultadoCombate {
    private final String atacante;
    private final String defensor;
    private final int danio;
    private final boolean atacanteDestruido;
    private final boolean defensorDestruido;
    private final boolean danioAlAtacante;  
    private final String descripcion;

    public ResultadoCombate(String atacante, String defensor, int danio,
                            boolean atacanteDestruido, boolean defensorDestruido,
                            boolean danioAlAtacante, String descripcion) {
        this.atacante = atacante;
        this.defensor = defensor;
        this.danio = danio;
        this.atacanteDestruido = atacanteDestruido;
        this.defensorDestruido = defensorDestruido;
        this.danioAlAtacante = danioAlAtacante;
        this.descripcion = descripcion;
    }

    public int getDanio() { return danio; }
    public boolean isAtacanteDestruido() { return atacanteDestruido; }
    public boolean isDefensorDestruido() { return defensorDestruido; }
    public boolean danioAlAtacante() { return danioAlAtacante; }

    @Override
    public String toString() {
        return atacante + " -> " + defensor + " | daño: " + danio + " | " + descripcion;
    }
}