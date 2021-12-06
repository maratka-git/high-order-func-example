
package sample.calc;

import java.util.function.*;
import static java.lang.StrictMath.pow;

/*
 *  solution for a small task proposed in:
 *  https://javarush.ru/groups/posts/495-ljambdih-i-strimih-toljhko-praktika-teorii-ne-budet
 *  the implementation of the simple ~calculate~ method with high order function:
*/

/*  resources used:
 *  https://dzone.com/articles/functional-programming-in-java-8-part-1-functions-as-objects
 *  https://www.tutorialspoint.com/how-to-use-binaryoperator-t-interface-in-lambda-expression-in-java
 *  https://www.w3schools.com/java/java_lambda.asp
 */

public class CalcLambdas {
    // 2 in power;
    private static Double power2 (Double b) {
        return pow (2.0, b);
    }
    // sub;
    private static Double sub(Double a, Double b) {
       return a - b;
    }
    // mul;
    private static Double mul(Double a, Double b) {
       return a * b;
    }
    // div;
    private static Double div(Double a, Double b) {
       return a / b;    //  without any check of course :)
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
    public static Double calculate (Double a, Double b, BiFunction<Double, Double, Double> bifunc) {
        return bifunc.apply(a, b);
    }

    public static void main(String[] args) {
        //  calc square root (using UnaryOperator<T> functional interface in Java 1.8 lang.):
        UnaryOperator<Double> square = StrictMath::sqrt;
        //  unary funcs:
        System.out.format("add, result: %f\n", calculate (4.0, square));
        //  using
        System.out.format("power2, result: %f\n", calculate (3.5, CalcLambdas::power2));
        System.out.format("lambda two_in_power: %f\n", calculate (4.0, a -> pow (2.0, a)));
        //  binary funcs  (using BinaryOperator<T> ):
        BinaryOperator<Double> add = Double::sum;
        System.out.format("add, result: %f\n", calculate (31.5, 2.27, add));
        //  using a func implemented in our class:
        System.out.format("lambda add, result: %f\n", calculate (31.5, 2.27, Double::sum));
        System.out.format("sub, result: %f\n", calculate (31.5, 2.27, CalcLambdas::sub));
        System.out.format("mul, result: %f\n", calculate (31.5, 2.27, CalcLambdas::mul));
        System.out.format("div, result: %f\n", calculate (31.5, 2.27, CalcLambdas::div));
        System.out.format("some_func result: %f\n", calculate (31.5, 2.27, CalcLambdas::some_sum_with_div));
    }
}
