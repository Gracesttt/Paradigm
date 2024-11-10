//Thitirat Kulpornpaisarn 6580871
package Ex6_6580871;

import java.io.PrintWriter;
import java.util.*;
import java.util.Random;

/**
 *
 * @author Thitirat Kulpornpaisarn 6580871
 */

class PrimeThread extends Thread {
    private PrintWriter out; // each thread writes to separate file
    private ArrayList<Integer> numbers;
    private int totalPrime, target;
    public PrimeThread(String n, int t) { super(n); target = t; } 
    
    public void run(){
        String OutfileName = "src/main/java/Ex6_6580871/"+getName()+".output.txt";
        try{
            out =new PrintWriter(OutfileName);
            out.printf("%s, target = %d\n\n",getName(),target);
            
            //keep round num
            int count=0;
            
            for(;totalPrime<target;){
                count++;
                out.printf("%s%4d  >>  ", "Round",count);

                numbers= new ArrayList<>();
                for(int i=1;i<=5;i++) numbers.add(new Random().nextInt(2,101));
                Collections.sort(numbers);

                for(int n: numbers) {
                    
                    int x,checkPrime=0;
                    for(x=2;x<=n/2;x++){
                    if(n%x==0) checkPrime++;
                    }
                                       
                    if(checkPrime==0){
                        totalPrime= totalPrime+ n;
                        out.printf("%+6d",n);
                    }
                    else out.printf("%6d",n);
                    
                }
                numbers.clear();
                out.printf("%22s%5d\n","total prime = ",totalPrime);
            } 
            out.close();
            System.out.printf("%-10s%-9s%-3s%d%s%s\n", getName(),"finishes","in",count," ","rounds");    
        }
        
        catch(Exception e){
            System.err.println("An error occurs.");
            System.err.println(e); System.exit(-1); 
	}
    }
}

public class Homework6 {
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Enter #threads =");
        Scanner scan = new Scanner(System.in);
        int num,target;
        num=scan.nextInt();
        System.out.println("Enter target =");
        target=scan.nextInt();
        
        ArrayList<PrimeThread> t=new ArrayList<>();    
        for(int a=1;a<=num;a++){
            String name;
            name= "Thread_"+Integer.toString(a);
            t.add(new PrimeThread(name,target));
        } 
        for(PrimeThread T:t) T.start();
     
    }    
}