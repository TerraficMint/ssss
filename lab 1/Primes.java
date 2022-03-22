import java.lang.System;

public class Primes {
    public static void main(String[] args) {
        // цикл чисел от 0 до 100;
        for (int i = 0; i <= 100; i++) {
            // вызов метода проверки на простоту числа
            if (isPrime(i))
                // вывод простых чисел
                System.out.println(i);
        }

    }

    public static boolean isPrime(int n) {
        if (n != 2) {
            for (int j = 2; j < n; j++) {
                // если число делится на какое-то число до него,
                // то число не является простым
                if (n % j == 0)
                    return false;
            }
            return true;
        } else
            return false;
    }

}