/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rnnn01;

import java.io.*;


public class prueba01 {
    public static void main(String[] args) throws FileNotFoundException, IOException{   
        double ingreso[][]=new double[600][169];
        double salida[][]=new double[600][10];
        
        int line = 0;
        for(int n=0; n<10; n++){
            String dir = "C:/Users/JARED/Desktop/Tareas/10ciclo/deep/neuralnnn-002/Redes-Convolucionales/input"+n+".txt";
            FileReader f = new FileReader(dir);
            BufferedReader b = new BufferedReader(f);
            String cadena;
            while((cadena = b.readLine())!=null){
                String[] pieces = cadena.split(" ");
                for(int i=0; i<pieces.length; i++){
                    ingreso[line][i] = Integer.parseInt(pieces[i]);
                }
                salida[line]= new double[10];
                for(int i=0; i<10; i++)
                    salida[line][i] = 0;
                salida[line][n] = 1;
                line++;
            }
            b.close();
        }
        //double evaluar[][];
        int[] m = {130,90,50};
        rna01 rn = new rna01(169,m,10,null);
        rn.entrenamiento(ingreso, salida, 5000);
        //rn.prueba(evaluar);
    }
}
