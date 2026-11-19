package yugioh.modelo;

public abstract class Carta {
    protected String nombre;

    public Carta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    // Cada subclase debe escribir su propia versión
    public abstract String descripcion();
}