package yugioh.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculadorCombateTest {

    private Monstruo crear(int atq, int def, Posicion pos) {
        return new Monstruo("X", atq, def, pos);
    }

    @Test
    public void ataqueVsAtaqueGanaAtacante() {
        ResultadoCombate r = CalculadorCombate.resolver(
                crear(2500, 0, Posicion.ATAQUE), crear(1800, 0, Posicion.ATAQUE));
        assertEquals(700, r.getDanio());
        assertTrue(r.isDefensorDestruido());
        assertFalse(r.isAtacanteDestruido());
        assertFalse(r.danioAlAtacante());
    }

    @Test
    public void ataqueVsAtaquePierdeAtacante() {
        ResultadoCombate r = CalculadorCombate.resolver(
                crear(1500, 0, Posicion.ATAQUE), crear(2000, 0, Posicion.ATAQUE));
        assertEquals(500, r.getDanio());
        assertTrue(r.isAtacanteDestruido());
        assertTrue(r.danioAlAtacante());
    }

    @Test
    public void ataqueVsAtaqueEmpate() {
        ResultadoCombate r = CalculadorCombate.resolver(
                crear(1000, 0, Posicion.ATAQUE), crear(1000, 0, Posicion.ATAQUE));
        assertEquals(0, r.getDanio());
        assertTrue(r.isAtacanteDestruido());
        assertTrue(r.isDefensorDestruido());
    }

    @Test
    public void ataqueVsDefensaDestruyeDefensorSinDanio() {
        ResultadoCombate r = CalculadorCombate.resolver(
                crear(2000, 0, Posicion.ATAQUE), crear(0, 1500, Posicion.DEFENSA));
        assertEquals(0, r.getDanio());
        assertTrue(r.isDefensorDestruido());
        assertFalse(r.isAtacanteDestruido());
    }

    @Test
    public void ataqueVsDefensaRebote() {
        ResultadoCombate r = CalculadorCombate.resolver(
                crear(1000, 0, Posicion.ATAQUE), crear(0, 1800, Posicion.DEFENSA));
        assertEquals(800, r.getDanio());
        assertTrue(r.danioAlAtacante());
        assertFalse(r.isDefensorDestruido());
    }

    @Test
    public void ataqueVsDefensaEmpate() {
        ResultadoCombate r = CalculadorCombate.resolver(
                crear(1500, 0, Posicion.ATAQUE), crear(0, 1500, Posicion.DEFENSA));
        assertEquals(0, r.getDanio());
        assertFalse(r.isAtacanteDestruido());
        assertFalse(r.isDefensorDestruido());
    }

    @Test
    public void ataqueDirecto() {
        ResultadoCombate r = CalculadorCombate.resolver(crear(1500, 0, Posicion.ATAQUE));
        assertEquals(1500, r.getDanio());
        assertFalse(r.danioAlAtacante());
    }
}