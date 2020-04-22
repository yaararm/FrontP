package domain;

import domain.Controllers.GuestController;
import domain.Controllers.SignedInController;
import domain.Controllers.SystemController;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        System.out.println("hello world");
        GuestController g = new GuestController();
        SignedInController s = new SignedInController();
        g.singUp("aa@bb.com","11111111","ssss","ssss");
        boolean b = s.signIn("aa@bb.com", "11111111");

    }
}
