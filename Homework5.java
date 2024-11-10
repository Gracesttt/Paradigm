//Thitirat Kulpornpaisarn 6580871
package Ex5_6580871;

import java.io.*;
import java.time.Duration;
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
    
    public Seafood(String t,String n,int omg,int ch,double mcr) throws InvalidInputException {
        type=t;
        name=n;
        omega3=omg;
        cholesterol=ch;
        mercury=mcr;
    }
    
    public String getType(){
        return this.type;
    }
   
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

class InvalidInputException extends Exception{
    InvalidInputException(String message){super(message);}
};

public class Homework5 {   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        
      ArrayList<Seafood> seafoods= new ArrayList<Seafood>();
      Scanner Scan = new Scanner(System.in);
      String inLine= "";
      String fileName= "seafoods.txt";
      boolean check=false;

        while(!check){
            try{ //file found
                Scanner fileScan= new Scanner(new File("src/main/java/Ex5_6580871/"+fileName));
                check=true;
                String skip=fileScan.nextLine();
                
                while(fileScan.hasNext()){
                  try{                        
                        inLine =fileScan.nextLine();
                        String[] cols =inLine.split(",");               
                        String type; 
                        if(cols[0].equals("f")) type="fish";
                        else if(cols[0].equals("m")) type="mollusk";
                        else if(cols[0].equals("c")) type="crustacean";                            
                        else throw new InvalidInputException("For input: \"" +cols[0]+ "\"");
                        
                        int i;
                        for(i=2;i<=4;i++){
                        if(Double.parseDouble(cols[i].trim())<0) throw new InvalidInputException("For input: \"" +cols[i].trim()+ "\"");     
                        }
                        
                        //แคสเป็นint && print haddock
                        int x= (int)Double.parseDouble(cols[2].trim());
                        
                    seafoods.add(new Seafood(type,cols[1].trim(),x,Integer.parseInt(cols[3].trim()),Double.parseDouble(cols[4].trim())));
                  } 
                    
                  catch(NumberFormatException|ArrayIndexOutOfBoundsException|InvalidInputException e){
                        System.err.println(e + "\n" + inLine + "\n");
                  }
                    
                }   
                fileScan.close();  
            }
                       
            catch(FileNotFoundException e){ //not found
                System.err.println(e);
                System.err.flush();
                Thread.sleep(50);
                System.out.println("New file name = ");
                fileName =Scan.nextLine();
            }
        }
        
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
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
                if(seafoods.get(b).getType().equals("fish")) seafoods.get(b).show();
            }
            System.out.println(" ");
            System.out.println(" ");
        }
        
        else if(choice.equals("m")||choice.equals("M")){
            System.out.println("Seafood (3 oz)         Type            Omega-3 (mg)    Cholesterol (mg)    Mercury (ppm)");
            System.out.println("========================================================================================");
            int c;
            for(c=0;c<seafoods.size();c++){
                if(seafoods.get(c).getType().equals("mollusk")) seafoods.get(c).show();
            }
            System.out.println(" ");
            System.out.println(" ");
        }
        
        else if(choice.equals("c")||choice.equals("C")){
            System.out.println("Seafood (3 oz)         Type            Omega-3 (mg)    Cholesterol (mg)    Mercury (ppm)");
            System.out.println("========================================================================================");
            int d;
            for(d=0;d<seafoods.size();d++){
                if(seafoods.get(d).getType().equals("crustacean")) seafoods.get(d).show();
            }
            System.out.println(" ");
            System.out.println(" ");
        }        
        else break;       
    }while(choice.equals("a")||choice.equals("A")||choice.equals("f")||choice.equals("F")||choice.equals("m")||choice.equals("M")||choice.equals("c")||choice.equals("C"));
 }
}
    


