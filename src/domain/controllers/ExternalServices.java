package domain.controllers;

import domain.Team;

import java.util.Random;

public class ExternalServices {

    public static boolean getNewTeamAuthorization(Team teamToAdd){
        Random rand = new Random();
        if(rand.nextInt() < 0.9)
            return false;
        return true;
    }
}
