import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        chooseOperation();
    }

    public static void chooseOperation() {

        chooseOperationPrintMsg();

        Scanner input = new Scanner(System.in);

        int choice;
        do {
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    UserInput.twoNumbers();
                    break;
                case 2:
                    UserInput.numArr();
                    break;
                case 3:
                    PairNum.pairOrNot();
                    break;
                case 4:
                    Power.power();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while ((choice != 1) && (choice != 2) && (choice != 3) && (choice != 4));
    }

    public static void chooseOperationPrintMsg(){
        System.out.println("Enter 1 to work with a pair of numbers");
        System.out.println("Enter 2 to work with a list of numbers");
        System.out.println("Enter 3 to check if your number is a pair number");
        System.out.println("Enter 4 to raise a number to a certain power");
    }
}