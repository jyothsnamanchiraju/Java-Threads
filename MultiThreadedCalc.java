package ThreadPrgs;
/**
 * In this exercise we will efficiently calculate the following result = base1 ^ power1 + base2 ^ power2
 * Where a^b means: a raised to the power of b.
 * For example 10^2 = 100
 * We know that raising a number to a power is a complex computation, so we we like to execute:
 * result1 = x1 ^ y1
 * result2 = x2 ^ y2
 * In parallel.
 * and combine the result in the end : result = result1 + result2
 * This way we can speed up the entire calculation.
 *
 * Note - base1 >=0, base2>=0, power1>=0, power2>=0
 */
import java.math.BigInteger;
public class MultiThreadedCalc {

    public static void main(String[] args){
        MultiThreadedCalc m = new MultiThreadedCalc();
        m.calcResult(new BigInteger("5"), new BigInteger("3"),
                new BigInteger("5"), new BigInteger("2"));
    }

        public void calcResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2){
            BigInteger Result;

            PowerCalculatingThread p1 = new PowerCalculatingThread(base1, power1);
            PowerCalculatingThread p2 = new PowerCalculatingThread(base2, power2);

            p1.start();
            p2.start();

            try{
                p1.join(2000);
                p2.join(2000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            Result = p1.getResult().add(p2.getResult());
            System.out.println("Result = "+Result);
        }

    private static class PowerCalculatingThread extends Thread{
        BigInteger base;
        BigInteger power;
        BigInteger result = BigInteger.ONE;

        public PowerCalculatingThread(BigInteger base, BigInteger power){
            this.base = base;
            this.power = power;
        }
        @Override
        public void run(){
          //  for(BigInteger i= BigInteger.ZERO; i.compareTo(power)!=0; i.add(BigInteger.ONE)){
             for(int i=0; i< power.intValue(); i++){
                this.result = this.result.multiply(base);
            }
        }
        public BigInteger getResult(){return result; }
    }
}
