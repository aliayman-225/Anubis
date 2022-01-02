package com.example.demo.Core;

import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Ali
 */
public class Admin {

    Authentication authentication;
    ArrayList<String> notifications=new ArrayList<String>();

    public Admin() {

    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public boolean activate(String username) {
        for (int i = 0; i < authentication.getDrivers().size(); i++) {
            if ((authentication.getDrivers().get(i).getUsername().equals(username))) {
                if (authentication.getDrivers().get(i).isSuspendedAccount()) {
                    authentication.getDrivers().get(i).setSuspendedAccount(false);
                    System.out.println("Activated");
                    return true;
                }
                else {
                    System.out.println("Already activated");
                    return false;
                }
            }
        }
        for (int i = 0; i < authentication.getPassengers().size(); i++) {
            if ((authentication.getPassengers().get(i).getUsername().equals(username))) {
                if (authentication.getPassengers().get(i).isSuspendedAccount()) {
                    authentication.getPassengers().get(i).setSuspendedAccount(false);
                    System.out.println("Activated");
                    return true;
                } else {
                    System.out.println("Already activated");
                    return false;
                }
            }

        }
        return false;
    }

    public boolean suspend(String username) {
        for (int i = 0; i < authentication.getDrivers().size(); i++) {
            if ((authentication.getDrivers().get(i).getUsername().equals(username))) {
                if (!authentication.getDrivers().get(i).isSuspendedAccount()) {
                    authentication.getDrivers().get(i).setSuspendedAccount(true);
                    System.out.println("Suspended");
                    return true;
                } else {
                    System.out.println("Already Suspended");
                    return true;
                }
            }

        }
        for (int i = 0; i < authentication.getPassengers().size(); i++) {
            if ((authentication.getPassengers().get(i).getUsername().equals(username))) {
                if (!authentication.getPassengers().get(i).isSuspendedAccount()) {
                    authentication.getPassengers().get(i).setSuspendedAccount(true);
                    System.out.println("Suspended");
                    return true;
                } else {
                    System.out.println("Already Suspended");
                    return true;
                }
            }

        }

        return false;
    }

    public ArrayList<Driver> listAllPendingDrivers() {
        ArrayList<Driver> pendingDrivers = new ArrayList<Driver>();
        for (int i = 0; i < authentication.getDrivers().size(); i++) {
            if (authentication.getDrivers().get(i).isSuspendedAccount()) {
                pendingDrivers.add(authentication.getDrivers().get(i));
            }
        }
        return pendingDrivers;
    }

    void getNotifications ()
    {
        for(int i=0;i<notifications.size();i++)
            System.out.println(notifications.get(i));
    }

    public void addDiscountOnSpecificArea(String area)
    {
        authentication.discountAreas.add(area);


    }

    public ArrayList<String> showRideActions(String rideID)
    {
        for(int i=0;i<authentication.getRides().size();i++)
            if(rideID.equals(authentication.getRides().get(i).rideID))
                return authentication.getRides().get(i).actions;
        return null;
    }

}

