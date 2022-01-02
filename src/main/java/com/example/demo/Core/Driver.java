package com.example.demo.Core;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Ali
 */
public class Driver extends User {

    private ArrayList<String> favAreas = new ArrayList<String>();
    private ArrayList<Ride> pendingRides = new ArrayList<Ride>();
    private ArrayList<String> rateHistory = new ArrayList<String>();
    double averageRate = 0.0;
    private String driverLicenseNumber, nationalID;
    private double balance;
    Ride ongoingRide;

    public Driver(String name,String username, String mobileNumber, String email, String password, String driverLicenseNumber, String nationalID) {
        super(name, username, mobileNumber, email, password);
        this.driverLicenseNumber = driverLicenseNumber;
        this.nationalID = nationalID;
        ongoingRide=null;
        balance=0;

    }
    public ArrayList<String> getRateHistory() {
        return rateHistory;
    }

    public void setRateHistory(ArrayList<String> RateHistory) {
        this.rateHistory = RateHistory;
    }

    public ArrayList<String> getFavAreas() {
        return favAreas;
    }

    public void setFavAreas(ArrayList<String> favAreas) {
        this.favAreas = favAreas;
    }

    public ArrayList<Ride> getPendingRides() {
        return pendingRides;
    }

    public void setPendingRides(ArrayList<Ride> pendingRides) {
        this.pendingRides = pendingRides;
    }

    public ArrayList<String> showRequests() {
        ArrayList<String> returnValue=new ArrayList<>();
        returnValue.add("There is " + pendingRides.size() + " passengers in your fav area");
        for (int j = 0; j < pendingRides.size() && !pendingRides.get(j).isAccepted; j++) {
            returnValue.add("************Ride " + (j + 1) + " ************");
            returnValue.add("passenger : " + pendingRides.get(j).getPassenger().getUsername());
            returnValue.add("passenger Mobile Number: " + pendingRides.get(j).getPassenger().getMobileNumber());
            returnValue.add("Source : " + pendingRides.get(j).getSource());
            returnValue.add("destination : " + pendingRides.get(j).getDestination());
            returnValue.add("**********************************");
        }
        return returnValue;
    }
    public void arrivedAtSource() {
        if(ongoingRide!=null)
        {
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
            ongoingRide.actions.add("AT "+myDateObj.format(myFormatObj)+ " captain "+name+" arrived to the source "+ongoingRide.getSource());
        }
    }
    public void arrivedAtDestination() {
        if(ongoingRide!=null)
        {
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
            ongoingRide.actions.add("AT "+myDateObj.format(myFormatObj)+ " captain "+name+" arrived to the destination "+ongoingRide.getDestination());
            balance+=ongoingRide.getOffer();
            ongoingRide=null;

        }
    }

    public void acceptRideAndOffer(Ride ride, double offer) {
        ride.setCaptain(this);
        ride.setOffer(offer);

    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate() {
        double count = 0;
        for (int i = 1; i < rateHistory.size(); i += 2) {  // searching for the rate to inc. the count
            count += Double.parseDouble(rateHistory.get(i));
        }
        averageRate = count / (rateHistory.size() / 2);
    }

    public void printDriverInformation() {
        System.out.println("Username : " + this.getUsername());
        System.out.println("Email : " + this.getEmail());
        System.out.println("Mobile Number : " + this.getMobileNumber());
        System.out.println("Licsense Number : " + this.getDriverLicenseNumber());
        System.out.println("National ID : " + this.getNationalID());
        System.out.println("=========================================");

    }

}
