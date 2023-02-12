import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

//Saving data will overwrite the current saved data.

public class FuelQueueArrayV {

    private static int stock = 6600;
    private static final int LIMIT = 500;

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
                999 or EXT: Exit the Program.
                                
                """);

        Scanner input = new Scanner(System.in);
        //initialize arrays
        String[] queue1 = {};
        String[] queue2 = {};
        String[] queue3 = {};

        loop1:
        do {
            System.out.print("Select operation : ");
            String choice = input.nextLine();
            switch (choice) {
                case "100", "VFQ", "vfq" -> {
                    viewQueue(queue1, 1);
                    viewQueue(queue2, 2);
                    viewQueue(queue3, 3);
                }
                case "101", "VEQ", "veq" -> {
                    System.out.println("Available queue for customer input : - ");
                    boolean emptyQueue1 = isArrayEmpty(queue1);
                    boolean emptyQueue2 = isArrayEmpty(queue2);
                    boolean emptyQueue3 = isArrayEmpty(queue3);
                    if (emptyQueue1) {
                        System.out.println("Queue 1");
                    }
                    if (emptyQueue2) {
                        System.out.println("Queue 2");
                    }
                    if (emptyQueue3) {
                        System.out.println("Queue 3");
                    }
                    if ((!emptyQueue1) & (!emptyQueue2) & (!emptyQueue3)) {
                        System.out.println("Non of the queue are available");
                    }
                }
                case "102", "ACQ", "acq" -> {
                    int qNum = inputValidation("Enter queue number : ");

                    if (qNum == 1) {
                        queue1 = addCustomer(queue1, qNum);
                    } else if (qNum == 2) {
                        queue2 = addCustomer(queue2, qNum);
                    } else {
                        queue3 = addCustomer(queue3, qNum);
                    }
                }
                case "103", "RCQ", "rcq" -> {

                    // When Customer removed from a specific location it will take as he is leaving the queue and fuel stock will restore to the value before he was assigned.

                    int qNum = inputValidation("Enter which queue you want to remove the customer from : ");

                    if (qNum == 1) {
                        queue1 = removeCustomer(queue1, qNum, "rcq");
                    } else if (qNum == 2) {
                        queue2 = removeCustomer(queue2, qNum, "rcq");
                    } else {
                        queue3 = removeCustomer(queue3, qNum, "rcq");
                    }
                }
                case "104", "PCQ", "pcq" -> {
                    int qNum = inputValidation("Enter which queue have the served customer : ");

                    if (qNum == 1) {
                        queue1 = removeCustomer(queue1, qNum, "pcq");
                    } else if (qNum == 2) {
                        queue2 = removeCustomer(queue2, qNum, "pcq");
                    } else {
                        queue3 = removeCustomer(queue3, qNum, "pcq");
                    }
                }
                case "105", "VCS", "vcs" -> {
                    System.out.println("Queue 1 A-Z Sorted : "+Arrays.toString(sortArray(queue1,queue1.length)));
                    System.out.println("Queue 2 A-Z Sorted : "+Arrays.toString(sortArray(queue2,queue2.length)));
                    System.out.println("Queue 3 A-Z Sorted : "+Arrays.toString(sortArray(queue3,queue3.length)));

                }
                case "106", "SPD", "spd" -> {
                    try {
                        FileWriter myWriter = new FileWriter("Data.txt");
                        myWriter.write("Queue 1 : " + Arrays.toString(queue1) + "\n");
                        myWriter.write("Queue 2 : " + Arrays.toString(queue2) + "\n");
                        myWriter.write("Queue 3 : " + Arrays.toString(queue3) + "\n");
                        myWriter.write("Remaining fuel Stock : " + stock + "\n");
                        myWriter.close();
                        System.out.println("Successfully written to the file");
                    } catch (IOException e) {
                        System.out.println("Error when writing to the file");
                    }
                }
                case "107", "LPD", "lpd" -> {
                    try {
                        File inputFile = new File("Data.txt");
                        Scanner readFile = new Scanner(inputFile);
                        int lineNumber = 1;
                        while (readFile.hasNextLine()) {
                            String data = readFile.nextLine();
                            data = data.substring(10).replace('[', ' ').replace(']', ' ').strip();
                            data = data.replace(" ", "");
                            if (lineNumber == 1) {
                                System.out.println("Queue 1 : " + data);
                                queue1 = data.split(",");
                            } else if (lineNumber == 2) {
                                System.out.println("Queue 2 : " + data);
                                queue2 = data.split(",");
                            } else if (lineNumber == 3) {
                                System.out.println("Queue 3 : " + data);
                                queue3 = data.split(",");
                            } else {
                                System.out.println(data);
                                stock = Integer.parseInt(data.substring(10));
                            }
                            lineNumber++;
                        }
                        System.out.println("File Data loaded successfully :)");
                        readFile.close();
                    } catch (Exception e) {
                        System.out.println("Error when reading the file");
                    }
                }
                case "108", "STK", "stk" -> {
                    System.out.println("Remaining fuel " + stock + "L");
                }
                case "109", "AFS", "afs" -> {
                    stock = addingFuelStock(stock);
                    System.out.println("Stock is updated " + stock);
                }
                case "999", "EXT", "ext" -> {
                    System.out.println("Program terminated");
                    break loop1;
                }
                default -> {
                    System.out.println("Invalid Operation try again");
                }
            }
            if (stock <= LIMIT) {
                System.out.printf("Remaining Stock is %d. Please refill !!! %n", stock);
            }
        } while (true);
    }


    // function for view all fuel queue
    public static void viewQueue(String [] queue,int num){
        boolean value = true;
        System.out.printf("Queue %d : - ",num);
        for (String i : queue){
            if (i != null){
                System.out.print(i+" ");
                value=false;
            }
        }
        if (value){
            System.out.print("empty");
        }
        System.out.println();
    }


    // function for checking an array is empty
    public static boolean isArrayEmpty(String [] queue) {
        return queue.length < 6;
    }


    //function to add customer to queue
    public static String[] addCustomer(String[] queue,int qNum){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter customer name : ");
        String customer = sc.nextLine();
        //Convert first letter to uppercase
        customer = String.valueOf(customer.charAt(0)).toUpperCase()+customer.substring(1);
        int n = queue.length;
        int i;
        if (n<6){
            String[] newArr = new String[n+1];
            for (i = 0; i < n; i++) {
                newArr[i] = queue[i];
            }
            newArr[n] = customer;
            System.out.printf("%s added to queue %d successfully.%n",customer,qNum);
            FuelQueueArrayV.stock = stockCount(FuelQueueArrayV.stock,"acq");
            return newArr;
        }
        System.out.println("Queue is full");
        return queue;
    }



    // removing a customer from a specific location in a queue && removing the first served customer
    public static String[] removeCustomer(String[] queue, int qNum,String operation) {
        int index=0;
        int n = queue.length;
        if(operation.equalsIgnoreCase("rcq")) {

            Scanner sc = new Scanner(System.in);
            System.out.printf("Enter the place in the queue %d : ", qNum);
            index = sc.nextInt();
            index -= 1;
            // If the array is empty
            // or the index is not in array range
            // return the original array
            if (index < 0 || index >= n) {
                System.out.println("no customer in that place");
                return queue;
            }

            // Create another array of size one less
            String[] newArray = new String[n - 1];

            // Copy the elements except the index
            // from original array to the other array
            for (int i = 0, k = 0; i < n; i++) {

                // if the index is
                // the removal element index
                if (i == index) {
                    System.out.printf("Customer "+queue[index]+" removed from the queue %d.%n",qNum);
                    continue;
                }

                newArray[k++] = queue[i];
            }
            //update the stock
            FuelQueueArrayV.stock = stockCount(FuelQueueArrayV.stock,"rcq");
            // return the resultant array
            return newArray;
        }else{
            // remove first customer
            String[] newArray = new String[n - 1];
            for (int i = 0, k = 0; i < n; i++) {
                if (i == index) {
                    System.out.println("Served Customer is removed.");
                    continue;
                }
                newArray[k++] = queue[i];
            }
            return newArray;
        }
    }


    // add fuel stock
    public static int addingFuelStock(int STOCK){
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter additional stock : ");
            try {
                int addStock = sc.nextInt();
                if (addStock<0){
                    System.out.println("invalid");
                    continue;
                }
                return STOCK + addStock;
            } catch (Exception e) {
                System.out.println("Please enter a valid stock count");
            }
        }
    }


    // Calculate stock
    public static int stockCount(int stock,String operation) {
        if (operation.equalsIgnoreCase("acq")) {
            stock -= 10;
        } else {
            stock+=10;
        }
        return stock;
    }


    // Validate user input for queues
    public static int inputValidation(String op){
        Scanner scanner = new Scanner(System.in);

        while (true){
            try{
                System.out.print(op);
                String qNum = scanner.nextLine();
                int Num = Integer.parseInt(qNum);

                if ((1<=Num)&&(3>=Num)){
                    return Num;
                }else{
                    System.out.println("Queue number you entered out of range enter a valid Queue number [1,2,3]");
                }
            }catch (Exception e){
                System.out.println("Enter a number 1,2 or 3");
            }
        }
    }



    // Use the bubble sort to sort the array(fuel queue)
    public static String[] sortArray(String[] queue, int length) {
        String temp;

        // Sorting strings using bubble sort
        for (int j = 0; j < length - 1; j++)
        {
            for (int i = j + 1; i < length; i++)
            {
                if (queue[j].compareTo(queue[i]) > 0)
                {
                    temp = queue[j];
                    queue[j] = queue[i];
                    queue[i] = temp;
                }
            }
        }
        return queue;
    }
}

//reference(bubble sort) :- https://www.geeksforgeeks.org/sorting-strings-using-bubble-sort-2/