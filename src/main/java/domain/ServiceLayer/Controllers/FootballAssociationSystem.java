package domain.ServiceLayer.Controllers;

import domain.BusinessLayer.SystemFeatures.TeamMemberPersonalPage;
import domain.BusinessLayer.Users.Fan;
import domain.BusinessLayer.Users.Footballer;
import domain.BusinessLayer.Users.Owner;
import domain.BusinessLayer.Users.SystemManager;
import domain.CrossCutting.Utils;
import domain.DB.SystemController;
import domain.BusinessLayer.Enum.TeamState;
import domain.ExternalServices.ExternalServices;
import domain.BusinessLayer.Football.Team;
import domain.ServiceLayer.Controllers.*;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashMap;
import java.util.Map;

import static domain.BusinessLayer.Enum.FootballerPosition.Center_Back;

public class FootballAssociationSystem {

    public static boolean initializeSystem(String systemManagerEmail) throws Exception {
        boolean valid = EmailValidator.getInstance().isValid(systemManagerEmail);
        if(!valid){
            throw new Exception("Not valid email");
        }

        SystemManager systemManager = new SystemManager(systemManagerEmail, Utils.sha256("initialPassword"+systemManagerEmail),"system","manager",
                systemManagerEmail);
        SystemController.userNameUser.put(systemManagerEmail, systemManager);

        ExternalServices.sendInviteToTheSystem(systemManagerEmail,systemManagerEmail,"initialPassword"+systemManagerEmail,"System owner");
        ExternalServices.establishConnectionToTaxSystem();
        ExternalServices.establishConnectionToAssociationAccountingSystem();
        return true;
    }

    public static void main(String[] args) throws Exception {
        GuestController gc = new GuestController();
        SignedInController sc = new SignedInController();
        FanController fc = new FanController();
        PersonalPageSystem pps = new PersonalPageSystem();


        //test_UC3_5_NotAcceptance_WrongDates() throws Exception {
        try {
            Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
            Map<String, Long> mymap = fc.mySearchHistory(f, System.currentTimeMillis(), (long) System.currentTimeMillis() - 1000);
        } catch (Exception e) {
            String message = "Wrong Dates";
            System.out.println(message.equals(e.getMessage()));
        }
        // test_UC3_5_NotAcceptance_NoSearch() {
        try {
            Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
            long start = System.currentTimeMillis();
            Thread.sleep(5);
            long end = System.currentTimeMillis();
            Map<String, Long> mymap = fc.mySearchHistory(f, start, end);
        } catch (Exception e) {
            String message1 = "No Search History";
            System.out.println(message1.equals(e.getMessage()));
        }

        // test_UC4_2_Acceptance()
        Owner tw = new Owner("delbuske@gmail.com", "192837465", "del", "buske", "delbuske@gmail.com");
        Team rm = new Team("real_madrid", TeamState.active, tw);
        Footballer ramos = TeamOwnerController.signUpNewFootballer(tw, "sergio", "ramos", "sergio@gmail.com", Center_Back, rm);
        HashMap<String, String> valuesToUpdate = new HashMap<>();
        valuesToUpdate.put("history", "i love spain");
        valuesToUpdate.put("home", "is whenever im with you");
        pps.addContentToPersonalPage(ramos.getMyPersonalPage(), valuesToUpdate);
        boolean ans = (((TeamMemberPersonalPage) ramos.getMyPersonalPage()).getContent().compareTo("history: i love spain") == 0);
        System.out.println(((TeamMemberPersonalPage) ramos.getMyPersonalPage()).getContent());
    }
}




