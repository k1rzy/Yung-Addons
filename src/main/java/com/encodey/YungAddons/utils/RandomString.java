package com.encodey.YungAddons.utils;

import java.util.*;

public class RandomString
{
    public static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();;
    public static final int alphabetLength = RandomString.alphabet.length;;

    public static String nextString(final int lenght, final Random random) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lenght; ++i) {
            sb.append(RandomString.alphabet[random.nextInt(RandomString.alphabet.length)]);
        }
        return sb.toString();
    }

    public static String nextString(final int lenght) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lenght; ++i) {
            sb.append(RandomString.alphabet[random.nextInt(RandomString.alphabet.length)]);
        }
        return sb.toString();
    }
}
