import java.util.LinkedList;
import java.util.Queue;

public class WaitingQ {

    private static final Queue<Passenger> waitingQ = new LinkedList<>();

   //adding extra customer to waiting queue
    public static void addCustomerWaitingQ(String fn,String ln,String vn,double noLiters){
        if (waitingQ.size()<20) {
            waitingQ.offer(new Passenger(fn, ln, vn, noLiters));
            System.out.println("Customer added to waiting queue !");
        }else {
            System.out.println("Waiting queue reach its limit customer will not be added :(");
        }
    }

    //once a customer is removed first customer in the waiting queue will be added to the removed customer position
    public static Passenger getFirstCustomer(){
        Passenger temp=waitingQ.poll();
        FuelQueue.setStock(-temp.getNoLitersNeed());
        return temp;
    }

    //getter
    public static Queue<Passenger> getWaitingQueue() {
        return waitingQ;
    }

}