package com.example.demo.Core;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Core.*;

import java.util.ArrayList;

/**
 * @author Reem
 */

@RestController
public class ClientController {
    Passenger scopedPassenger ;
    Driver scopedDriver ;
    Admin admin ;
    Authentication authentication ;

    @GetMapping("/getAuthentication")
    public Authentication getAuthentication() {
        admin = new Admin();
        authentication = Authentication.getAuthentication(admin);
        admin.setAuthentication(authentication);
        return authentication;
    }


    @GetMapping("/logoutPassenger")
    public void logoutPassenger() {
        scopedPassenger=null;
    }
    @GetMapping("/logoutDriver")
    public void logoutDriver() {
        scopedDriver=null;
    }


    @GetMapping ("/getDriverBalance")
    public double getDriverBalance() {
        return scopedDriver.getBalance();
    }

    @PostMapping("/signupPassenger/{name}/{username}/{mobilenumber}/{email}/{password}/{bdMonth}/{bdDay}")
    public boolean signupPassenger(@PathVariable String name,@PathVariable String username,
                                   @PathVariable String mobilenumber,
                                   @PathVariable String email,
                                   @PathVariable String password,
                                   @PathVariable int bdMonth,
                                   @PathVariable int bdDay){
        User user = new Passenger(name,username,mobilenumber,email,password,bdMonth,bdDay);
        return authentication.SignUp(user);
    }

    @PostMapping("/signupDriver/{name}/{username}/{mobilenumber}/{email}/{password}/{driverLicenseNumber}/{nationalID}")
    public boolean signupPassenger(@PathVariable String name,@PathVariable String username,
                                   @PathVariable String mobilenumber,
                                   @PathVariable String email,
                                   @PathVariable String password,
                                   @PathVariable String driverLicenseNumber,
                                   @PathVariable String nationalID) {
        User user = new Driver(name,username,mobilenumber,email,password,driverLicenseNumber,nationalID);
        return authentication.SignUp(user);
    }

    @PostMapping("/loginDriver/{username}/{password}")
    public String loginDriver(@PathVariable String username,
                              @PathVariable String password){
        User user = new Driver("",username,"","",password,"","");
        User scopedUser= authentication.login(user);
        if(scopedUser!=null)
        {
            scopedDriver=(Driver)scopedUser;
            return "login Successfully --> welcome captain "+scopedDriver.getName();
        }
        return "login failed";
    }


    @PostMapping("/loginPassenger/{username}/{password}")
    public ArrayList<String> loginPassenger(@PathVariable String username,
                                            @PathVariable String password){
        ArrayList<String> notifications;
        User user = new Passenger("",username,"","",password,1,1);
        User scopedUser= authentication.login(user);
        if(scopedUser!=null)
        {
            scopedPassenger=(Passenger)scopedUser;
            notifications=scopedPassenger.getNotifications();
            notifications.add(0,"login Successfully --> welcome Passenger "+scopedPassenger.getName());
            return notifications;
        }
        notifications=new ArrayList<>();
        notifications.add("login failed");
        return notifications;
    }

    @PostMapping("/rateDriver/{rate}/{driverUserName}")
    public String rateDriver(@PathVariable int rate,
                             @PathVariable String driverUserName){
        Driver driver=null;
        for(int i=0;i<authentication.getDrivers().size();i++)
        {
            if(authentication.getDrivers().get(i).getUsername().equals(driverUserName))
                driver =authentication.getDrivers().get(i);
        }
        if(driver!=null)
        {
            scopedPassenger.rateDriver(rate,driver);
            return "rate submitted successfully";
        }
        return "Driver not found";
    }



    @PostMapping("/acceptOffer/{driverUserName}")
    public String acceptOffer(@PathVariable String driverUserName){
        Ride scopedRide=null;
        for (int j = 0; j < authentication.getRides().size(); j++) {
            if (authentication.getRides().get(j).getPassenger().getUsername().equals(scopedPassenger.getUsername())&& authentication.getRides().get(j).getCaptain().getUsername().equals(driverUserName)) {
                scopedRide=authentication.getRides().get(j);
            }
        }
        if(scopedRide!=null)
        {
            scopedPassenger.acceptOffer(scopedRide);
            return "offer accepted";
        }
        return "ride not found";
    }




    @PostMapping("/checkDriverAverageRate/{driverUserName}")
    public String checkDriverAverageRate(
            @PathVariable String driverUserName){
        for(int i=0;i<authentication.getDrivers().size();i++)
        {
            if(authentication.getDrivers().get(i).getUsername().equals(driverUserName))
                return ("Captain : "+driverUserName+" Average rate : "+authentication.getDrivers().get(i).getAverageRate());
        }
        return "Driver not found";
    }

    @PostMapping("/requestRide/{source}/{destination}/{numberOfPassengers}")
    public ArrayList<String>  requestRide(
            @PathVariable String source,
            @PathVariable String destination,
            @PathVariable int numberOfPassengers){
        ArrayList<String> output=new ArrayList<>();
        output.add("rideID :  "+scopedPassenger.requestRide(source, destination, authentication,numberOfPassengers)+ "  please save it to contact the ID with the admin if any problems");
        output.add ("Your ride is waiting for a captain");
        return output;
    }



    @PostMapping("/addFavouriteArea/{favArea}")
    public void addFavouriteArea(
            @PathVariable String favArea){
        scopedDriver.getFavAreas().add(favArea);
    }



    @GetMapping("/listAllRides")
    public ArrayList<String> listAllRides(){
        return scopedDriver.showRequests();
    }

    @GetMapping("/listRatingHistory")
    public ArrayList<String> listRatingHistory(){
        ArrayList<String> output=new ArrayList<String>();
        for(int j=0;j<scopedDriver.getRateHistory().size();j+=2)
        {
            output.add("Passenger : "+scopedDriver.getRateHistory().get(j)+" Rate : "+scopedDriver.getRateHistory().get(j+1));
        }
        return output;
    }


    @GetMapping("/arrivedAtSource")
    public String arrivedAtSource(){
        scopedDriver.arrivedAtSource();
        return "Done";
    }

    @GetMapping("/arrivedAtDestination")
    public String arrivedAtDestination(){
        scopedDriver.arrivedAtDestination();
        return "Done";
    }


    @PostMapping("/AcceptAndOffer/{username}/{offer}")
    public String acceptRideAndOffer(@PathVariable String username ,
                                     @PathVariable double offer) {
        for (int j = 0; j < authentication.getRides().size(); j++) {
            if (authentication.getRides().get(j).getPassenger().getUsername().equals(username)) {
                scopedDriver.acceptRideAndOffer(authentication.getRides().get(j), offer);
                return "done";
            }
        }
        return "invalid username ";

    }




    @PostMapping("/activate/{userName}")
    public String activate(@PathVariable String userName){
        if(!admin.activate(userName))
            return "user not found or maybe the user is already activated";
        return "Done";
    }

    @PostMapping("/suspend/{userName}")
    public String suspend(@PathVariable String userName){
        if(!admin.suspend(userName))
            return "user not found or maybe the user is already activated";
        return "Done";
    }

    @GetMapping("/listAllPendingDrivers")
    public ArrayList<Driver> listAllPendingDrivers(){
        return admin.listAllPendingDrivers();
    }



    @PostMapping("/showRideActions/{rideId}")
    public ArrayList<String> showRideActions(@PathVariable String rideId){
        return admin.showRideActions(rideId);
    }

    @PostMapping("/addDiscountOnSpecificArea/{area}")
    public String addDiscountOnSpecificArea(@PathVariable String area){
        admin.addDiscountOnSpecificArea(area);
        return "done";
    }

}