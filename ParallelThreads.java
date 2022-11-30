package ThreadPrgs;
/*
* Sample program – Calculating factorial for big numbers using multiple parallel threads
 * */
import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ParallelThreads {
    public static void main(String[] args) throws InterruptedException{
        List<Long> inputNumbers = Arrays.asList(2312987L, 12329L, 23232L, 2343L, 34L);
        List<FactorialThread> threads = new ArrayList<>();

        for(Long i: inputNumbers){
            threads.add(new FactorialThread(i));
        }

        for(Thread t: threads){
            t.setDaemon(true);
            t.start();
        }

        for(Thread t: threads){
            t.join(2000);
        }

        for(int j=0; j<inputNumbers.size(); j++){
            FactorialThread f = threads.get(j);
            if(f.isFinished()){
                System.out.println("Factorial of "+inputNumbers.get(j)+" is "+f.getResult());
            }else{
                System.out.println("The calculation for "+inputNumbers.get(j)+" is in progress..");
            }
        }
    }
    public static class FactorialThread extends Thread{
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber){
            this.inputNumber = inputNumber;
        }

        @Override
        public void run(){
            BigInteger temp = BigInteger.ONE;
            for(long i=inputNumber; i>0; i--){
                temp = temp.multiply(new BigInteger(Long.toString(i)));
            }
            result = temp;
            isFinished = true;
        }
        public BigInteger getResult(){
            return result;
        }
        public boolean isFinished(){
            return isFinished;
        }
    }
}
