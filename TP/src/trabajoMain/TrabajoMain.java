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
	//valores de los kernels: https://visualize-it.github.io/image_filters/simulation.html#:~:text=Every%20image%20can%20be%20represented%20by%20a%20superposition,the%20kernel%20matrix%20to%20get%20the%20%27filtered%27%20image.
	public static final double[][] Box_blur = {
	        {0.111, 0.111, 0.111},
	        {0.111, 0.111, 0.111},
	        {0.111, 0.111, 0.111}
	    };
	public static final double[][] Gaussian_blur = {
	        {0.0625, 0.1250, 0.0625},
	        {0.1250, 0.25, 0.1250},
	        {0.0625, 0.1250, 0.0625}
	    };
	public static final double[][] Sharpen = {
	        {-1, -1,-1},
	        {-1, 9,-1},
	        {-1, -1,-1}
	    };
	public static final double[][] Emboss = {
	        {-2, -1, 0},
	        {-1, 1, 1},
	        {0, 1, 2}
	    };
	public static final double[][] Edge_detection = {
			{-1, -1,-1},
	        {-1, 8,-1},
	        {-1, -1,-1}
	    };

    public static void main(String[] args) {
        try {
        	clearScreen();
            Scanner in = new Scanner(System.in);
            //Desde la consola ingreso ruta del archivo.
            System.out.println("Ingrese ruta de la imagen: ");
            String stringImagen = in.nextLine();
            clearScreen();
            String output = (stringImagen.replaceAll("'", "")).replaceAll(" ", "");
            File imagen = new File(output);
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
            //creacion de kernel
            System.out.println("Presione ENTER para mostrar imagen RGB (cerrar imagen con tecla ENTER): ");
            stringImagen = in.nextLine();
            clearScreen();
            mostrarMatrizImagenRGB(matrizRGB);
            System.out.println("Presione ENTER para mostrar imagen en escala de grises canal ROJO (cerrar imagen con tecla ENTER): ");
            stringImagen = in.nextLine();
            clearScreen();
            mostrarMatrizImagenRojo(matrizRGB);
            System.out.println("Presione ENTER para mostrar imagen en escala de grises canal VERDE (cerrar imagen con tecla ENTER): ");
            stringImagen = in.nextLine();
            clearScreen();
            mostrarMatrizImagenVerde(matrizRGB);
            System.out.println("Presione ENTER para mostrar imagen en escala de grises canal AZUL (cerrar imagen con tecla ENTER): ");
            stringImagen = in.nextLine();
            clearScreen();
            mostrarMatrizImagenAzul(matrizRGB);
            System.out.println("Presione ENTER para mostrar imagen en escala de grises PROMEDIO (cerrar imagen con tecla ENTER): ");
            stringImagen = in.nextLine();
            clearScreen();
            int[][] matrizGrises = escalaDeGrises(matrizRGB);
            int indice;
            //eleccion de kernel
            System.out.println("Ingrese filtro a aplicar: ");
            System.out.println("0- Salir");
            System.out.println("1- Box blur ");
            System.out.println("2- Gaussian blur ");
            System.out.println("3- Sharpen ");
            System.out.println("4- Emboss ");
            System.out.println("5- Edge detection ");
            System.out.println("6- Personalizado");
            indice = in.nextInt();
            clearScreen();
            switch (indice) {
            case 1 : mostrarKernel(matrizGrises,Box_blur);break;
            case 2 : mostrarKernel(matrizGrises,Gaussian_blur);break;
            case 3 : mostrarKernel(matrizGrises,Sharpen);break;
            case 4 : mostrarKernel(matrizGrises,Emboss);break;
            case 5 : mostrarKernel(matrizGrises,Edge_detection);break;
            case 6 : mostrarKernel(matrizGrises,in);break;
            default: System.out.println("ERROR");break;
            }
            in.close();
            System.out.println("Programa finalizado.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    private static void mostrarKernel(int[][] matrizGris, double[][] kernel) {
    	
    }
    
    private static void mostrarKernel(int[][] matrizGris, Scanner in) {
    	double[][] kernel ={
    		{0, 0, 0},
    		{0, 0, 0},
    		{0, 0, 0}
    	};
    	for (int i =0;i<3;i++) {
    		for (int j =0;j<3;j++) {
    			for (int q =0;q<3;q++) {
    				System.out.print("{");
    				for (int k =0;k<3;k++) {
    					System.out.print(" "+kernel[q][k]+" ");
    				}
    				System.out.print("}");
    				System.out.println("");
    			}
    			kernel [i][j] =in.nextDouble();
    			clearScreen();
    		}
    	}
    	for (int q =0;q<3;q++) {
			System.out.print("{");
			for (int k =0;k<3;k++) {
				System.out.print(" "+kernel[q][k]+" ");
			}
			System.out.print("}");
			System.out.println("");
    	}
    	mostrarKernel(matrizGris, kernel);
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
        JFrame frame = new JFrame("Matriz GRISES:");
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
        JFrame frame = new JFrame("Matriz RGB:");
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
    
    private static void mostrarMatrizImagenRojo(int[][][] matrizRGB) {
        BufferedImage imagenNueva = crearImagenMatrizRojo(matrizRGB);
        ImageIcon icon = new ImageIcon(imagenNueva);
        JLabel label = new JLabel(icon);
        JFrame frame = new JFrame("Matriz canal ROJO:");
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

    private static BufferedImage crearImagenMatrizRojo(int[][][] matrizRGB) {
        int altura = matrizRGB.length;
        int ancho = matrizRGB[0].length;
        BufferedImage imagen = new BufferedImage(ancho, altura, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < ancho; x++) {
                int red = matrizRGB[y][x][0];
                Color color = new Color(red, red, red);
                imagen.setRGB(x, y, color.getRGB());
            }
        }

        return imagen;
    }
    
    private static void mostrarMatrizImagenVerde(int[][][] matrizRGB) {
        BufferedImage imagenNueva = crearImagenMatrizVerde(matrizRGB);
        ImageIcon icon = new ImageIcon(imagenNueva);
        JLabel label = new JLabel(icon);
        JFrame frame = new JFrame("Matriz canal VERDE:");
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

    private static BufferedImage crearImagenMatrizVerde(int[][][] matrizRGB) {
        int altura = matrizRGB.length;
        int ancho = matrizRGB[0].length;
        BufferedImage imagen = new BufferedImage(ancho, altura, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < ancho; x++) {
                int green = matrizRGB[y][x][1];
                Color color = new Color(green, green, green);
                imagen.setRGB(x, y, color.getRGB());
            }
        }

        return imagen;
    }
    
    private static void mostrarMatrizImagenAzul(int[][][] matrizRGB) {
        BufferedImage imagenNueva = crearImagenMatrizAzul(matrizRGB);
        ImageIcon icon = new ImageIcon(imagenNueva);
        JLabel label = new JLabel(icon);
        JFrame frame = new JFrame("Matriz canal AZUL:");
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

    private static BufferedImage crearImagenMatrizAzul(int[][][] matrizRGB) {
        int altura = matrizRGB.length;
        int ancho = matrizRGB[0].length;
        BufferedImage imagen = new BufferedImage(ancho, altura, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < ancho; x++) {
                int blue = matrizRGB[y][x][2];
                Color color = new Color(blue, blue, blue);
                imagen.setRGB(x, y, color.getRGB());
            }
        }

        return imagen;
    }
    
    public static void clearScreen() {
    // Limpia la consola utilizando comandos específicos para sistemas operativos
    try {
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
    } catch (IOException | InterruptedException e) {
        // Maneja cualquier excepción que pueda ocurrir al limpiar la consola
        e.printStackTrace();
    }
}


}
