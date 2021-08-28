
/*
 *  solution for a small task assigned in:
 *  https://javarush.ru/groups/posts/495-ljambdih-i-strimih-toljhko-praktika-teorii-ne-budet
 *  implementation of the simple ~calculate~ method with high order function:
*/
public class CalcLambdas {
    // 2 in power;
    private static Double power2 (Double b) {
        return pow (2.0, b);
    }
    // вычитать;
    private static Double sub(Double a, Double b) {
       return a - b;
    }
    // умножать;
    private static Double mul(Double a, Double b) {
       return a * b;
    }
    // делить;
    private static Double div(Double a, Double b) {
       return a / b;    //  without any checking of course :)
    }
    // возводить в степень сумму аргументов поделенную на первое число + 117;
    private static Double some_sum_with_div(Double a, Double b) {
       return (a + b) / (a + 12345);
    }

    //  overloaded calculate func for various number of args:
    /*
    public static Double calculate (Double value, Function<Double, Double> func) {
        return func.apply(value);
    }
     */
    public static Double calculate (Double value, UnaryOperator<Double> func) {
        return func.apply(value);
    }
    public static Double calculate (Double a, Double b, BiFunction <Double, Double, Double> bifunc) {
        return bifunc.apply(a, b);
    }

    public static void main(String[] args) {
        //  вычислять корень (using UnaryOperator<T> functional interface in Java 1.8 lang.):
        UnaryOperator<Double> square = StrictMath::sqrt;
        //  unary funcs:
        System.out.format("add, result: %f\n", calculate (4.0, square));
        //  using
        System.out.format("power2, result: %f\n", calculate (3.5, CalcLambdas::power2));
        System.out.format("lambda two_in_power: %f\n", calculate (4.0, a -> pow (2.0, a)));
        //  binary funcs  (using BinaryOperator<T> ):
        BinaryOperator<Double> add =  (p1, p2) -> p1 + p2;
        System.out.format("add, result: %f\n", calculate (31.5, 2.27, add));
        //  using a func implemented in our class:
        System.out.format("lambda add, result: %f\n", calculate (31.5, 2.27, (a, b) -> a + b));
        System.out.format("sub, result: %f\n", calculate (31.5, 2.27, CalcLambdas::sub));
        System.out.format("mul, result: %f\n", calculate (31.5, 2.27, CalcLambdas::mul));
        System.out.format("div, result: %f\n", calculate (31.5, 2.27, CalcLambdas::div));
        System.out.format("complx, result: %f\n", calculate (31.5, 2.27, CalcLambdas::some_sum_with_div));
    }
}

//  resources used:
//  https://dzone.com/articles/functional-programming-in-java-8-part-1-functions-as-objects
//  https://www.tutorialspoint.com/how-to-use-binaryoperator-t-interface-in-lambda-expression-in-java
//  https://www.w3schools.com/java/java_lambda.asp
