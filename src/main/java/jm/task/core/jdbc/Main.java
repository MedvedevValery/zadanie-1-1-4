package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
                userService.createUsersTable();
                userService.createUsersTable();
        //        // Users add
             userService.saveUser("Petr I", "Petrov", (byte) 15);
                User user1 = userService.getAllUsers()
                    .get(
                        userService.getAllUsers().size() - 1
                    );
                System.out.println("User с именем - "
                   + user1.getName()
                    + " добавлен в базу данных");
                userService.saveUser("Pavel I", "Petrov", (byte) 21);
                User user2 = userService.getAllUsers()
                    .get(
                        userService.getAllUsers().size() - 1
                    );
                System.out.println("User с именем - "
                    + user2.getName()
                    + " добавлен в базу данных");
                userService.saveUser("Pavel II", "Pavlov", (byte) 51);
                User user3 = userService.getAllUsers()
                    .get(
                        userService.getAllUsers().size() - 1
                    );
                System.out.println("User с именем - "
                    + user3.getName()
                    + " добавлен в базу данных");
                userService.saveUser("Petr II", "Pavlov", (byte) 36);
                User user4 = userService.getAllUsers()
                    .get(
                        userService.getAllUsers().size() - 1
                    );
                System.out.println("User с именем - "
                    + user4.getName()
                    + " добавлен в базу данных");
        //
        //        //Get all users
                System.out.println(userService.getAllUsers());
        //
        //        // Table cleansing
                userService.cleanUsersTable();
                System.out.println("Размер списка Users после очистки - "
                    + userService.getAllUsers().size());
        //
        //        // Table drop
                userService.dropUsersTable();
        ////        Will throw exception
                System.out.println(userService.getAllUsers());
    }
}
