package yugioh;

import yugioh.controlador.ControladorBatalla;
import yugioh.vista.FrmBatalla;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            FrmBatalla vista = new FrmBatalla();
            new ControladorBatalla(vista);
            vista.setVisible(true);
        });
    }
}