import java.util.Scanner;

public class UserInput {

    public static void twoNumbers(){

        Scanner input = new Scanner(System.in);

        System.out.println("Please enter the first number: ");
        double num1 = input.nextDouble();
        System.out.println("What operation do you wish to perform? (+ - * /)");
        char operation = Calculator.isValidOp();
        System.out.println("Please enter the second number: ");
        double num2 = input.nextDouble();
        System.out.println("The result is: " +Calculator.resultOfTwo(num1, operation, num2));

    }

    public static void numArr(){

        Scanner input = new Scanner(System.in);

        System.out.println("With how many numbers do you wish to work with?");
        int arrSize = 0;
        while(arrSize<2){
            arrSize = input.nextInt();
            if(arrSize < 2){
                System.out.println("Error, the list must be at least 2 numbers long, insert a new number:");
            }
        }
        System.out.println("Enter your ["+arrSize +"] numbers:");

        double[] userArr = new double[arrSize];
        for(int i = 0; i < arrSize; i++){
            System.out.println("Enter number "+(i+1) +": ");
            userArr[i] = input.nextDouble();
        }

        System.out.println("What operation do you wish to perform? (+ - * /)");
        char operation = Calculator.isValidOp();
        System.out.println("The result is: " +Calculator.resultOfArr(userArr, operation));
    }
}
