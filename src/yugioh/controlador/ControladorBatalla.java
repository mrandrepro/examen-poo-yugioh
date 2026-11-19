package yugioh.controlador;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import yugioh.modelo.CalculadorCombate;
import yugioh.modelo.Jugador;
import yugioh.modelo.Monstruo;
import yugioh.modelo.Posicion;
import yugioh.modelo.ResultadoCombate;
import yugioh.persistencia.HistorialDAO;
import yugioh.vista.FrmBatalla;

public class ControladorBatalla {

    private final FrmBatalla vista;
    private final HistorialDAO dao = new HistorialDAO();
    private Jugador j1;
    private Jugador j2;
    private final ArrayList<Monstruo> monstruosJ1 = new ArrayList<>();
    private final ArrayList<Monstruo> monstruosJ2 = new ArrayList<>();
    private final ArrayList<ResultadoCombate> resultados = new ArrayList<>();

    public ControladorBatalla(FrmBatalla vista) {
        this.vista = vista;
        vista.getBtnCrearJugadores().addActionListener(e -> crearJugadores());
        vista.getBtnCrearMonstruo().addActionListener(e -> crearMonstruo());
        vista.getBtnAtacar().addActionListener(e -> atacar());
        vista.getBtnAtaqueDirecto().addActionListener(e -> ataqueDirecto());
        vista.getCmbTurno().addActionListener(e -> recargarCombos());
        vista.getCmbAtacante().setModel(new DefaultComboBoxModel<>());
        vista.getCmbDefensor().setModel(new DefaultComboBoxModel<>());
    }

    
    private void crearJugadores() {
        try {
            int lp = Integer.parseInt(vista.getTxtLpInicial().getText().trim());
            String n1 = vista.getTxtNombreJ1().getText().trim();
            String n2 = vista.getTxtNombreJ2().getText().trim();
            if (n1.isEmpty() || n2.isEmpty()) {
                vista.mostrarMensaje("Escribe el nombre de los dos jugadores.");
                return;
            }
            j1 = new Jugador(n1, lp);
            j2 = new Jugador(n2, lp);
            monstruosJ1.clear();
            monstruosJ2.clear();
            recargarCombos();
            actualizarLp();
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("El LP inicial debe ser un número entero.");
        } catch (IllegalArgumentException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

   
    private void crearMonstruo() {
        if (!hayJugadores()) return;
        try {
            String nombre = vista.getTxtNombreMonstruo().getText().trim();
            if (nombre.isEmpty()) {
                vista.mostrarMensaje("El monstruo necesita un nombre.");
                return;
            }
            int atq = Integer.parseInt(vista.getTxtAtq().getText().trim());
            int def = Integer.parseInt(vista.getTxtDef().getText().trim());
            Posicion pos = Posicion.valueOf((String) vista.getCmbPosicion().getSelectedItem());
            Monstruo m = new Monstruo(nombre, atq, def, pos);

            if (vista.getCmbDueno().getSelectedIndex() == 0) {
                monstruosJ1.add(m);
            } else {
                monstruosJ2.add(m);
            }
            vista.getTxtNombreMonstruo().setText("");
            vista.getTxtAtq().setText("");
            vista.getTxtDef().setText("");
            recargarCombos();
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("ATQ y DEF deben ser números enteros.");
        } catch (IllegalArgumentException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }

 
    private void atacar() {
        if (!hayJugadores()) return;
        boolean turnoJ1 = vista.getCmbTurno().getSelectedIndex() == 0;
        ArrayList<Monstruo> propios = turnoJ1 ? monstruosJ1 : monstruosJ2;
        ArrayList<Monstruo> rivales = turnoJ1 ? monstruosJ2 : monstruosJ1;
        int ia = vista.getCmbAtacante().getSelectedIndex();
        int id = vista.getCmbDefensor().getSelectedIndex();
        if (ia < 0 || id < 0) {
            vista.mostrarMensaje("Selecciona un monstruo atacante y un defensor.");
            return;
        }
        Monstruo atk = propios.get(ia);
        Monstruo def = rivales.get(id);
        if (atk.getPosicion() != Posicion.ATAQUE) {
            vista.mostrarMensaje("El atacante debe estar en posición de ataque.");
            return;
        }
        ResultadoCombate r = CalculadorCombate.resolver(atk, def);
        Jugador dueAtk = turnoJ1 ? j1 : j2;
        Jugador dueDef = turnoJ1 ? j2 : j1;
        aplicar(r, dueAtk, dueDef);

        if (r.isAtacanteDestruido()) propios.remove(atk);
        if (r.isDefensorDestruido()) rivales.remove(def);
        recargarCombos();
        verificarGanador();
    }

    private void ataqueDirecto() {
        if (!hayJugadores()) return;
        boolean turnoJ1 = vista.getCmbTurno().getSelectedIndex() == 0;
        ArrayList<Monstruo> propios = turnoJ1 ? monstruosJ1 : monstruosJ2;
        ArrayList<Monstruo> rivales = turnoJ1 ? monstruosJ2 : monstruosJ1;
        int ia = vista.getCmbAtacante().getSelectedIndex();
        if (ia < 0) {
            vista.mostrarMensaje("Selecciona un monstruo atacante.");
            return;
        }
        if (!rivales.isEmpty()) {
            vista.mostrarMensaje("No puedes atacar directo: el rival tiene monstruos.");
            return;
        }
        Monstruo atk = propios.get(ia);
        if (atk.getPosicion() != Posicion.ATAQUE) {
            vista.mostrarMensaje("El atacante debe estar en posición de ataque.");
            return;
        }
        ResultadoCombate r = CalculadorCombate.resolver(atk);
        aplicar(r, turnoJ1 ? j1 : j2, turnoJ1 ? j2 : j1);
        verificarGanador();
    }

    private void aplicar(ResultadoCombate r, Jugador dueAtacante, Jugador dueDefensor) {
        Jugador afectado = r.danioAlAtacante() ? dueAtacante : dueDefensor;
        afectado.recibirDanio(r.getDanio());
        resultados.add(r);
        try {
            dao.guardar(r.toString());
        } catch (IOException ex) {
            vista.mostrarMensaje("No se pudo guardar el historial.");
        }
        vista.getTxtAreaHistorial().append(r + "\n");
        actualizarLp();
    }

  
    private boolean hayJugadores() {
        if (j1 == null || j2 == null) {
            vista.mostrarMensaje("Primero crea los jugadores.");
            return false;
        }
        return true;
    }

    private void recargarCombos() {
        boolean turnoJ1 = vista.getCmbTurno().getSelectedIndex() == 0;
        ArrayList<Monstruo> propios = turnoJ1 ? monstruosJ1 : monstruosJ2;
        ArrayList<Monstruo> rivales = turnoJ1 ? monstruosJ2 : monstruosJ1;
        vista.getCmbAtacante().setModel(modelo(propios));
        vista.getCmbDefensor().setModel(modelo(rivales));
    }

    private DefaultComboBoxModel<String> modelo(ArrayList<Monstruo> lista) {
        DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
        for (Monstruo mo : lista) m.addElement(mo.toString());
        return m;
    }

    private void actualizarLp() {
        vista.getLblLpJ1().setText(j1.getNombre() + ": " + j1.getLp() + " LP");
        vista.getLblLpJ2().setText(j2.getNombre() + ": " + j2.getLp() + " LP");
    }

    private void verificarGanador() {
        if (j1.haPerdido()) vista.mostrarMensaje("¡Gana " + j2.getNombre() + "!");
        else if (j2.haPerdido()) vista.mostrarMensaje("¡Gana " + j1.getNombre() + "!");
    }
        // ===== Reportes =====

    // Reporte 1: monstruos vivos de ambos jugadores, ordenados por ATQ (mayor a menor)
    public String reporteMonstruosPorAtaque() {
        ArrayList<Monstruo> todos = new ArrayList<>();
        todos.addAll(monstruosJ1);
        todos.addAll(monstruosJ2);
        todos.sort((a, b) -> Integer.compare(b.getAtaque(), a.getAtaque()));
        StringBuilder sb = new StringBuilder("=== REPORTE 1: Monstruos por ATQ ===\n");
        for (Monstruo m : todos) {
            sb.append(m).append("\n");
        }
        if (todos.isEmpty()) sb.append("(sin monstruos)\n");
        return sb.toString();
    }

    // Reporte 2: combates donde hubo daño a los LP o se destruyó algún monstruo
    public String reporteCombatesRelevantes() {
        StringBuilder sb = new StringBuilder("=== REPORTE 2: Combates con daño o destrucción ===\n");
        int total = 0;
        for (ResultadoCombate r : resultados) {
            if (r.getDanio() > 0 || r.isAtacanteDestruido() || r.isDefensorDestruido()) {
                sb.append(r).append("\n");
                total++;
            }
        }
        sb.append("Total: ").append(total).append(" de ").append(resultados.size()).append(" combates\n");
        return sb.toString();
    }
}