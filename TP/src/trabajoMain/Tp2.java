package trabajoMain;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class Tp2 {
	
	public static final double[][] Emboss = {
	        {-2, -1, 0},
	        {-1, 1, 1},
	        {0, 1, 2}
	    };
	
	public static final double[][] Sobel = {
	        {-1, 0, 1},
	        {-2, 0, 2},
	        {-1, 0, 1}
	    };
	
	public static final double[][] Sharpen = {
			{-1, -1,-1},
	        {-1, 9,-1},
	        {-1, -1,-1}
	    };
	

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
		double [][] matrizGrises = new double[alto][ancho];
		
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
		
		double [][] matrizFiltro = convolucion(matrizGrises,Sobel,alto,ancho);
		
		BufferedImage matrizFiltrada = new BufferedImage(ancho-1,alto-1,BufferedImage.TYPE_INT_RGB);
		
		for (int i=0;i<alto-1;i++) {
			for (int j =0; j<ancho-1; j++) {
				Color gris = new Color((int)matrizFiltro[i][j],(int)matrizFiltro[i][j],(int)matrizFiltro[i][j]);
				matrizFiltrada.setRGB(j, i,gris.getRGB());
			}
		}
		
		File imagenFiltrada = new File("imagenFiltrada.jpg");
		ImageIO.write(matrizFiltrada, "jpg", imagenFiltrada);
		
		
		in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static double [][] convolucion (double [][] matrizGrises,double [][] kernel, int alto, int ancho) {
		double aux;
		double [][] matrizFiltrada = new double[alto-1][ancho-1];
		int altk,anck;
		for (int i=1;i<alto-1;i++) {
			for (int j=1;j<ancho-1; j++) {
				aux=0;
				altk=0;
				for (int k=i-1;k<i+2;k++) {
					anck=0;
					for (int h=j-1; h<j+2;h++) {
					aux= (aux + (matrizGrises[k][h]*kernel[altk][anck]));
					anck++;
					}
					altk++;
				}
				if (aux<0) aux=0;
				if (aux>255) aux=255;
				matrizFiltrada[i][j] = aux;
			}
		}
		return matrizFiltrada;
	}

}
