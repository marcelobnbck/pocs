package org.example;

public class GenericServiceLayer {
    // Generic Interface
    interface Service<T> {
        void save(T obj);
        T findById(int id);
    }

    // Implementation
    static class UserService implements Service<String> {
        @Override
        public void save(String user) {
            System.out.println("Saving user: " + user);
        }

        @Override
        public String findById(int id) {
            return "User" + id;
        }
    }

    public static void main(String[] args) {
        Service<String> userService = new UserService();

        // Save users
        userService.save("Marcelo");
        userService.save("Ana");

        // Find users
        String user1 = userService.findById(1);
        String user2 = userService.findById(2);

        System.out.println("Found: " + user1);
        System.out.println("Found: " + user2);
    }
}
