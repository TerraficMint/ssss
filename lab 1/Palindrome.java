public class Palindrome {
    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            // разбитие строки слов на отдельные слова
            String word = args[i];
            boolean temp = isPalindrome(word);
            // Вывод
            System.out.println(temp);
        }

    }

    // метод отзеркаливания поступающего слова
    public static String reverseString(String str) {
        String s = "";
        // в цикле буквы записываются в обратном порядке
        for (int i = str.length(); i > 0; i--) {
            s += str.charAt(i - 1);
        }
        return s;
    }

    // метод проверки идентичности отзеркаленного в reverseString слова исходному
    public static boolean isPalindrome(String str) {
        String s2 = reverseString(str);
        return s2.equals(str);
    }
}