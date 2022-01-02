package com.example.demo.Core;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Reem
 */

public class Passenger extends User {

    ArrayList<String> notifications = new ArrayList<String>();
    boolean makeHisFirstRide;
    LocalDate birthDate;
    public Passenger(String name ,String username, String mobileNumber, String email, String password,int bdMonth ,int bdDay) {
        super(name, username, mobileNumber, email, password);
        makeHisFirstRide=false;
        Date d=new Date();
        birthDate= LocalDate.of(d.getYear()+1900,bdMonth,bdDay);

    }
    public Passenger(String name ,String username, String mobileNumber, String email, String password) {
        super(name, username, mobileNumber, email, password);

    }

    public void rateDriver(int rate, Driver driver) {
        driver.getRateHistory().add(this.getUsername());
        driver.getRateHistory().add(String.valueOf(rate));
        driver.setAverageRate();

    }

    public void acceptOffer(Ride ride) {
        ride.setIsAccepted(true);
        ride.getCaptain().ongoingRide=ride;
    }

    public double checkDriverRate(Driver driver) {

        return driver.getAverageRate();
    }

    public String requestRide(String source, String destination, Authentication authentication,int numberOfPassengers)
    {

        Ride ride = new Ride(authentication, source, destination, this,numberOfPassengers);
        for (int i = 0; i < authentication.getDrivers().size(); i++) {
            if (authentication.getDrivers().get(i).getFavAreas().contains(source) && authentication.getDrivers().get(i).ongoingRide==null) {
                authentication.getDrivers().get(i).getPendingRides().add(ride);
            }
        }
        authentication.getRides().add(ride);
        return ride.rideID;
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }



}