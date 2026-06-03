package juego;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Clase con métodos utilitarios de imágenes.
 */
public class Imagenes {

    /**
     * Método para cargar imágenes que se encuentran en la carpeta src/juego (al mismo nivel que las clases).
     * Lo creamos debido a que los utilitarios provistos no nos funcionaban.
     * @param nombreArchivo el nombre del archivo de la imagen
     * @return un objeto Image con la imagen provista
     * @throws NullPointerException si el archivo no existe
     */
    public static Image cargarImagen(String nombreArchivo){
        var url = (Imagenes.class).getResource(nombreArchivo);
        Objects.requireNonNull(url, "el %s archivo no existe".formatted(nombreArchivo));
        return new ImageIcon(url).getImage();
    }

    /**
     * Escala una imagen al ancho y alto provistos.
     * @param imagen la imagen a escalar
     * @param ancho el nuevo ancho de la imagen
     * @param alto el nuevo alto de la imagen
     * @return la imagen escalada
     */
    public static Image escalar(Image imagen, double ancho, double alto){
        return imagen.getScaledInstance((int) ancho, (int) alto, Image.SCALE_DEFAULT);
    }
}
