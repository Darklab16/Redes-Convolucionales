package leerjpg;
import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Leerjpg  extends Component {

  public static void main(String[] foo) throws IOException {
    new Leerjpg();
  }

  public void printPixelARGB(int pixel) {
    int alpha = (pixel >> 24) & 0xff;
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = (pixel) & 0xff;
    System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
  }

  private void marchThroughImage(BufferedImage image) {
    int w = image.getWidth();
    int h = image.getHeight();
    System.out.println("width, height: " + w + ", " + h);

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        System.out.println("x,y: " + j + ", " + i);
        int pixel = image.getRGB(j, i);
        printPixelARGB(pixel);
        System.out.println("");
      }
    }
  }
  
  public int[][] Convolucion01(BufferedImage image){
            Color colorAux;
            int color;
            int width = image.getWidth(null);
            int height = image.getHeight(null);
           // System.out.println("Width : "+width);
           // System.out.println("Height : "+height);
            BufferedImage image2 = new BufferedImage(width-2, height-2,BufferedImage.TYPE_INT_RGB);
            //sobel
            //int kernel[][] = {{1,0,-1},{2,0,-2},{1,0,-1}};
            
            //Laplace Operator
            //int kernel[][] = {{0,-1,0},{-1,4,-1},{0,-1,0}};
            
            //The Laplacian of Gaussian
            //int kernel[][] = {{0,1,0},{1,-4,1},{0,1,0}};
                    
            //Edge detection 
            //int kernel[][] = {{0,1,0},{0,0,0},{0,-1,0}};
            int M[][] = new int[26][26];
            int kernel[][] = {{0,-1,01},{-1,5,-1},{0,-1,0}};  
            for(int y = 0; y < height-2; y++){
                for(int x = 0; x < width-2; x++){
                    int total = 0;
                    for(int i = y,a=0 ; i < y+3; a++,i++){
                        for(int j = x,b=0; j < x+3; b++,j++){
                            colorAux=new Color(image.getRGB(j, i));
                            total = total + colorAux.getBlue()*kernel[a][b];
                            if(total<0) total=0;
                            if(total>250) total = 250;                            
                        }
                    }                     
                    if(total > 50 ){
                        System.out.print("1 ");
                        M[y][x] = 1;
                    }
                    else {
                        System.out.print("0 ");
                        M[y][x] = 0;
                        
                    }    
                    color=(total << 16) | (total << 8) | total;               
                    image2.setRGB(x, y,color);
                }  
                System.out.println();
            }     
        int width2 = image2.getWidth(null);
        int height2 = image2.getHeight(null);
        System.out.println("Width2 : "+width2);
        System.out.println("Height2 : "+height2);   
        
        try{
            File f = new File("img010000000.jpg"); 
            ImageIO.write(image2, "jpg", f);
            System.out.println("Writing complete.");
            return M;
        }catch(IOException e){
            System.out.println("Error: "+e);
        } 
        return M;
  }  
  public void Convolucion(BufferedImage image){
            Color colorAux;
            int color;
            int width = image.getWidth(null);
            int height = image.getHeight(null);
           // System.out.println("Width : "+width);
           // System.out.println("Height : "+height);
            BufferedImage image2 = new BufferedImage(width-2, height-2,BufferedImage.TYPE_INT_RGB);
            //sobel
            //int kernel[][] = {{1,0,-1},{2,0,-2},{1,0,-1}};
            
            //Laplace Operator
            //int kernel[][] = {{0,-1,0},{-1,4,-1},{0,-1,0}};
            
            //The Laplacian of Gaussian
            int kernel[][] = {{0,1,0},{1,-4,1},{0,1,0}};
                    
            //Edge detection 
           // int kernel[][] = {{0,-1,0},{-1,5,-1},{0,-1,0}}; 

            for(int y = 0; y < height-2; y++){
                for(int x = 0; x < width-2; x++){
                    int total = 0;
                    for(int i = y,a=0 ; i < y+3; a++,i++){
                        for(int j = x,b=0; j < x+3; b++,j++){
                            colorAux=new Color(image.getRGB(j, i));
                            total = total + colorAux.getBlue()*kernel[a][b];
                            if(total<0) total=0;
                            if(total>250) total = 250;
                                         
                        }
                    }      
                if(total > 30 ){
                    System.out.print("0 ");
                }
                else {
                    System.out.print("1 ");                          
                }        
                color=(total << 16) | (total << 8) | total;
               
                image2.setRGB(x, y,color);
                }  
                System.out.println();
            }     
      //  int width2 = image2.getWidth(null);
      //  int height2 = image2.getHeight(null);
      //  System.out.println("Width2 : "+width2);
      //  System.out.println("Height2 : "+height2);   
        try{
            File f = new File("img_aleaa.jpg"); 
            ImageIO.write(image2, "jpg", f);
            System.out.println("Writing complete.");
        }catch(IOException e){
            System.out.println("Error: "+e);
        }          
  }
  
  public int[][] Pooling(int matriz[][]) throws IOException{
 
            String ruta = "./input.txt";
            File file = new File(ruta);
            if (!file.exists()) {
                file.createNewFile();
            }
                    
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            int M[][] = new int[13][13];                    
            for(int y = 0; y < 13; y=y+1){
                for(int x = 0; x < 13; x=x+1){
                    int max1 = Math.max(matriz[y][x],matriz[y][x+1]) ;
                    int max2 = Math.max(matriz[y+1][x],max1);
                    int max3 = Math.max(matriz[y+1][x+1],max2);
                    M[y][x]= max3;                   
                     bw.write(max3 +" ");
                }  
               // bw.write("\n");
            }     
            bw.write("\n");
            bw.close();
            fw.close();
    
        return M;
  }
