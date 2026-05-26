package ru.netology.aqa.data;

import com.github.javafaker.Faker;

public class DataHelper {
    private static final Faker FAKER = new Faker();

    private DataHelper() {}

    public static UserData getValidUser() {
        return new UserData("vasya", "qwerty123");
    }

    public static UserData getInvalidUser() {
        return new UserData("vasya", FAKER.internet().password());
    }
}