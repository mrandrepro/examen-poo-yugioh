package yugioh;

import yugioh.modelo.*;

public class Main {
    public static void main(String[] args) {
        Monstruo dragon = new Monstruo("Dragón Blanco", 3000, 2500, Posicion.ATAQUE);
        Monstruo mago = new Monstruo("Mago Oscuro", 2500, 2100, Posicion.ATAQUE);
        Monstruo guardian = new Monstruo("Guardián", 1000, 1800, Posicion.DEFENSA);

        System.out.println(CalculadorCombate.resolver(dragon, mago));
        System.out.println(CalculadorCombate.resolver(mago, guardian));
        System.out.println(CalculadorCombate.resolver(dragon));
    }
}