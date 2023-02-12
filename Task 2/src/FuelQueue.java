import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FuelQueue {

    //creating a ArrayList as object type to hold Passenger object with all the details
    private final ArrayList<Passenger> queue;
    private static double stock = 6600;
    public final static double LIMIT = 500;


    //constructor
    public FuelQueue() {
        this.queue=new ArrayList<>();
    }


    //adding a customer to a queue
    public void addCustomer(String fn,String ln,String vn,double noLiters,int qNum){
        if (queue.size()<6){
            queue.add(new Passenger(fn,ln,vn,noLiters));
            stock -=noLiters;
            System.out.println(fn + " added to queue "+ qNum);
        }else {
            System.out.println("Queues are Full");
        }
    }


    //remove customer from a specific place
    public void removeCustomer(int index){
        index=index-1;
        try {
            double prvLiters = queue.get(index).getNoLitersNeed();
            stock += prvLiters;
            queue.remove(index);
            System.out.println("Customer Successfully removed");
        }catch (Exception e){
            System.out.println("No customer in that location");
        }
    }


    //remove served customer
    public void removeServed(){
        queue.remove(0);
        System.out.println("Customer Successfully removed");
    }



    //view customer in a queue one by one
    public void viewCustomer(){
        for(Passenger i : queue){
            i.viewCustomerDetails();
            System.out.println();
        }
    }



    // Show empty queue
    public void emptyQueue(){
        if(queue.size()<=6){
            System.out.println("Available for data entry");
        }else{
            System.out.println("full");
        }
    }



    //to get the size of each queue
    public int sizeOfQueue(){
        return queue.size();
    }

    //get stock
    public static double getStock() {
        return stock;
    }

    // update fuel stock
    public static void setStock(double stock) {
        FuelQueue.stock += stock;
    }

    //income of each fuel queue
    public double income(){
        double totalIncome = 0;
        // price of a fuel liter : Rs 430.00
        double PRICE = 430.00;
        for(Passenger i : queue){
            totalIncome = totalIncome + i.getNoLitersNeed() * PRICE;
        }
        return totalIncome;
    }

    //writing data of each queue to a file (Data.txt)
    public void writingTOFile(String massage,FileWriter myWriter){
        try {
            myWriter.write(massage+"\n");
            for (Passenger i : queue) {
                myWriter.write(i.getDetails()+"\n");
            }
        }catch (IOException e){
            System.out.println("Error when writing to the file :(");
        }
    }

    //writing current fuel stock to the file (Data.txt)
    public static void writingFuelStock(FileWriter myWriter){
        try {
            myWriter.write("Remaining fuel stock : "+stock + "\n");
        }catch (IOException e){
            System.out.println();
        }
    }

    public void sortAlpha(String massage){
        System.out.println(massage);
        ArrayList<String> sorted =new ArrayList<>();
        for(Passenger i : queue){
            sorted.add(i.customerName());
        }
        Collections.sort(sorted);
        for(String e : sorted){
            System.out.println(e);
        }
    }
}

