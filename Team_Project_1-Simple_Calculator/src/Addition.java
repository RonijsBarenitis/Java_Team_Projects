public class Addition extends Calculator{

    public static double sumOfTwo(double num1, double num2){
        return num1+num2;
    }

    public static double sumOfArr(double[] arr) {
        double result = 0;
        for (double v : arr) {
            result += v;
        }
        return result;
    }
}
