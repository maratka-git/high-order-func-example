
package sample.calc;

import java.util.*;
import java.util.function.*;

import static java.lang.StrictMath.pow;

/*  resources used:
 *  https://stackoverflow.com/questions/34669586/how-can-i-implement-function-and-bifunction-at-the-same-time
 *  https://coderoad.ru/32057262/Java-8-применить-BiFunction-к-двум-спискам-различных-объектов
 */
interface GenericFunction<T, U, R> extends Function<T, R>, BiFunction<T, U, R> {
    @Override
    default <V> GenericFunction<T, U, V> andThen(Function<? super R, ? extends V> after) {
        return new GenericFunction<>() {
            @Override
            public V apply(final T t, final U u) {
                return after.apply(GenericFunction.this.apply(t, u));
            }

            @Override
            public V apply(final T t) {
                return after.apply(GenericFunction.this.apply(t));
            }
        };
    }
}

public class CalcInterfaces {
    final private Map<String, GenericFunction<Double, Double, Double>> opsFuncs = new HashMap<>();

    void add(String operationName, Function<Double, Double> func) {
        GenericFunction<Double, Double, Double> mySum = new GenericFunction<>() {
            @Override
            public Double apply(Double a) {
                return func.apply(a);
            }

            @Override
            public Double apply(Double a, Double b) {
                return null;
            }
        };
        opsFuncs.put(operationName, mySum);
    }

    void add(String operationName, BiFunction<Double, Double, Double> biFunc) {
        GenericFunction<Double, Double, Double> mySum = new GenericFunction<>() {
            @Override
            public Double apply(Double a) {
                return null;
            }

            @Override
            public Double apply(Double a, Double b) {
                return biFunc.apply(a, b);
            }
        };
        opsFuncs.put(operationName, mySum);
    }

    void calculate(String operationName, Double a, Double b) {

        BiFunction<Double, Double, Double> biFunc = opsFuncs.get(operationName);
        if (biFunc == null || biFunc.apply(a, b) == null) {
            System.out.printf("binary operation '%s' not found\n", operationName);
            return;
        }
        System.out.printf("binary operation: '%s' on op1: %.2f  op2: %.2f, result: %.2f\n",
                operationName, a, b, biFunc.apply(a, b));
    }

    void calculate(String operationName, Double a) {

        Function<Double, Double> func = opsFuncs.get(operationName);
        if (func == null || func.apply(a) == null) {
            System.out.printf("unary operation '%s' not found\n", operationName);
            return;
        }
        System.out.printf("unary operation: %s on op: %.2f, result: %.2f\n", operationName, a, func.apply(a));
    }


    public static void main(String[] args) {

        CalcInterfaces calc = new CalcInterfaces();
        //  fill our calc with funcs:
        calc.add("sum", Double::sum);
        calc.add("sub", (a, b) -> a - b);
        calc.add("mul", (a, b) -> a * b);
        calc.add("div", (a, b) -> a / b);
        calc.add("pow", Math::pow);
        calc.add("sqrt", (a, b) -> Math.pow(a, 1.0 / b));
        //  test unary ops:
        calc.add("pow2", a -> pow(2.0, a));
        calc.add("sqrt2", Math::sqrt);
        //  binary ones:
        calc.calculate("sum", 2.1, 3.5);
        calc.calculate("div", 2.1, 1.0);
        calc.calculate("sqrt", 4.0, 0.0);
        //  operate unary ops:
        calc.calculate("pow2", 3.51);
        calc.calculate("pow2", 4.0);
        calc.calculate("sqrt2", -4.0);

        //  some additional check for incorrect funcs:
        calc.calculate("sqrt2", 4.0, 3.5);
        calc.calculate("sum",  6.9);
    }
}

