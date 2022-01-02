package com.example.demo.Core;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 * @author Mariam
 */


public class Ride {

    private String source;
    private String destination;
    private Driver captain;
    private Passenger passenger;
    private double offer;
    String rideID;
    boolean isAccepted;
    private Admin admin;
    int numberOfPassengers;
    ArrayList<String> actions = new ArrayList<>();
    Authentication authentication;

    public Ride(Authentication authentication, String source, String destination, Passenger passenger,int numberOfPassengers) {
        this.source = source;
        this.destination = destination;
        this.passenger = passenger;
        this.authentication = authentication;
        this.admin = authentication.admin;
        this.numberOfPassengers = numberOfPassengers;
        StringBuilder sb = new StringBuilder(12);
        String numbers = "0123456789";
        for (int i = 0; i < 12; i++) {
            int index = (int) (numbers.length() * Math.random());
            sb.append(numbers.charAt(index));
        }
        rideID = sb.toString();
        System.out.println("rideID :  " + rideID + "  please save it to contact the ID with the admin if any problems");
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Driver getCaptain() {
        return captain;
    }

    public void setCaptain(Driver captain) {
        this.captain = captain;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
        passenger.notifications.add("Your ride was accepted by captain " + captain.getUsername() + " with offer " + offer);
        LocalDateTime myDateObj = LocalDateTime.now();
        double passengerPay=checkDiscounts(offer);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
        actions.add("AT " + myDateObj.format(myFormatObj) + " Setting " + offer + " offer by " + this.captain.getUsername() + " but passenger require to pay "+passengerPay+" due to discounts");
        passenger.notifications.add("You require to pay now "+passengerPay+" LE");

    }

    public double checkDiscounts(double offer)
    {
        double passengerPay = offer;
        for (int i = 0; i < authentication.discountAreas.size(); i++) {
            if (destination.equals(authentication.discountAreas.get(i))) {
                passengerPay -= (passengerPay*0.1);
                passenger.notifications.add("Your ride has a 10% discount for this destination ");
            }
        }
        if(numberOfPassengers>=2)
        {
            passengerPay-= (passengerPay*0.05);
            passenger.notifications.add("Your ride has a 5% discount as more than one passenger ");
        }
        if(!passenger.makeHisFirstRide)
        {
            passengerPay -= (passengerPay*0.1);
            passenger.notifications.add("Your ride has a 10% discount for the first ride ");
            passenger.makeHisFirstRide=true;
        }
        for (int i = 0; i < authentication.publicHolidays.size(); i++) {
            if (LocalDate.now().equals(authentication.publicHolidays.get(i))) {
                passengerPay -= (passengerPay*0.05);
                passenger.notifications.add("Your ride has a 5% discount for a public holiday ");
            }
        }
        if(passenger.birthDate.equals(LocalDate.now()))
        {
            passengerPay -= (passengerPay*0.1);
            passenger.notifications.add("Your ride has a 10% discount for for your birthday with offer ");
            passenger.makeHisFirstRide=true;
        }
        return passengerPay;

    }

    public boolean isIsAccepted() {
        return isAccepted;
    }


    public void setIsAccepted(boolean isAccepted) {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
        actions.add("AT " + myDateObj.format(myFormatObj) + " Accept offer " + offer + " by " + this.passenger.getUsername());
        this.isAccepted = isAccepted;

    }


    public void getRideInfo() {
        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        System.out.println("Passenger: " + passenger);
        System.out.println("Driver: " + captain);

    }

    public String getId() {
        return rideID;
    }

    public void setId(String nextId) {
        this.rideID=nextId;
    }
}