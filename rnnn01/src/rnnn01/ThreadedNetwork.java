/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnnn01;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 *
 * @author JARED
 */
public class ThreadedNetwork implements Callable{
    double w[];
    //double w_old[];
    ArrayList<Double> dif;
    Results res;
    
    int times;
    int c[];
    int cm;
    double xin[][];
    double xout[][];
    double g[];
    double y[];
    double s[];
    
    public void init(int[] c2, int cm2, double xin2[][], double xout2[][], int x, int times2){
        c = c2;
        cm = cm2;
        xin = xin2;
        xout = xout2;
        times = times2;
        g = new double[x];
        y = new double[x];
        s = new double[x];
    }
    
    public void setWeights(double w2[]){
        w = new double[w2.length];
        //w_old = new double[w2.length];
        dif = new ArrayList<Double>();
        System.arraycopy(w2, 0, w, 0, w.length);
        //System.arraycopy(w2, 0, w_old, 0, w.length);
    }
    
    public double fun(double d){
        return 1/(1+Math.exp(-d));
    }
    
    public void train(int cii){
        int ii;
        double pls; 
        int ci;

        //entrenamiento
        //////////////////////////////////   
        //////******** Ida**********//////
        //+++++++capa1        
        ///ci=0;//entrenamiento primero   /////HOPE
        ci=cii;
        ii = 0;//capa0*capa1
        pls=0;
        for(int i=0;i<c[1];i++){
            for(int j=0;j<c[0];j++){
                pls=pls+w[ii]*xin[ci][j];
                ii++;
            }
            s[i]=pls;  //i = i+ capa0
            y[i]=fun(s[i]); //i = i+ capa0
            pls=0;
        }
        int c_sum=0;
        
        for(int n=1; n<cm; n++){
            //ii=ii+c[n-1]*c[n];
            pls=0;
            for(int i=0;i<c[n+1];i++){
                for(int j=0;j<c[n];j++){
                    pls=pls+w[ii]*y[j+c_sum];
                    ii++;
                }
                s[i+c_sum+c[n]]=pls;
                y[i+c_sum+c[n]]=fun(s[i+c_sum+c[n]]);
                pls=0;
            }
            c_sum=c_sum+c[n];
        }
        
        //++++++capa2
        pls=0;
        //ii = c[0]*c[1];//capa1*capa2
        //ii = c[cm-1]*c[cm]; //esto ya debería cumplirse
        for(int i=0;i<c[cm+1];i++){
            for(int j=0;j<c[cm];j++){
                pls=pls+w[ii]*y[j+c_sum];
                ii++;
            }
            s[i+c_sum+c[cm]]=pls;  //i = i + capa1
            y[i+c_sum+c[cm]]=fun(s[i+c_sum+c[cm]]); //i = i + capa1
            pls=0;
        }
    
        c_sum=c_sum+c[cm];
        //printy();
        //prints();
        //printw();        
        //printxingreso();
        //printxysalida();
        //System.out.println("----------------------********------------------------"); 
       
        //////----------Fin Ida--------/////
        //////******** Vuelta**********/////
        //++++capa_final g
        for(int k=0; k<c[cm+1]; k=k+1){
            g[k+c_sum]=(xout[ci][k]-y[k+c_sum])*y[k+c_sum]*(1-y[k+c_sum]);
        }
        
        int prod=0;
        for(int i=0;i<cm;i++){
            prod=prod+c[i]*c[i+1];
        }
        //++++capas_intermedias
        for(int n=cm;n>0;n--){
            pls=0;
            for(int i=0;i<c[n];i++){
                for(int j=0;j<c[n+1];j++){
                    pls=pls+w[prod+j*c[n]+i]*g[c_sum+j];      // c[1]=>c_sum
                }
                g[i+c_sum-c[n]]=y[i+c_sum-c[n]]*(1-y[i+c_sum-c[n]])*pls;
                pls=0;
            }
            prod=prod-c[n-1]*c[n];
            c_sum=c_sum-c[n];
        }
        
        //++++capa1 g
    //    pls=0;
    //    for(int i=0;i<c[1];i++){
    //        for(int j=0;j<c[2];j++){
    //            pls=pls+w[c[0]*c[1]+j*c[1]+i]*g[c[1]+j];
    //        }
    //        g[i]=y[i]*(1-y[i])*pls;
    //        pls=0;
    //    }
        
        //++++capa2 w
    //    ii = c[0]*c[1];//capa1*capa2
    //    for(int i=0;i<c[2];i++){
    //        for(int j=0;j<c[1];j++){
    //            w[ii]=w[ii]+g[i+c[1]]*y[j];
    //            ii++;
    //        }
    //    }
        
        prod=0;
        c_sum=0;
        for(int i=0;i<cm;i++){
            prod=prod+c[i]*c[i+1];
            c_sum=c_sum+c[i+1];
        }
        for(int n=cm;n>0;n--){
            ii=prod;
            for(int i=0;i<c[n+1];i++){
                for(int j=0;j<c[n];j++){
                    w[ii]=w[ii]+g[i+c_sum]*y[c_sum-c[n]+j];
                    ii++;
                }
            }
            c_sum=c_sum-c[n];
            prod=prod-c[n-1]*c[n];
        }
        //++++capa1 w
        ii = 0;//capa0*capa1
        for(int i=0;i<c[1];i++){
            for(int j=0;j<c[0];j++){
                w[ii]=w[ii]+g[i]*xin[ci][j];
                ii++;
            }
        }
       //////----------Fin Vuelta--------/////
        //printg();
        //printy();
        //prints();
        //printw();        
        //printxingreso();
        //printxysalida();     
        //System.out.println("----------------------****Fin****------------------------"); 
    }
    
    @Override
    public Results call(){
        for(int v=0; v<times; v++){
            for(int i=0; i<xin.length; i++){
                train(i);
            }
        }
        
        for(int i=0; i<w.length; i++){
            dif.add(w[i]);
        }
        
        res = new Results(dif);
        
        return res;
    }
}
