// Thitirat Kulpornpaisarn 6580871
package Ex7_6580871;    

import java.util.*;
import java.util.concurrent.*;
import java.util.Random;

////////////////////////////////////////////////////////////////////////////////
class BankThread extends Thread
{
    private Account             sharedAccount;    // threads from the same bank work on the same account
    private Exchanger<Account>  exchanger;         
    private CyclicBarrier       barrier;
    private int                 transaction = 1;

    public BankThread(String n, Account sa)             { super(n); sharedAccount = sa; }
    public void setBarrier(CyclicBarrier ba)            { barrier   = ba; }
    public void setExchanger(Exchanger<Account> ex)     { exchanger = ex; }
    
    public void run() {
        // (1) Only 1 thread (from any bank) print start deposit to signal deposit tasks
        //     - All threads must wait to do next step together
            int first = -1;
            try { 
                first = barrier.await();
            }  
            catch(InterruptedException|BrokenBarrierException e) {
                e.printStackTrace(); 
            }
            if (first==0) System.out.printf("%s  >>  start deposit\n",Thread.currentThread().getName());
        
        // (2) Each thread does 3 transactions of deposit by calling Account's deposit
           for(int i=1;i<=3;i++){
            sharedAccount.deposit(transaction);
            transaction++;
           }
           
        // (3) Each bank representative whose Exchanger != null exchanges shardAccount
        //     - Other threads who don't exchange objects must wait until this is done
            try { 
                first = barrier.await();
            }  
            catch(InterruptedException|BrokenBarrierException e){
                e.printStackTrace(); 
            }
            if(exchanger!=null){
            try {
             System.out.printf("%s  >>  exchange account\n", Thread.currentThread().getName());
                sharedAccount = exchanger.exchange(sharedAccount);
            } 
            catch (Exception e) {
                System.err.println(e);
            }
            }
        
        // (4) Only 1 thread (from any bank) print start withdraw to signal withdrawal tasks
        //     - All threads must wait to do the next step together
        boolean printed =false;
        try{
            int t=barrier.await();
            if (t==0 && !printed) {
            System.out.printf("%s  >>  start withdraw\n", Thread.currentThread().getName());
            printed = true;
            }
        } 
        catch(Exception e) {
        System.err.println(e);
        }
        
        // (5) Each thread does 3 transactions of withdrawal by calling Account's withdraw
        for(int i=1;i<=3;i++){
            this.sharedAccount.withdraw(transaction);
            transaction++;    
        }  
    }
}

////////////////////////////////////////////////////////////////////////////////
class Account {
    private String  name;
    private int     balance;
    
    public Account(String id, int b)   { name = id; balance = b; }
    public String getName()            { return name; }
    public int    getBalance()         { return balance; }
    
    public synchronized void deposit(int Trans) 
    {
        // Random money 1-100 to deposit; update balance
        int deposit;
        deposit = new Random().nextInt(1,101);
        this.balance= this.balance+deposit;
        
        // Report thread activity (see example output)
        System.out.printf("%s  >>  transaction %d   %s %+4d  balance = %3d\n",Thread.currentThread().getName(),Trans,this.getName(),deposit,this.getBalance());
    }
    
    public synchronized void withdraw(int Trans)
    {
        // If balance > 0, random money 1-100 but not exceeding balance to withdraw; update balance
        if(this.getBalance()>0){
            int randomMoney,exceed;
            exceed= Math.min(100,this.getBalance())+1;
            randomMoney= new Random().nextInt(1,exceed);
            this.balance=this.balance-randomMoney;
            System.out.printf("%s  >>  transaction %d   %s %4s  balance = %3d\n",Thread.currentThread().getName(),Trans,this.getName(),"-" + randomMoney,this.getBalance());//-----------------------------------------------------------------
        }
        
        // If balance = 0, report that this account is closed
        // Report thread activity (see example output)
        else if(this.getBalance()==0){
            System.out.printf("%s  >>  transaction %d   %s  closed\n",Thread.currentThread().getName(),Trans,this.getName());//-----------------------------------------------------------------
        }
       
    }
}

////////////////////////////////////////////////////////////////////////////////
public class Ex7 {
    public static void main(String[] args) {
        Ex7 mainApp = new Ex7();
        mainApp.runSimulation();
    }

    public void runSimulation()
    {    
        // (1) Suppose there are 2 banks (A and B). Each bank has 1 account
        Account [] accounts = { new Account("account A", 0), 
                                new Account(".".repeat(35) + "account B", 0) };   

        Scanner keyboardScan = new Scanner(System.in);
        System.out.printf("%s  >>  Enter #threads per bank = \n", Thread.currentThread().getName());  
        int num = keyboardScan.nextInt();

        
        // (2) Synchronization objects that will be shared by all threads from both banks
        Exchanger<Account> exchanger = new Exchanger<>();
        CyclicBarrier barrier        = new CyclicBarrier(num*2);   

        
        // (3) Add code to 
        ArrayList<BankThread> allThreads = new ArrayList<>();
        
        //     - Create threads for bank A (using account A) and bank B (using account B)
        for(int i=0;i<num;i++){
            String nA,nB;
            nA="A_"+i;
            nB="B_"+i;
            allThreads.add(new BankThread(nA, accounts[0]));
            allThreads.add(new BankThread(nB, accounts[1]));
        }
        
        //     - Pass synchronization objects; Exchanger may be passed to 1 thread per bank
        for(int j=0;j<allThreads.size();j++){
            allThreads.get(j).setBarrier(barrier);
            if (j < 2) allThreads.get(j).setExchanger(exchanger);
            else allThreads.get(j).setExchanger(null);
        }  
        
        //     - Start all Bankthreads
        for(int a=0;a<allThreads.size();a++) allThreads.get(a).start();
        for(BankThread t: allThreads) {
            try{
                t.join();
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
          
        // (4) After all BankThreads complete their work, print final balance all accounts
        System.out.println("main  >>");
        for(int b=0;b<2;b++){ 
            System.out.printf("%s  >>  final balance  %s = %3d\n",Thread.currentThread().getName(), accounts[b].getName(), accounts[b].getBalance());
        }    
        
    }
}
