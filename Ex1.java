package Ex1_6580871;

import java.util.*;

/**
 *
 * @author Thitirat Kulpornpaisarn 6580871
 */
public class Homework1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scan = new Scanner(System.in);
        int input,storeprime;
        do{
        System.out.println("Enter integer x (2-1000) = ");
        input = scan.nextInt();
        } while(input>1000||input<2);
        System.out.println(); //เว้นline
        
        if(isPrime(input)) System.out.println(input+" is prime");
            
        else{
            System.out.println(input+" is not prime");    
            storeprime=findNum(input);
            System.out.println("The immediate smaller value that is prime = "+storeprime);
        }
    }
    
    
    static boolean isPrime(int value){  //for checking whether it is prime or not
       int x;
       for(x=2;x<=value/2;x++){
           if(value%x==0) return false;
       }
       return true;
       
       /*if(count!=0) System.out.println(value+" is not prime");        
       else System.out.println(value+" is prime"); */
    }
    
    
    public static int findNum(int value){  //for finding the nearest smaller prime num
       int i;
       for(i=value-1;i>=0;i--){
           if(isPrime(i)) return i;                   
       }
    return 0;
    }
}
