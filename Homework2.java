package Ex2_6580871;

import java.io.*;
import java.util.*;

/**
 *
 * @author Thitirat Kulpornpaisarn 6580871
 */
public class Homework2 {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        int sizekm=Getsizekm();
        double sizem=SizeinM(sizekm);
        Do(sizekm,sizem);
    
    }
    
    public static int Getsizekm(){
        Scanner scan= new Scanner(System.in);
        int sizekm;
        System.out.println("Enter the size of your city in square km =");
        sizekm=scan.nextInt();
        return sizekm;
    }
    
    public static double SizeinM(int sizekm){
        double sizem;
        sizem=0.386102*sizekm;
        return sizem;
    }
    
    public static void Do(int sizekm,double sizem){
      
        // This is where we are
        //String localDir = System.getProperty("user.dir");
        //System.out.println("Current directory = " + localDir + "\n");
        
        String path        = "src/main/Java/Ex2_6580871/";
	String inFilename  = path + "provinces.txt";
	String outFilename = path + "output.txt";
        
	try 
        {
            File inFile      = new File(inFilename);
            Scanner fileScan = new Scanner(inFile); 
            System.out.println("Read province size from " + inFile.getPath());
            
            File outFile      = new File(outFilename);
            System.out.println("Write output "+outFile.getPath());
            //PrintWriter write = new PrintWriter(outFile);                            // overwrite (default)
            PrintWriter write = new PrintWriter( new FileWriter(outFile, false) );     // overwrite คือเขียนทับอันเก่า
            //PrintWriter write = new PrintWriter( new FileWriter(outFile, true)  );   // append คือเขียนต่ออันเก่า
            
        write.printf("%-12s %14s %18s %24s\r\n","Province","Square km","Square mile","ratio to your city");
        write.printf("=======================================================================\r\n");
        write.printf("%-12s %,13d %,18.2f %18.2f\r\n","Your city",sizekm,sizem,1.00);
        
            while (fileScan.hasNext()) 
            {							
                String province=fileScan.next();
                int sqkm=fileScan.nextInt();
                double ratio=(double)sqkm/(double)sizekm;
                               
                write.printf("%-12s %,13d %,18.2f %18.2f\r\n",province,sqkm,0.386102*sqkm,ratio);
            }
                               
            fileScan.close();
            write.close();
	}
	catch(Exception e) {
            System.err.println("An error occurs. End program.");
            System.err.println(e);	  
            System.exit(-1);
	}
    }

    
}

