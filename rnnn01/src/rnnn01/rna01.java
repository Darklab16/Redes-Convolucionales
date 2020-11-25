/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rnnn01;
import java.util.*;
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
   
   public rna01(int ci_,int[] co_,int cs_){
        ci=ci_;
        cm=co_.length;     // cantidad de medios inicializada
        //int co=co_;
        co=new int[cm];
        System.arraycopy(co_, 0, co, 0, cm);
        cs=cs_;
        
        c = new int[cm+2];
        
        int n_neuron_m = 0;
        for(int i=0; i<cm; i++){
            n_neuron_m = n_neuron_m + co[i];
        }
        
        y = new double[n_neuron_m+cs];          //salidas de las neuronas
        s = new double[n_neuron_m+cs];          //entradas de las neuronas
        g = new double[n_neuron_m+cs];          //error retropropagado por la neurona
        
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
        for(int i=0;i<w.length;i++){
            w[i]=getRandom(); 
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
//        double test7[]={1,1,0}; usored(test7);       
   }
   
   public double fun(double d){
        return 1/(1+Math.exp(-d));
   }
   
   public void printxingreso(){
           //visualizar x ingreso     
        for(int i=0;i<xin.length;i++)
            for(int j=0;j<xin[i].length;j++)
                System.out.println("xingreso["+i+","+j+"]="+xin[i][j]);
        System.out.println("                ");
   }
   
   public void printxysalida(){
           //visalizar x de salida
        for(int i=0;i<xout.length;i++)
            for(int j=0;j<xout[i].length;j++)
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
   
    public void entrenamiento(double[][] in,double[][] sal,int veces){
        xin=in;
        xout=sal;
        for(int v=0;v<veces;v++)
         for(int i=0;i<xin.length;i++){
            entreno(i);
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
        for(int i=0;i<c[cm+1];i++){
            g[i+c_sum]=(xout[ci][i]-y[i+c_sum])*y[i+c_sum]*(1-y[i+c_sum]);
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
        
        for(int i=0;i<pruebas.length;i++){
            for(int j=0;j<pruebas[i].length;j++){
                prubs[j]=pruebas[i][j];
                //System.out.println("["+i+","+j+"]"+pruebas[i][j]);
            }
            usored(prubs);
        }
   
   
   }
           
   public void usored(double[] datatest){
        System.out.println("-----------****Inicio Test****----------");
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
        for(int i=c_sum;i<(c_sum+c[cm+1]);i++){
            System.out.print("["+y[i]+"] ");
        }
        System.out.println();
        
        //System.out.println("-----------****Fin Test****----------");
       
   }
}
