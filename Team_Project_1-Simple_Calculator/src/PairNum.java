import java.util.Scanner;

public class PairNum {
    public static void pairOrNot(){
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a number you wish to check if it is a pair number: ");
        int num = input.nextInt();

        if(num % 2 == 0){
            System.out.println(num +" is a pair number");
        } else{
            System.out.println(num +" is not a pair number");
        }
    }
}
