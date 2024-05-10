package trabajoMain;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class Tp2 {
	

	public static void main(String[] args) {
		try {
		
		Scanner in = new Scanner (System.in);
		String ruta = in.next().replaceAll("'", "").replaceAll(" ", "");
		File imagenTemporal = new File (ruta);
		BufferedImage imagen = ImageIO.read(imagenTemporal);
		
		int ancho = imagen.getWidth();
		int alto = imagen.getHeight();
		
		int [][] matrizRojos =  new int[alto][ancho];
		int [][] matrizVerdes = new int[alto][ancho];
		int [][] matrizAzules = new int[alto][ancho];
		int [][] matrizGrises = new int[alto][ancho];
		
		for (int i=0;i<alto;i++) {
			for (int j=0;j<ancho; j++) {
					int pixel = imagen.getRGB(j, i);
					matrizRojos[i][j]= (pixel >> 16) & 0xff;
					matrizVerdes[i][j]= (pixel >> 8) & 0xff;
					matrizAzules[i][j]= pixel & 0xff;
			}
		}
		
		BufferedImage imagenNueva = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
		
		for (int i=0;i<alto;i++) {
			for (int j =0; j<ancho; j++) {
				int promedio = ((matrizRojos[i][j] + matrizVerdes[i][j] + matrizAzules[i][j]) / 3);
				matrizGrises[i][j] = promedio;
				Color gris = new Color(promedio,promedio,promedio);
				imagenNueva.setRGB(j, i,gris.getRGB());
			}
		}
		
		File imagenGrises = new File("imagenGrises.jpg");
		ImageIO.write(imagenNueva, "jpg", imagenGrises);
		
		
		
		in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
