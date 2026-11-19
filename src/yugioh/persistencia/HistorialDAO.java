package yugioh.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistorialDAO {

    public static final String ARCHIVO = "historial_combate.txt";

  
    public void guardar(String linea) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(linea);
            bw.newLine();
        }
    }

    
    public List<String> leer() throws IOException {
        List<String> lineas = new ArrayList<>();
        File f = new File(ARCHIVO);
        if (!f.exists()) {
            return lineas; // si aún no existe, devuelve lista vacía
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        }
        return lineas;
    }
}
