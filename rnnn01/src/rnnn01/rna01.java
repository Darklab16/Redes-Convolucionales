/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rnnn01;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Administrador
 */
public class rna01 {
   static final Random rand = new Random();
   int ci;
   int cm;
   int[] co;    // Aqui seria un array de nros de neuronas por cada capa
   int cs;
   int x;
   String weights;
    
   double xin[][];//={{0,1,0},{0,1,1},{1,0,0},{1,0,1}};
   //double xin[][]={{0,1,0},{0,1,1},{1,0,0},{1,0,1}};
   double xout[][];//={{1},{0},{1},{0}};
   //double xout[][]={{1},{0},{1},{0}};
   
   //////////////////double prueba[][]={{0,1,0},{0,1,1},{1,0,0},{1,0,1},{1,1,1},{0,0,0},{1,1,0}};
   
//           double test1[]={0,1,0}; usored(test1);
//        double test2[]={0,1,1}; usored(test2);
//        double test3[]={1,0,0}; usored(test3);
//        double test4[]={1,0,1}; usored(test4);       
//        double test5[]={1,1,1}; usored(test5);       
//        double test6[]={0,0,0}; usored(test6);       
//        double test7[]={1,1,0}; usored(test7);   
   
//   double y[]={0,0,0};
   double y[];
   
//   double w[]={2,-2,0,1,3,-1,3,-2};
//   double s[]={0,0,0};
   double s[];
   double g[];
//   double g[]={0,0,0};
   double w[];
   
//   int c[] = {3,2,1};//capas de datos
//   int c[]=new int[3];//capas de datos
   int[] c;//capas de datos
   
