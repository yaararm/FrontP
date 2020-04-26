package Acceptness;
import BusinessLayer.FootballAssociationSystem;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class UC1 {

    //jenkis test ToDo delete

    FootballAssociationSystem fas = new FootballAssociationSystem();
    @Test
    public void test_UC_1_1_Acceptance() throws Exception {
        boolean ans = fas.initializeSystem("itai_dag@gmail.com");
        assertTrue(ans);

    }

    @Test
    public void test_UC_1_1_NotAcceptance(){
        try {
            fas.initializeSystem("ytrbhdfgdjfg");
        } catch (Exception e) {
            String message = "Not valid email";
            assertEquals(message, e.getMessage());
        }

    }


}
