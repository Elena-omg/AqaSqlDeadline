package ru.netology.aqa.data;

public class DataHelper {
    private DataHelper() {}

    public static String getValidLogin() {
        return "vasya";
    }

    public static String getValidPassword() {
        return "password";
    }

    public static String getInvalidPassword() {
        return "wrongpass";
    }
}