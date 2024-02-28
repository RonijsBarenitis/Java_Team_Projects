import java.util.Scanner;

import static java.lang.Math.pow;

public class Power {
    public static void power() {
        Scanner input = new Scanner(System.in);

        System.out.println("What number?");
        int num = input.nextInt();
        System.out.println("To what power?");
        int pow = input.nextInt();
        System.out.println(num +" to the power of " +pow +" is: "+pow(num, pow));
    }
}
