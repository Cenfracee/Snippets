package lk.ijse.dep.mobile.util;

public class Encryption {
    private static final int ENCRYPTION_KEY =5;//To get the +5th char
    public static String encrypt(String input) {
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = (char) (charArray[i] + ENCRYPTION_KEY);
        }
        String s = new String(charArray);
        System.out.println("Before encryption: " + input+"\nAfter encryption: "+s);
        return s;
    }

    public static String decrypt(String input) {
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = (char) (charArray[i] - ENCRYPTION_KEY);
        }
        String s = new String(charArray);
        System.out.println("Before decryption: " + input+"\nAfter decryption: "+s);
        return s;
    }
}
