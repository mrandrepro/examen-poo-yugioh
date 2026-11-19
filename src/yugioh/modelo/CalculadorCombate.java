package yugioh.modelo;

public final class CalculadorCombate {

    private CalculadorCombate() { }  

    
    public static ResultadoCombate resolver(Monstruo atk, Monstruo def) {
        String a = atk.getNombre();
        String d = def.getNombre();

        if (def.getPosicion() == Posicion.ATAQUE) {
            
            int dif = atk.getAtaque() - def.getAtaque();
            if (dif > 0) {
                return new ResultadoCombate(a, d, dif, false, true, false, "Defensor destruido");
            }
            if (dif < 0) {
                return new ResultadoCombate(a, d, -dif, true, false, true, "Atacante destruido");
            }
            return new ResultadoCombate(a, d, 0, true, true, false, "Ambos destruidos");
        }

        
        int dif = atk.getAtaque() - def.getDefensa();
        if (dif > 0) {
            return new ResultadoCombate(a, d, 0, false, true, false, "Defensor destruido, sin daño");
        }
        if (dif < 0) {
            return new ResultadoCombate(a, d, -dif, false, false, true, "Rebote: daño al atacante");
        }
        return new ResultadoCombate(a, d, 0, false, false, false, "Sin efecto");
    }

    
    public static ResultadoCombate resolver(Monstruo atk) {
        return new ResultadoCombate(atk.getNombre(), "Ataque directo",
                atk.getAtaque(), false, false, false, "Daño directo");
    }
}