package yugioh;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import yugioh.controlador.ControladorBatalla;
import yugioh.vista.FrmBatalla;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            FrmBatalla vista = new FrmBatalla();
            ControladorBatalla controlador = new ControladorBatalla(vista);

            vista.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.out.println(controlador.reporteMonstruosPorAtaque());
                    System.out.println(controlador.reporteCombatesRelevantes());
                }
            });
            vista.setVisible(true);
        });
    }
}