//Thitirat Kulpornpaisarn 6580871
package Ex4_6580871;

import java.io.File;
import java.util.Scanner;
import java.util.*;

/**
 *
 * @author Thitirat Kulpornpaisarn 6580871
 */

class Seafood implements Comparable<Seafood> {
    private String name, type;
    private int omega3, cholesterol;
    private double mercury;
    public Seafood(String t,String n,int omg,int ch,double mcr){type=t; name=n; omega3=omg; cholesterol=ch; mercury=mcr;}
    
    public int compareTo(Seafood other) {
// add your code according to conditions in 2.2-2.5    
    if(this.omega3!=other.omega3){
        if(this.omega3>other.omega3) return -1;
        else if(this.omega3<other.omega3) return 1;
    }
    else if(this.cholesterol!=other.cholesterol){
        if(this.cholesterol>other.cholesterol) return 1;
        else if(this.cholesterol<other.cholesterol) return -1;
    }
    else if(this.mercury!=other.mercury){
        if(this.mercury>other.mercury) return 1;
        else if(this.mercury<other.mercury) return -1;         
    }
    return this.name.compareTo(other.name);   
}
    
    public void show(){
        System.out.printf("%-22s %-19s %4d %12s %3d %13s %4.3f\n",name,type,omega3," ",cholesterol," ",mercury);
    }
}

class Fish extends Seafood{
    public Fish(String t,String n,int omg,int ch,double mcr){
         super(t,n,omg,ch,mcr);
    }
}

class Crustacean extends Seafood{
    public Crustacean(String t,String n,int omg,int ch,double mcr){
         super(t,n,omg,ch,mcr);
    }
}

class Mollusk extends Seafood{
    public Mollusk(String t,String n,int omg,int ch,double mcr){
         super(t,n,omg,ch,mcr);
    }
}


public class Homework4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Seafood[] seafoods= new Seafood[28];
        ArrayList<Seafood> seafoods= new ArrayList<Seafood>();
        String path        = "src/main/Java/Ex4_6580871/";
        String inFilename  = path + "seafoods.txt";
                
        try{
            File inFile      = new File(inFilename);
            Scanner fileScan = new Scanner(inFile);       
        
        String skip=fileScan.nextLine(); //for skip 1st line
            
        int i;
        for(i=0;i<28;i++){
       
            String line = fileScan.nextLine();               
            String [] cols = line.split(",");
            
            String type= cols[0].trim();
            String name= cols[1].trim();
            int omega= Integer.parseInt(cols[2].trim());
            int cholesterol= Integer.parseInt(cols[3].trim());
            double mercury= Double.parseDouble(cols[4].trim());
            
            if(type.equals("m")) seafoods.add(new Mollusk("mollusk",name,omega,cholesterol,mercury));           
            else if(type.equals("f")) seafoods.add(new Fish("fish",name,omega,cholesterol,mercury));            
            else if(type.equals("c")) seafoods.add(new Crustacean("crustacean",name,omega,cholesterol,mercury));
         
        }
                        
        fileScan.close();           
	}
        
	catch(Exception e){
            System.err.println("An error occurs. End program.");
            System.err.println(e);	  
            System.exit(-1); 
	}
        
        
        Collections.sort(seafoods);
        
        String choice;
        Scanner scan= new Scanner(System.in);
        
    do{
        System.out.println("Choose filter -> a = all, f = fish, c = crustacean, m = mollusk, others = quit");
        choice = scan.next();
        
        if(choice.equals("a")||choice.equals("A")){
            System.out.println("Seafood (3 oz)         Type            Omega-3 (mg)    Cholesterol (mg)    Mercury (ppm)");
            System.out.println("========================================================================================");
            int a;           
            for(a=0;a<seafoods.size();a++) {
               seafoods.get(a).show();
            }
            System.out.println(" ");
            System.out.println(" ");
        }
        
        else if(choice.equals("f")||choice.equals("F")){
            System.out.println("Seafood (3 oz)         Type            Omega-3 (mg)    Cholesterol (mg)    Mercury (ppm)");
            System.out.println("========================================================================================");
            int b;
            for(b=0;b<seafoods.size();b++){
                if(seafoods.get(b) instanceof Fish) seafoods.get(b).show();
            }
            System.out.println(" ");
            System.out.println(" ");
        }
        
        else if(choice.equals("m")||choice.equals("M")){
            System.out.println("Seafood (3 oz)         Type            Omega-3 (mg)    Cholesterol (mg)    Mercury (ppm)");
            System.out.println("========================================================================================");
            int c;
            for(c=0;c<seafoods.size();c++){
                if(seafoods.get(c) instanceof Mollusk) seafoods.get(c).show();
            }
            System.out.println(" ");
            System.out.println(" ");
        }
        
        else if(choice.equals("c")||choice.equals("C")){
            System.out.println("Seafood (3 oz)         Type            Omega-3 (mg)    Cholesterol (mg)    Mercury (ppm)");
            System.out.println("========================================================================================");
            int d;
            for(d=0;d<seafoods.size();d++){
                if(seafoods.get(d) instanceof Crustacean) seafoods.get(d).show();
            }
            System.out.println(" ");
            System.out.println(" ");
        }
        
        else break;
        
        
    }while(choice.equals("a")||choice.equals("A")||choice.equals("f")||choice.equals("F")||choice.equals("m")||choice.equals("M")||choice.equals("c")||choice.equals("C"));
        
    
  }
}
    

