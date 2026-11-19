package yugioh.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class JugadorTest {

    @Test
    public void recibirDanioReduceLp() {
        Jugador j = new Jugador("Yugi", 8000);
        j.recibirDanio(1500);
        assertEquals(6500, j.getLp());
    }

    @Test
    public void lpNoBajaDeCero() {
        Jugador j = new Jugador("Yugi", 1000);
        j.recibirDanio(5000);
        assertEquals(0, j.getLp());
        assertTrue(j.haPerdido());
    }

    @Test
    public void danioNegativoSeIgnora() {
        Jugador j = new Jugador("Yugi", 1000);
        j.recibirDanio(-300);
        assertEquals(1000, j.getLp());
    }

    @Test(expected = IllegalArgumentException.class)
    public void lpInicialInvalidoLanzaExcepcion() {
        new Jugador("Yugi", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void monstruoConAtaqueNegativoLanzaExcepcion() {
        new Monstruo("X", -100, 500, Posicion.ATAQUE);
    }
}