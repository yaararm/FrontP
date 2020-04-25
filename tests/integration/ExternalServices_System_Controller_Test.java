package integration;

import domain.Controllers.ExternalServices;
import domain.Controllers.SystemController;
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
}
