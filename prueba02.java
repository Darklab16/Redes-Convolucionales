/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rnnn01;


public class prueba02 {
    public static void main(String[] args) {   
        double ingreso[][]={
                            {1,1,1,0,0,0},
                            {0,0,0,1,1,0},
                            {1,0,0,0,0,1},
                            {0,0,0,1,1,1}
                            };
        double salida[][]={{0},{1},{0},{1}};
        double evaluar[][]={{1,1,1,0,0,0},
                            {0,0,0,1,1,0},
                            {1,0,0,0,0,1},
                            {0,0,0,1,1,1},
                            {1,1,1,0,0,1},
                            {0,0,0,0,1,1},
                            {1,1,0,0,0,1},
                            {0,0,0,1,0,1}
                            };
        
        rna01 rn = new rna01(6,6,1);
        rn.entrenamiento(ingreso, salida,1000);
        rn.prueba(evaluar);
    }
}
