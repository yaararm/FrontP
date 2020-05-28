package Client;

import PresentationLayer.PresentationController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AlertsHttpNotification {
    ClientController cl = new ClientController();


    @RequestMapping("/")
    public String index() {
        return "Greetings from Yaara!";
    }


    //region User Control
    @RequestMapping(method = RequestMethod.POST, value = "/newAlert")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, String> newAlert(@RequestBody Map<String, String> newAlerts) {
        Map<String, String> ans = new HashMap<>();

        try {
           PresentationController.showNewAlerts(newAlerts);

            ans.put("status","fine");


        } catch (Exception e) {
            ans.put("status", "error");
            ans.put("error", e.getMessage());
            System.out.println(e);
        }


        return ans;

    }


}


