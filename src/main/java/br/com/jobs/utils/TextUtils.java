package br.com.jobs.utils;

public class TextUtils {
    public static String color(String msg) {
        return msg == null ? "" : msg.replace("&", "§");
    }
}