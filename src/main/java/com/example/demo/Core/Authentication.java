package com.example.demo.Core;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Ali
 */
public class Authentication {

    private ArrayList<Driver> drivers;
    private ArrayList<Passenger> passengers;
    private ArrayList<Ride> rides;
    ArrayList<String> discountAreas;
    ArrayList<LocalDate> publicHolidays;
    private static Authentication instance;
    Admin admin;
    Scanner input = new Scanner(System.in);

    private Authentication(Admin admin) {
        drivers = new ArrayList<Driver>();
        passengers = new ArrayList<Passenger>();
        rides = new ArrayList<Ride>();
        discountAreas=new ArrayList<String>();
        this.admin=admin;
        publicHolidays= new ArrayList<>();
        generateHolidays();
    }

    void generateHolidays()
    {
        Date d=new Date();
        publicHolidays.add(LocalDate.of(d.getYear()+1900,1,1));
        publicHolidays.add(LocalDate.of(d.getYear()+1900,1,25));
        publicHolidays.add(LocalDate.of(d.getYear()+1900,10,6));
        publicHolidays.add(LocalDate.of(d.getYear()+1900,4,25));
        publicHolidays.add(LocalDate.of(d.getYear()+1900,6,30));
    }

    public static Authentication getAuthentication(Admin admin) {
        if (instance == null) {
            instance = new Authentication(admin);
        }
        return instance;
    }

    public ArrayList<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(ArrayList<Driver> drivers) {
        this.drivers = drivers;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public ArrayList<Ride> getRides() {
        return rides;
    }

    public void setRides(ArrayList<Ride> rides) {
        this.rides = rides;
    }

    public boolean checkUser(User user2, User user) // function to check if the username and password matches in the data base
    {
        System.out.println(user.getUsername()+" "+user2.getUsername()+" "+user.getPassword()+" "+user2.getPassword());
        if ((user2.getUsername().equals(user.getUsername())) && (user2.getPassword().equals(user.getPassword()))) {
            return true;
        } else {
            return false;
        }
    }

    public User login(User user) {
        System.out.println(user.getClass().getName());
        if (user.getClass().getName().equals("com.example.demo.Core.Passenger")) {
            for (int i = 0; i < passengers.size(); i++) {
                System.out.println(this.checkUser(passengers.get(i), user));
                if (this.checkUser(passengers.get(i), user)) {
                    if (passengers.get(i).isSuspendedAccount()) {
                        System.out.println("This account suspended by the admin");
                        return null;
                    }

                    return passengers.get(i);
                }
            }
            System.out.println("Incorrect username or password");

        } else if (user.getClass().getName().equals("com.example.demo.Core.Driver")) {
            for (int i = 0; i < drivers.size(); i++) {
                if (this.checkUser(drivers.get(i), user)) {
                    if (drivers.get(i).isSuspendedAccount()) {
                        System.out.println("This account not verified yet by the admin");
                        return null;
                    }

                    return drivers.get(i);
                }
            }
            System.out.println("Incorrect username or password");

        }
        return null;
    }

    public boolean SignUp(User user) {
        for (int i = 0; i < passengers.size(); i++) {
            if (user.getUsername().equals(passengers.get(i).getUsername())) {
                System.out.println("Username was taken please choose another user name");
                return false;
            }
        }
        for (int i = 0; i < drivers.size(); i++) {
            if (user.getUsername().equals(drivers.get(i).getUsername())) {
                System.out.println("Username was taken please choose another user name");
                return false;
            }
        }
        if (user.getClass().getName().equals("com.example.demo.Core.Passenger")) {
            passengers.add((Passenger) user);
            System.out.println("Done");
            return true;
        } else if (user.getClass().getName().equals("com.example.demo.Core.Driver")) {
            drivers.add((Driver) user);
            System.out.println("Done");
            return true;
        } else {
            System.out.println("error please choose another username");
            return false;
        }

    }
}
