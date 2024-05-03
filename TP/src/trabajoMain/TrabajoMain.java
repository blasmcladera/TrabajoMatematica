package trabajoMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TrabajoMain {

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            //Desde la consola ingreso ruta del archivo.
            System.out.println("Ingrese ruta de la imagen: ");
            String stringImagen = in.nextLine();
            File imagen = new File(stringImagen);
            BufferedImage image = ImageIO.read(imagen);

            // Obtener la resolución del monitor del sistema
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = (int) screenSize.getWidth();
            int screenHeight = (int) screenSize.getHeight();

            // Escalar la imagen al tamaño del monitor
            BufferedImage scaledImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
            scaledImage.createGraphics().drawImage(image.getScaledInstance(screenWidth, screenHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);

            int[][][] matrizRGB = new int[screenHeight][screenWidth][3];

            for (int i = 0; i < screenHeight; i++) {
                for (int j = 0; j < screenWidth; j++) {
                    int pixel = scaledImage.getRGB(j, i);
                    matrizRGB[i][j][0] = (pixel >> 16) & 0xff;
                    matrizRGB[i][j][1] = (pixel >> 8) & 0xff;
                    matrizRGB[i][j][2] = pixel & 0xff;
                }
            }
            System.out.println("Presione ENTER para mostrar imagen RGB (cerrar imagen con tecla ENTER): ");
            stringImagen = in.nextLine();
            mostrarMatrizImagenRGB(matrizRGB);
            System.out.println("Presione ENTER para mostrar imagen en escala de grises (cerrar imagen con tecla ENTER): ");
            stringImagen = in.nextLine();
            int[][] matrizGrises = escalaDeGrises(matrizRGB);
            System.out.println("Programa finalizado.");
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int[][] escalaDeGrises(int[][][] matrizRGB) {
        int altura = matrizRGB.length;
        int ancho = matrizRGB[0].length;
        int[][] matrizGrises = new int[altura][ancho];

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < ancho; j++) {
                int red = matrizRGB[i][j][0];
                int green = matrizRGB[i][j][1];
                int blue = matrizRGB[i][j][2];
                int gris = (red + green + blue) / 3;
                matrizGrises[i][j] = gris;
            }
        }

        mostrarMatrizImagenGris(matrizGrises);
        return matrizGrises;
    }

    private static void mostrarMatrizImagenGris(int[][] matrizGris) {
        BufferedImage imagenNueva = crearImagenMatrizGris(matrizGris);
        ImageIcon icon = new ImageIcon(imagenNueva);
        JLabel label = new JLabel(icon);
        JFrame frame = new JFrame("MatrizGris:");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(label);

        // Establecer el tamaño de la ventana como el tamaño de la imagen
        frame.pack();

        frame.setVisible(true);

        final JFrame finalFrame = frame;

        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    finalFrame.dispose();
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });

    }

    private static BufferedImage crearImagenMatrizGris(int[][] matrizGris) {
        int altura = matrizGris.length;
        int ancho = matrizGris[0].length;
        BufferedImage imagen = new BufferedImage(ancho, altura, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < ancho; x++) {
                int red = matrizGris[y][x];
                int green = matrizGris[y][x];
                int blue = matrizGris[y][x];
                Color color = new Color(red, green, blue);
                imagen.setRGB(x, y, color.getRGB());
            }
        }

        return imagen;
    }

    private static void mostrarMatrizImagenRGB(int[][][] matrizRGB) {
        BufferedImage imagenNueva = crearImagenMatrizRGB(matrizRGB);
        ImageIcon icon = new ImageIcon(imagenNueva);
        JLabel label = new JLabel(icon);
        JFrame frame = new JFrame("MatrizRGB:");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(label);

        // Establecer el tamaño de la ventana como el tamaño de la imagen
        frame.pack();

        frame.setVisible(true);

        final JFrame finalFrame = frame;

        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    finalFrame.dispose();
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });

    }

    private static BufferedImage crearImagenMatrizRGB(int[][][] matrizRGB) {
        int altura = matrizRGB.length;
        int ancho = matrizRGB[0].length;
        BufferedImage imagen = new BufferedImage(ancho, altura, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < ancho; x++) {
                int red = matrizRGB[y][x][0];
                int green = matrizRGB[y][x][1];
                int blue = matrizRGB[y][x][2];
                Color color = new Color(red, green, blue);
                imagen.setRGB(x, y, color.getRGB());
            }
        }

        return imagen;
    }

}
