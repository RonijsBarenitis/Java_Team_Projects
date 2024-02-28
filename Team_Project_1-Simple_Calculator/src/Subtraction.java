public class Subtraction extends Calculator{
    public static double subOfTwo(double num1, double num2){
        return num1-num2;
    }

    public static double subOfArr(double[] arr) {
        double result = arr[0];
        for(int i = 1; i < arr.length; i++){
            result -= arr[i];
        }
        return result;
    }
}
