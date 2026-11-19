package yugioh;

import java.io.IOException;
import yugioh.modelo.*;
import yugioh.persistencia.HistorialDAO;

public class Main {
    public static void main(String[] args) throws IOException {
        Monstruo dragon = new Monstruo("Dragón Blanco", 3000, 2500, Posicion.ATAQUE);
        Monstruo mago = new Monstruo("Mago Oscuro", 2500, 2100, Posicion.ATAQUE);

        HistorialDAO dao = new HistorialDAO();
        ResultadoCombate r = CalculadorCombate.resolver(dragon, mago);
        dao.guardar(r.toString());

        for (String linea : dao.leer()) {
            System.out.println(linea);
        }
    }
}