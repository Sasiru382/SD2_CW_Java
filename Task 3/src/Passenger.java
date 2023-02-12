public class Passenger {

    private final String firstName;
    private final String secondName;
    private final String VehicleNo;
    private final double noLitersNeed;

    public Passenger(String firstName, String secondName, String vehicleNo, double noLitersNeed) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.VehicleNo = vehicleNo;
        this.noLitersNeed = noLitersNeed;
    }


    public double getNoLitersNeed() {
        return noLitersNeed;
    }

    // display all the details about a customer
    public void viewCustomerDetails(){
        System.out.printf("%s    %s    %s    %.2f",firstName,secondName,VehicleNo,noLitersNeed);
    }
    // to return customer details
    public String getDetails(){
        return firstName +" "+ secondName+" "+VehicleNo+" "+noLitersNeed;
    }
    public String customerName(){
        return firstName+ " "+ secondName;
    }

}

