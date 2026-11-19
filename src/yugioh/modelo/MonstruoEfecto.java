package yugioh.modelo;

public class MonstruoEfecto extends Monstruo {
    private String efecto;

    public MonstruoEfecto(String nombre, int ataque, int defensa, Posicion posicion, String efecto) {
        super(nombre, ataque, defensa, posicion);
        this.efecto = efecto;
    }

    @Override
    public String descripcion() { return "Monstruo de efecto: " + efecto; }
}