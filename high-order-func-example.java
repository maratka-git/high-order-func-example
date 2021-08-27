import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.Math.pow;
import static java.lang.StrictMath.sqrt;

/*
 *  solution for the task given in:
 *  https://javarush.ru/groups/posts/495-ljambdih-i-strimih-toljhko-praktika-teorii-ne-budet
 *  implementation of the simple ~calculate~ method with high order function:
*/
public class CalcLambdas {
    //  вычислять корень;
    private static Double square (Double a) {
        return sqrt (a);
    }
    // 2 in power;
    private static Double power2 (Double b) {
        return pow (2.0, b);
    }
    // Складывать;
    private static Double add(Double a, Double b) {
        return a + b;
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
    public static Double calculate (Double value, Function<Double, Double> func) {
        return func.apply(value);
    }
    public static Double calculate (Double a, Double b, BiFunction <Double, Double, Double> bifunc) {
        return bifunc.apply(a, b);
    }

    public static void main(String[] args) {
        //  unary func:
        System.out.format("add, result: %f\n", calculate (4.0, CalcLambdas::square));
        System.out.format("power2, result: %f\n", calculate (3.5, CalcLambdas::power2));
        System.out.format("lambda two_in_power: %f\n", calculate (4.0, a -> pow (2.0, a)));
        //  binary func:
        System.out.format("add, result: %f\n", calculate (31.5, 2.27, CalcLambdas::add));
        System.out.format("lambda add, result: %f\n", calculate (31.5, 2.27, (a, b) -> {return a +b;}));
        System.out.format("sub, result: %f\n", calculate (31.5, 2.27, CalcLambdas::sub));
        System.out.format("mul, result: %f\n", calculate (31.5, 2.27, CalcLambdas::mul));
        System.out.format("div, result: %f\n", calculate (31.5, 2.27, CalcLambdas::div));
        System.out.format("complx, result: %f\n", calculate (31.5, 2.27, CalcLambdas::some_sum_with_div));
    }
}

//  resources used:
//  https://dzone.com/articles/functional-programming-in-java-8-part-1-functions-as-objects