public void creartxt(String n){
    
    try {
      String folder = "./trainingSample/trainingSample/"+n+"/";
            
        File folderFile = new File(folder);
        boolean resultado;
        int cont = 0 ;
        File[] files = folderFile.listFiles();
            for (File file : files) {
                boolean isFolder = file.isDirectory();
                if(isFolder) continue;
                System.out.println((isFolder ? "FOLDER: " : "  FILE: ") + file.getName());
                
                int M[][] = new int [26][26];
                String ruta_imagen = "./trainingSample/trainingSample/"+n+"/"+file.getName() ;
                System.out.println(ruta_imagen);
                BufferedImage image = ImageIO.read(this.getClass().getResource(ruta_imagen));
                M = Convolucion01(image);
                for (int j = 0; j < 26; j++) {
                    for (int i = 0; i < 26; i++) {
                        System.out.print(M[j][i]+" ");     
                    }
                     System.out.println();
                }

                System.out.println();
                System.out.println();
                int P[][] = new int[13][13];
                P = Pooling(M);

              /*  for (int j = 0; j < 13; j++) {
                    for (int i = 0; i < 13; i++) {
                        System.out.print(P[j][i]+" ");     
                    }
                     System.out.println();
                }*/
                cont++;
                
            }
        System.out.println(cont);
      
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    
}
public Leerjpg() throws IOException {
      // get the BufferedImage, using the ImageIO class
          BufferedImage a1 = ImageIO.read(this.getClass().getResource("img_14.jpg"));
          Convolucion(a1);
          creartxt("0");
          
          /*  int M[][] = new int [26][26];
            BufferedImage image3 = ImageIO.read(this.getClass().getResource("img_14.jpg"));
            M = Convolucion01(image3);
            for (int j = 0; j < 26; j++) {
                for (int i = 0; i < 26; i++) {
                    System.out.print(M[j][i]+" ");     
                }
                 System.out.println();
            }
            
            System.out.println();
            System.out.println();
            int P[][] = new int[13][13];
            P = Pooling(M);

            for (int j = 0; j < 13; j++) {
                for (int i = 0; i < 13; i++) {
                    System.out.print(P[j][i]+" ");     
                }
                 System.out.println();
            }
        */
      
    
    
  }

}