package org.example;

import java.util.HashMap;
import java.util.Map;

public class GenericServiceLayer {
    interface Service<T> {
        void save(int id, T obj);
        T findById(int id);
    }

    static class UserService implements Service<String> {
        private Map<Integer, String> database = new HashMap<>();

        @Override
        public void save(int id, String user) {
            database.put(id, user);
            System.out.println("Saving user: " + user);
        }

        @Override
        public String findById(int id) {
            return database.get(id);
        }
    }

    public static void main(String[] args) {
        UserService userService = new UserService();

        userService.save(1, "Marcelo");
        userService.save(2, "Ana");

        System.out.println("Found: " + userService.findById(1));
        System.out.println("Found: " + userService.findById(2));
    }
}