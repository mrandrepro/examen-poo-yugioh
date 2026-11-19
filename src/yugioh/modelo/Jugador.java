package yugioh.modelo;

public class Jugador {
    private final String nombre;
    private int lp;

    public Jugador(String nombre, int lpInicial) {
        if (lpInicial <= 0) {
            throw new IllegalArgumentException("LP inicial debe ser mayor a 0");
        }
        this.nombre = nombre;
        this.lp = lpInicial;
    }

    public void recibirDanio(int danio) {
        lp = Math.max(0, lp - Math.max(0, danio)); // el LP nunca baja de 0
    }

    public boolean haPerdido() { return lp == 0; }

    public String getNombre() { return nombre; }

    public int getLp() { return lp; }
}