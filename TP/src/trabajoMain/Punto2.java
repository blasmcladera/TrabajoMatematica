package trabajoMain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Punto2 {
	public static final int[][] Emboss = {
	        {-2, -1, 0},
	        {-1, 1, 1},
	        {0, 1, 2}
	    };
	
	public static final int[][] Sobel = {
	        {-1, 0, 1},
	        {-2, 0, 2},
	        {-1, 0, 1}
	    };
	
	public static final int[][] Sharpen = {
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
		
		int [][] matrizGrises =  new int[alto][ancho];
		
		for (int i=0;i<alto;i++) {
			for (int j=0;j<ancho; j++) {
					int pixel = imagen.getRGB(j, i);
					matrizGrises[i][j]= (pixel >> 16) & 0xff;
			}
		}
		
		int indice = in.nextInt();
		int [][] matrizFiltro = null;
		switch (indice) {
		case 1 : matrizFiltro =convolucion(matrizGrises,Emboss,alto-1,ancho-1);break;
		case 2 : matrizFiltro =convolucion(matrizGrises,Sobel,alto-1,ancho-1);break;
		case 3 : matrizFiltro =convolucion(matrizGrises,Sharpen,alto-1,ancho-1);break;
		default: System.out.println("ERROR");break;
		}
		
		BufferedImage matrizFiltrada = new BufferedImage(ancho-1,alto-1,BufferedImage.TYPE_INT_RGB);
		
		for (int i=0;i<alto-2;i++) {
			for (int j =0; j<ancho-2; j++) {
				Color gris = new Color(matrizFiltro[i][j],matrizFiltro[i][j],matrizFiltro[i][j]);
				matrizFiltrada.setRGB(j, i,gris.getRGB());
			}
		}
		
		ImageIO.write(matrizFiltrada, "jpg", imagenTemporal);
		

		in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	public static int [][] convolucion (int [][] matrizGrises,int [][] kernel, int alto, int ancho) {
		int aux;
		int [][] matrizFiltrada = new int[alto-1][ancho-1];
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
