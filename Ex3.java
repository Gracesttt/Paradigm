package Ex3_6580871;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Thitirat Kulpornpaisarn 6580871
 */

class Racing{
    public static final int CURRENT_YEAR = 2024;
    private String event, venue;
    protected int venueOpened, venueAge;
    public Racing(String nm, String vn) { event = nm; venue = vn; }
    public String getEvent() { return event; }
    public String getVenue() { return venue; }
    public void printVenue() { /* override this in child class */ }
    public void printDetails() { /* override this in child class */ }
}

class HorseRacing extends Racing{
    private int age;
    protected double distanceFurlong;
    protected double distanceKM;
    
    public HorseRacing(String nm,String vn,int age,double distanceFur){
       super(nm,vn);
       this.age=age;
       distanceFurlong=distanceFur;
       distanceKM = distanceFur/5;
    }
   
    @Override
    public void printVenue(){ 
        System.out.printf("%-19s venue = %-21s (opened %d, %3d years ago)\n",getEvent(),getVenue(),CURRENT_YEAR-age,age);
    }
    
    @Override
    public void printDetails(){
         System.out.printf("%-19s distance =%6.2f furlongs   = %4.2f km\n",getEvent(),distanceFurlong,distanceKM);
    }
}                                      

class MotorRacing extends Racing{
    protected double laplength,laptimeMS,speed;
    protected int year;
    protected String laptime;
    
    public MotorRacing(String nm,String vn,int year,double ll,double ms,String lapt){
        super(nm,vn);
        this.year=year;
        this.laplength=ll;
        this.laptimeMS= ms;
        this.laptime=lapt;
    }
    
    @Override
    public void printVenue() { 
        System.out.printf("%-19s venue = %-21s (opened %d, %3d years ago)\n",getEvent(),getVenue(),year,CURRENT_YEAR-year);
    }
    
    @Override
    public void printDetails(){   
        System.out.printf("%-19s lap =%6.3f km     lap time =%s mins    avg speed = %.1f km/hr\n",getEvent(),laplength,laptime,3600000*laplength/laptimeMS);
        //System.out.printf("%-20s lap = %5.3d km %5s lap time = %12s mins    avg speed = %6.1f km/hr\n",getEvent(),laplength,laptime,(360000*laplength)/laptimeMS);
    }
   
}

public class Homework3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Racing [] allRaces = new Racing[13];
        
         String path        = "src/main/Java/Ex3_6580871/";
         String inFilename  = path + "racing.txt";
                 
        try{
            File inFile      = new File(inFilename);
            Scanner fileScan = new Scanner(inFile);       
            
            int i;
            for(i=0;i<13;i++){	
                String line = fileScan.nextLine();               
                String [] cols = line.split(",");
                                                  
                if(cols[0].equals("m")){                    
                    String event= cols[1].trim();
                    String venue= cols[2].trim();
                    int year = Integer.parseInt( cols[3].trim() );
                    double length= Double.parseDouble(cols[4].trim());  
                    String lapStr = cols[5];
                    String [] laps = lapStr.split(":");
                    int min= Integer.parseInt(laps[0].trim());
                    double secms= Double.parseDouble(laps[1].trim());   
                    double ms = min*60000 + secms*1000;
                    allRaces[i] = new MotorRacing(event,venue,year,length,ms,lapStr);                    
                }
                
                else{ //h
                    String event= cols[1].trim();
                    String venue= cols[2].trim();
                    int venueAge= Integer.parseInt(cols[3].trim());
                    double disfur= Double.parseDouble(cols[4].trim());
                    allRaces[i]= new HorseRacing(event,venue,venueAge,disfur);
                }                
            }
            
            int a;
            System.out.println("=== All races (reverse order) ===");
            for(a=12;a>=0;a--) allRaces[a].printVenue();
            
            int b;
            System.out.println("\n=== Only Horse races (input order) ===");
            for(b=0;b<13;b++){
                if (allRaces[b] instanceof HorseRacing){
                HorseRacing hr = (HorseRacing) allRaces[b];
                hr.printDetails();                                              //ใช้ hr print
                }
            }
                        
            int c;
            System.out.println("\n=== Only Motor races (input order) ===");
            for(c=0;c<13;c++){
                if (allRaces[c] instanceof MotorRacing){
                //MotorRacing mt = (MotorRacing) allRaces[c];
                allRaces[c].printDetails();                                     //ใช้ allRaces pai loey
                }
            }
                     
        fileScan.close();           
	}
        
	catch(Exception e){
            System.err.println("An error occurs. End program.");
            System.err.println(e);	  
            System.exit(-1); 
	}
    }
         
}
      

      
    
