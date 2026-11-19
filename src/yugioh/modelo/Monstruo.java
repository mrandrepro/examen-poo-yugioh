package yugioh.modelo;

public class Monstruo extends Carta implements Combatiente {
    private int ataque;
    private int defensa;
    private Posicion posicion;

    public Monstruo(String nombre, int ataque, int defensa, Posicion posicion) {
        super(nombre); // llama al constructor de Carta
        if (ataque < 0 || defensa < 0) {
            throw new IllegalArgumentException("ATQ y DEF no pueden ser negativos");
        }
        this.ataque = ataque;
        this.defensa = defensa;
        this.posicion = posicion;
    }

    @Override
    public int getAtaque() { return ataque; }

    @Override
    public int getDefensa() { return defensa; }

    @Override
    public Posicion getPosicion() { return posicion; }

    public void setPosicion(Posicion posicion) { this.posicion = posicion; }

    @Override
    public String descripcion() { return "Monstruo normal"; }

    @Override
    public String toString() {
        return nombre + " [ATQ " + ataque + "/DEF " + defensa + ", " + posicion + "]";
    }
}