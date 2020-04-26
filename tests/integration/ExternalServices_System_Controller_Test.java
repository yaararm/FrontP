package integration;

import domain.ExternalServices.ExternalServices;
import domain.DB.SystemController;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ExternalServices_System_Controller_Test {

    private static SystemController systemController;
    private static ExternalServices externalServices;

    @BeforeClass
    public static void BeforeClass(){
        systemController = new SystemController();
        externalServices = new ExternalServices();
    }

    //establishConnectionToTaxSystem
    @Test
    public void test_establishConnectionToTaxSystem() throws InterruptedException {
        assertTrue(externalServices.establishConnectionToTaxSystem());
        assertTrue(systemController.logger!=null);
    }

    //establishConnectionToAssociationAccountingSystem
    @Test
    public void test_establishConnectionToAssociationAccountingSystem() throws InterruptedException {
        assertTrue(externalServices.establishConnectionToAssociationAccountingSystem());
        assertTrue(systemController.logger!=null);
    }
}