   public rna01(int ci_,int[] co_,int cs_,String pesos){
        ci=ci_;
        cm=co_.length;     // cantidad de medios inicializada
        //int co=co_;
        co=new int[cm];
        System.arraycopy(co_, 0, co, 0, cm);
        cs=cs_;
        weights=pesos;
        
        c = new int[cm+2];
        
        int n_neuron_m = 0;
        for(int i=0; i<cm; i++){
            n_neuron_m = n_neuron_m + co[i];
        }
        
        x = n_neuron_m+cs;
        
        y = new double[x];          //salidas de las neuronas
        s = new double[x];          //entradas de las neuronas
        g = new double[x];          //error retropropagado por la neurona
        
        int prod=0;
        for(int i=1; i<co.length; i++){
            prod = prod + co[i-1]*co[i];
        }
        
        w = new double[ci*co[0]+prod+co[cm-1]*cs];    //pesos;
        
        c[0]=ci;
        //c[1]=co;
        System.arraycopy(co, 0, c, 1, cm);
        //c[2]=cs;
        c[cm+1]=cs;
        
        for(int i=0;i<y.length;i++){
            y[i]=0;s[i]=0;g[i]=0;
        }
        if(weights==null){
            for(int i=0;i<w.length;i++){
                w[i]=getRandom(); 
            }
        }
        else{
            FileReader f = null;
            try {
                f = new FileReader(weights);
                BufferedReader b = new BufferedReader(f);
                String cadena;
                while((cadena = b.readLine())!=null){
                    String[] pieces = cadena.split(" ");
                    for(int i=0; i<pieces.length; i++){
                        w[i] = Double.parseDouble(pieces[i]);
                    }
                }
                b.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(rna01.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(rna01.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    f.close();
                } catch (IOException ex) {
                    Logger.getLogger(rna01.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
//        for(int i=0;i<500;i++) entrenamiento(0);
//        for(int i=0;i<500;i++) entrenamiento(1);
//        for(int i=0;i<500;i++) entrenamiento(2);
//        for(int i=0;i<500;i++) entrenamiento(3);
//        
//        
//        double test[]={0,1,0}; usored(test);
//        double test2[]={0,1,1}; usored(test2);
//        double test3[]={1,0,0}; usored(test3);
//        double test4[]={1,0,1}; usored(test4);
        
//        for(int i=0;i<500;i++){
//            entrenamiento(0);
//            entrenamiento(1);
//            entrenamiento(2);
//            entrenamiento(3);
//        }
//        double test1[]={0,1,0}; usored(test1);
//        double test2[]={0,1,1}; usored(test2);
//        double test3[]={1,0,0}; usored(test3);
//        double test4[]={1,0,1}; usored(test4);
//        double test5[]={1,1,1}; usored(test5);
//        double test6[]={0,0,0}; usored(test6);
//        double test7[]={1,1,0}; usored(test7);s
   }
   
   public double fun(double d){
        return 1/(1+Math.exp(-d));
   }
   
   public void printxingreso(){
           //visualizar x ingreso     
        for(int i=0;i<xin.length;i++)
            for(int j=0;j<xin[0].length;j++)
                System.out.println("xingreso["+i+","+j+"]="+xin[i][j]);
        System.out.println("                ");
   }
   
   public void printxysalida(){
           //visalizar x de salida
           System.out.println(xout.length+" "+xout[0].length);
        for(int i=0;i<xout.length;i++)
            for(int j=0;j<xout[0].length;j++)
                System.out.println("xsalida["+i+","+j+"]="+xout[i][j]);
   }
   public void printy(){
        for(int i=0;i<y.length;i++)
                System.out.println("y["+i+"]="+y[i]);   
   } 
   public void printw(){
        for(int i=0;i<w.length;i++)
                System.out.println("w["+i+"]="+w[i]);   
   }
   public void prints(){
        for(int i=0;i<s.length;i++)
                System.out.println("s["+i+"]="+s[i]);   
   } 
   public void printg(){
        for(int i=0;i<g.length;i++)
                System.out.println("g["+i+"]="+g[i]);   
   }
    double getRandom() {
            return (rand.nextDouble() * 2 - 1); // [-1;1[
    }
   
    public void entrenamiento(double[][] in,double[][] sal,int veces,int t,int ciclos){
        xin=in;
        xout=sal;
        if(t==1){
            for(int v=0;v<veces;v++)
                for(int i=0;i<xin.length;i++){
                    entreno(i);
                }
        }
        else{
            ThreadedNetwork[] threads = new ThreadedNetwork[t];
            List<Future<Results>> difs = new ArrayList<Future<Results>>();
            int k = xin.length/t;
            for(int n=0; n<t; n++){
                double xin_part[][] = new double[k][xin[0].length];
                double xout_part[][] = new double[k][xout[0].length];
                for(int i=k*n; i<k*(n+1); i++){
                    System.arraycopy(xin[i], 0, xin_part[i-k*n], 0, xin[0].length);
                    System.arraycopy(xout[i], 0, xout_part[i-k*n], 0, xout[0].length);
                }
                threads[n] = new ThreadedNetwork();
                threads[n].init(c, cm, xin_part, xout_part, x, veces/ciclos);
            }
            double w_old[];
            for(int cy=0; cy<ciclos; cy++){
                ExecutorService service = Executors.newFixedThreadPool(t);
                System.out.println(cy);
                for(int n=0; n<t; n++){             //entrena en paralelo
                    threads[n].setWeights(w);
                    Future<Results> future = service.submit(threads[n]);
                    difs.add(future);
                }
                Results best = null;
                service.shutdown();
                try {
                    service.awaitTermination(1, TimeUnit.HOURS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(rna01.class.getName()).log(Level.SEVERE, null, ex);
                }
                for(int n=0; n<t; n++){      //actualiza pesos
                    Results boxed = null;
                    try {
                        //System.out.println(difs.get(n).get());
                        boxed = difs.get(n).get();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(rna01.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(rna01.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(n==0){
                        best = boxed;
                        for(int i=0; i<w.length; i++){
                            w[i] = best.dif.get(i);
                        }
                        double error = getError();
                        boxed.error = error;
                    }
                    else{
                        for(int i=0; i<w.length; i++){
                            w[i] = boxed.dif.get(i);
                        }
                        double error = getError();
                        boxed.error = error;
                        if(best.error>boxed.error){
                            best = boxed;
                        }
                    }
                }
                for(int i=0; i<w.length; i++){
                    w[i] = best.dif.get(i);
                }
            }
        }
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("C:/Users/JARED/Desktop/Tareas/10ciclo/deep/neuralnnn-002/Redes-Convolucionales/pesos.txt");
            pw = new PrintWriter(fichero);

            String cadena = "";
            for (int i = 0; i < w.length; i++){
                cadena = cadena + w[i] + " ";
            }
            
            pw.println(cadena);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
  
   public void entreno(int cii){
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
   
   public void prueba(double[][] pruebas){
        double prubs[] = new double[c[0]];
        
        System.out.println(pruebas.length+" "+pruebas[0].length);
        for(int i=0;i<pruebas.length;i++){
            for(int j=0;j<pruebas[0].length;j++){
                prubs[j]=pruebas[i][j];
                //System.out.println("["+i+","+j+"]"+pruebas[i][j]);
            }
            usored(prubs, i);
        }
   
   
   }
           
   public void usored(double[] datatest,int time){
        System.out.println("-----------****Inicio Test "+time+"****----------");
                int ii;
        double pls; 
        //int ci;        

        //entrenamiento
        //////////////////////////////////   
        //////******** Ida**********//////
        //+++++++capa1        
        ///ci=0;//entrenamiento primero   /////HOPE
        //ci=cii;
        ii = 0;//capa0*capa1
        pls=0;
        for(int i=0;i<c[1];i++){
            for(int j=0;j<c[0];j++){
                //pls=pls+w[ii]*xin[ci][j];
                pls=pls+w[ii]*datatest[j];
                ii++;
            }
            s[i]=pls;  //i = i+ capa0
            y[i]=fun(s[i]); //i = i+ capa0
            pls=0;
        }
        
        int c_sum=0;
        for(int n=0;n<cm;n++){
            pls=0;
            //ii=ii+c[n]*c[n+1];
            for(int i=0;i<c[n+2];i++){
                for(int j=0;j<c[n+1];j++){
                    pls=pls+w[ii]*y[j+c_sum];
                    ii++;
                }
                s[i+c_sum+c[n+1]]=pls;
                y[i+c_sum+c[n+1]]=fun(s[i+c_sum+c[n+1]]);
                pls=0;
            }
            c_sum=c_sum+c[n+1];
        }
        
        //++++++capa2
    //    pls=0;
    //    ii = c[0]*c[1];//capa1*capa2
    //    for(int i=0;i<c[2];i++){
    //        for(int j=0;j<c[1];j++){
    //            pls=pls+w[ii]*y[j];
    //            ii++;
    //        }
    //        s[i+c[1]]=pls;  //i = i + capa1
    //        y[i+c[1]]=fun(s[i+c[1]]); //i = i + capa1
    //        pls=0;
    //    }
        //printy();
        //printy();
        System.out.print("prueba");
        for(int i=0;i<datatest.length;i++){
            System.out.print("["+datatest[i]+"] ");
        }
        System.out.println();
        System.out.print("salida");
        //for(int i=(co-1);i<(co+cs);i++){
        //for(int i=2;i<3;i++){
        double max = y[c_sum];
        int index = 0;
        for(int i=1; i<c[cm+1]; i++){
            if(y[c_sum+i]>max){
                max = y[c_sum+i];
                index = i;
            }
        }
        System.out.print(" valor: "+index+" precisión: "+(max*100)+"%");
        
        for(int i=c_sum;i<(c_sum+c[cm+1]);i++){
            System.out.print("["+y[i]+"] ");
        }
        System.out.println();
        
        //System.out.println("-----------****Fin Test****----------");
       
   }
   
   public double getError(){
        double prubs[] = new double[c[0]];
        double error = 0;
        
        for(int i=0;i<xin.length;i++){
            for(int j=0;j<xin[0].length;j++){
                prubs[j]=xin[i][j];
                //System.out.println("["+i+","+j+"]"+pruebas[i][j]);
            }
            error = error + partialError(prubs, i);
        }
        
        return error;
   }
   
   public double partialError(double[] datatest,int time){
        int ii;
        double pls; 
        //int ci;        

        //entrenamiento
        //////////////////////////////////   
        //////******** Ida**********//////
        //+++++++capa1        
        ///ci=0;//entrenamiento primero   /////HOPE
        //ci=cii;
        ii = 0;//capa0*capa1
        pls=0;
        for(int i=0;i<c[1];i++){
            for(int j=0;j<c[0];j++){
                //pls=pls+w[ii]*xin[ci][j];
                pls=pls+w[ii]*datatest[j];
                ii++;
            }
            s[i]=pls;  //i = i+ capa0
            y[i]=fun(s[i]); //i = i+ capa0
            pls=0;
        }
        
        int c_sum=0;
        for(int n=0;n<cm;n++){
            pls=0;
            //ii=ii+c[n]*c[n+1];
            for(int i=0;i<c[n+2];i++){
                for(int j=0;j<c[n+1];j++){
                    pls=pls+w[ii]*y[j+c_sum];
                    ii++;
                }
                s[i+c_sum+c[n+1]]=pls;
                y[i+c_sum+c[n+1]]=fun(s[i+c_sum+c[n+1]]);
                pls=0;
            }
            c_sum=c_sum+c[n+1];
        }
        
        double error = 0;
        for(int i=c_sum;i<(c_sum+c[cm+1]);i++){
            error = error + (y[i]-xout[ci][i-c_sum])*(y[i]-xout[ci][i-c_sum]);
        }
        error = error / c[cm+1];
        
        return error;
   }
}
