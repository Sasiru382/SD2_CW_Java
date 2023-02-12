import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//Assumption :- Waiting queue can hold 20 customers
public class FuelQueueManagementSys {

    public static void main(String[] args) {

        System.out.println(".......Please Select a operation from the below menu.......");
        System.out.println("""
                                
                100 or VFQ: View all Fuel Queues.
                101 or VEQ: View all Empty Queues.
                102 or ACQ: Add customer to a Queue.
                103 or RCQ: Remove a customer from a Queue. (From a specific location)
                104 or PCQ: Remove a served customer.
                105 or VCS: View Customers Sorted in alphabetical order.
                106 or SPD: Store Program Data into file.
                107 or LPD: Load Program Data from file.
                108 or STK: View Remaining Fuel Stock.
                109 or AFS: Add Fuel Stock.
                110 or IFQ: Income of each fuel queue.
                999 or EXT: Exit the Program.
                                               
                """);

        //array is created to hold 5 FuelQueue objects
        FuelQueue[] refQueue = new FuelQueue[5];
        for(int p=0;p<5;p++) {
            refQueue[p] = new FuelQueue();
        }

        Scanner input = new Scanner(System.in);
        loop1:
        do {
            //get the user input for operation
            System.out.print("Select operation : ");
            String choice = input.nextLine();
            switch (choice){
                case "100", "VFQ", "vfq" -> {
                    viewAllQueue(refQueue[0],1);
                    viewAllQueue(refQueue[1],2);
                    viewAllQueue(refQueue[2],3);
                    viewAllQueue(refQueue[3],4);
                    viewAllQueue(refQueue[4],5);
                }
                case "101", "VEQ", "veq" -> {
                    System.out.print("Fuel queue 1 : ");
                    refQueue[0].emptyQueue();
                    System.out.print("Fuel queue 2 : ");
                    refQueue[1].emptyQueue();
                    System.out.print("Fuel queue 3 : ");
                    refQueue[2].emptyQueue();
                    System.out.print("Fuel queue 4 : ");
                    refQueue[3].emptyQueue();
                    System.out.print("Fuel queue 5 : ");
                    refQueue[4].emptyQueue();
                }
                case "102", "ACQ", "acq" -> {

                    //selecting the queue with the minimum length.
                    ArrayList<Integer> size =new ArrayList<>();
                    size.add(refQueue[0].sizeOfQueue());
                    size.add(refQueue[1].sizeOfQueue());
                    size.add(refQueue[2].sizeOfQueue());
                    size.add(refQueue[3].sizeOfQueue());
                    size.add(refQueue[4].sizeOfQueue());
                    int min = Collections.min(size);

                    //getting user inputs
                    System.out.print("Enter customer first name : ");
                    String fn = input.nextLine();
                    fn = String.valueOf(fn.charAt(0)).toUpperCase()+fn.substring(1);
                    System.out.print("Enter customer last name : ");
                    String ln = input.nextLine();
                    ln = String.valueOf(ln.charAt(0)).toUpperCase()+ln.substring(1);
                    System.out.print("Enter vehicle number : ");
                    String vn = input.nextLine();
                    double noLiters;
                    System.out.print("Enter no of liters need : ");
                    try {
                        noLiters = input.nextDouble();
                        input.nextLine();
                        if(noLiters<=0){
                            System.out.println("Invalid");
                            continue;
                        }
                    }catch (Exception e){
                        System.out.println("Invalid input for liters");
                        input.nextLine();
                        continue;
                    }
                    // adding customer object to each of the queue
                    if (refQueue[0].sizeOfQueue() == min){
                        refQueue[0].addCustomer(fn,ln,vn,noLiters,1);
                    } else if (refQueue[1].sizeOfQueue() == min) {
                        refQueue[1].addCustomer(fn,ln,vn,noLiters,2);
                    } else if (refQueue[2].sizeOfQueue() == min) {
                        refQueue[2].addCustomer(fn,ln,vn,noLiters,3);
                    } else if (refQueue[3].sizeOfQueue() == min) {
                        refQueue[3].addCustomer(fn,ln,vn,noLiters,4);
                    } else {
                        refQueue[4].addCustomer(fn, ln, vn, noLiters,5);
                    }
                }
                case "103", "RCQ", "rcq" -> {
                    int queueNum = inputValidation("Enter which queue you want to remove the customer from : ");
                    if (queueNum == 1){
                        refQueue[0].removeCustomer(indexValidation(queueNum));
                    } else if (queueNum == 2) {
                        refQueue[1].removeCustomer(indexValidation(queueNum));
                    } else if (queueNum == 3) {
                        refQueue[2].removeCustomer(indexValidation(queueNum));
                    } else if (queueNum == 4) {
                        refQueue[3].removeCustomer(indexValidation(queueNum));
                    } else {
                        refQueue[4].removeCustomer(indexValidation(queueNum));
                    }
                }
                case "104", "PCQ", "pcq" -> {
                    int queueNum = inputValidation("Enter which queue you want to remove the customer from : ");
                    if (queueNum == 1){
                        refQueue[0].removeServed();
                    } else if (queueNum == 2) {
                        refQueue[1].removeServed();
                    } else if (queueNum == 3) {
                        refQueue[2].removeServed();
                    } else if (queueNum == 4) {
                        refQueue[3].removeServed();
                    } else {
                        refQueue[4].removeServed();
                    }
                }
                case "105", "VCS", "vcs" -> {
                    System.out.println("----------- Customer sorted A - Z by name -----------");
                    refQueue[0].sortAlpha("Queue 1 ->");
                    refQueue[1].sortAlpha("Queue 2 ->");
                    refQueue[2].sortAlpha("Queue 3 ->");
                    refQueue[3].sortAlpha("Queue 4 ->");
                    refQueue[4].sortAlpha("Queue 5 ->");
                }
                case "106", "SPD", "spd" -> {
                    try {
                        FileWriter myWriter = new FileWriter("Data.txt");
                        refQueue[0].writingTOFile("Queue 1 ->",myWriter);
                        refQueue[1].writingTOFile("Queue 2 ->",myWriter);
                        refQueue[2].writingTOFile("Queue 3 ->",myWriter);
                        refQueue[3].writingTOFile("Queue 4 ->",myWriter);
                        refQueue[4].writingTOFile("Queue 5 ->",myWriter);
                        FuelQueue.writingFuelStock(myWriter);
                        System.out.println("Successfully written to the file :)");
                        myWriter.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "107", "LPD", "lpd" -> {
                    try {
                        int count=0;
                        File inputFile = new File("Data.txt");
                        Scanner readFile = new Scanner(inputFile);
                        while (readFile.hasNextLine()) {
                            String data = readFile.nextLine();
                            String section1 =data.substring(0,5);
                            if(!((section1.equals("Queue"))||(section1.equals("Remai")))){
                                String[] x = data.split(" ");
                                if (count==1){
                                    refQueue[0].addCustomer(x[0],x[1],x[2],Double.parseDouble(x[3]),1 );
                                }else if (count==2) {
                                    refQueue[1].addCustomer(x[0],x[1],x[2],Double.parseDouble(x[3]),2 );
                                }else if (count==3) {
                                    refQueue[2].addCustomer(x[0],x[1],x[2],Double.parseDouble(x[3]),3 );
                                }else if (count==4) {
                                    refQueue[3].addCustomer(x[0],x[1],x[2],Double.parseDouble(x[3]),4 );
                                }else {
                                    refQueue[4].addCustomer(x[0],x[1],x[2],Double.parseDouble(x[3]),5 );
                                }
                            }else{
                                count++;
                            }
                        }
                        System.out.println("Data Loaded successfully :)");
                    }catch (Exception e) {
                        System.out.println("Error when reading the file");
                    }
                }
                case "108", "STK", "stk" -> {
                    System.out.println(FuelQueue.getStock());
                }
                case "109", "AFS", "afs" -> {
                    System.out.print("Enter fuel stock : ");
                    double newStock;
                    try {
                        newStock = input.nextDouble();
                        input.nextLine();
                    }catch (Exception e){
                        input.nextLine();
                        System.out.println("Invalid input for Stock");
                        continue;
                    }
                    if(newStock<0){
                        System.out.println("invalid stock");
                        continue;
                    }
                    FuelQueue.setStock(newStock);
                    System.out.println("Fuel Stock updated");
                }
                case "110", "IFQ", "ifq" -> {
                    System.out.println("Income Queue 1 : Rs "+ refQueue[0].income());
                    System.out.println("Income Queue 2 : Rs "+ refQueue[1].income());
                    System.out.println("Income Queue 3 : Rs "+ refQueue[2].income());
                    System.out.println("Income Queue 4 : Rs "+ refQueue[3].income());
                    System.out.println("Income Queue 5 : Rs "+ refQueue[4].income());
                }
                case "999", "EXT", "ext" -> {
                    System.out.println("Program terminated");
                    break loop1;
                }
                default -> {
                    System.out.println("Invalid Operation try again");
                }
            }
            //each iteration check stock count is low
            if (FuelQueue.getStock() <= FuelQueue.LIMIT) {
                System.out.printf("Remaining Stock is %.2f --> Please refill !!! %n", FuelQueue.getStock());
            }
        }while (true);
    }

    // method related to 100
    public static void viewAllQueue(FuelQueue queue,int num){
        System.out.printf("Queue %d : - %n",num);
        queue.viewCustomer();
    }

    //method related to 103,104

    // input validation for which queue(1-5)
    public static int inputValidation(String op){
        Scanner scanner = new Scanner(System.in);

        while (true){
            try{
                System.out.print(op);
                String qNum = scanner.nextLine();
                int Num = Integer.parseInt(qNum);
                if ((Num<=5)&&(1<=Num)){
                    return Num;
                }else{
                    System.out.println("Queue number you entered out of range enter a valid Queue number [1,2,3]");
                }
            }catch (Exception e){
                System.out.println("Enter a number 1,2,3,4 or 5");
            }
        }
    }

    // validation for index of the queue(array)
    public static int indexValidation(int num){
        Scanner scanner = new Scanner(System.in);
        do{
            try{
                System.out.printf("Enter the position of %d queue you want to remove the customer:",num);
                String qNum = scanner.nextLine();
                int Num = Integer.parseInt(qNum);
                if ((Num<=6)&&(1<=Num)){
                    return Num;
                }else{
                    System.out.println("index number out of range try number less or equal to 6");
                }
            }catch (Exception e){
                System.out.println("Enter a number");
            }
        }while (true);
    }


}

