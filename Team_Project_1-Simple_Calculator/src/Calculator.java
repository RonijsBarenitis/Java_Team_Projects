import java.util.Scanner;

public class Calculator {

    public static double resultOfTwo(double num1, char operation, double num2) {
        double result = 0;
        switch (operation) {
            case '+':
                result = Addition.sumOfTwo(num1, num2);
                break;
            case '-':
                result = Subtraction.subOfTwo(num1, num2);
                break;
            case '*':
                result = Multiplication.multOfTwo(num1, num2);
                break;
            case '/':
                result = Division.divOfTwo(num1, num2);
                break;
            default:
                System.out.println("Something went wrong");
                break;
        }
        return result;
    }

    public static double resultOfArr(double[] arr, char operation) {
        double result = 0;
        switch (operation) {
            case '+':
                result = Addition.sumOfArr(arr);
                break;
            case '-':
                result = Subtraction.subOfArr(arr);
                break;
            case '*':
                result = Multiplication.multOfArr(arr);
                break;
            case '/':
                result = Division.divOfArr(arr);
                break;
            default:
                System.out.println("Something went wrong");
                break;
        }
        return result;
    }

    public static char isValidOp(){

        Scanner input = new Scanner(System.in);
        boolean valid = false;
        char op;

        do{
            op = input.next().charAt(0);
            if((op == '+') || (op == '-') || (op == '*') || (op == '/')){
                valid = true;
            } else{
                System.out.println("Invalid operator");
            }
        } while(!valid);
        return op;
    }
}
