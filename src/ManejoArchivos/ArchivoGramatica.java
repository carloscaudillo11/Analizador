package ManejoArchivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ArchivoGramatica{
    String[] lineas;
    File archivo;
    final String ruta = "src/Herramientas/gramatica.txt";

    public void abrirArchivo() throws FileNotFoundException, IOException{
        archivo = new File(ruta);
        if (!archivo.exists()) {
            JFileChooser promptFile = new JFileChooser();
            promptFile.setFileFilter(new FileNameExtensionFilter("Documentos", "txt"));
            int option = promptFile.showDialog(null, "Seleccionar");
            System.out.println("Nueva ruta: "+option);
            System.out.println("");
            archivo = new File(promptFile.getSelectedFile().toString());
        }
    }

    private void contarLineas() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        int i = 0;
        while (br.readLine() != null)
            i++;
        lineas = new String[i];
    }

    public String[] leerArchivo() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String cadena;
            int i = 0;
            contarLineas();
            while ((cadena = br.readLine()) != null) {
                lineas[i] = cadena;
                i++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return lineas;
    }
    
    public void cerraArchivo() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        br.close();
    }   
}
