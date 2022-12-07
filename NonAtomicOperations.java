package ThreadPrgs;
/*Program to demonstrate non-atomic operations*/
public class NonAtomicOperations {
    public static void main(String[] args) throws InterruptedException{
        InventoryCounter ic1 = new InventoryCounter();

        IncrementThread iT = new IncrementThread(ic1); //sharing the same object ic1 across two threads
        DecrementThread iD = new DecrementThread(ic1);

        iT.start();
        iD.start();

        iT.join();  //since two threads are accessing the same object, it causes discrepancy in the value.
        iD.join();

        System.out.println("We currently have "+ic1.getItems()+" items in the repository");
    }


    public static class DecrementThread extends Thread{
        private InventoryCounter ic;
        public DecrementThread(InventoryCounter ic){
            this.ic = ic;
        }
        @Override
        public void run(){
            for(int i=0; i<10000; i++){
                ic.decrement();
            }
        }
    }
    public static class IncrementThread extends Thread{
        private InventoryCounter ic;

        public IncrementThread(InventoryCounter ic){
            this.ic = ic;
        }

        @Override
        public void run(){
            for(int i=0; i<10000; i++){
                ic.increment();
            }
        }
    }
    private static class InventoryCounter{
        private int items =0;

        public void increment(){
            items++; //non-atomic statement
        }
        public void decrement(){
            items--; //non-atomic statement
        }
        public int getItems(){
            return items;
        }
    }
}
