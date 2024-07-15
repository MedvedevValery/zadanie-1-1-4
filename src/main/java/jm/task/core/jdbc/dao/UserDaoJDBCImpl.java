package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.boot.Metadata;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
      private Util dbUtil = new Util();
    private Connection conn = dbUtil.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String creator = "CREATE TABLE IF NOT EXISTS users ("
            + "id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, "
            + "name VARCHAR(20) NOT NULL, "
            + "lastName VARCHAR(20) NOT NULL, "
            + "age SMALLINT NOT NULL"
            + ")";

        try (Statement stat = conn.createStatement()) {
            stat.executeUpdate(creator);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public void dropUsersTable() {
        String tableName = "users";
        String drop = "DROP TABLE " + tableName;
        try {
            DatabaseMetaData dbMetaD = conn.getMetaData();
            ResultSet rs = dbMetaD.getTables(null, null, tableName, new String[] {"TABLE"});
            if (rs.next()) {
                try (Statement stat = conn.createStatement()) {
                    stat.execute(drop);
                }
            }
       }
         catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users "
            + "(name, lastName, age) "
            + "VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
           ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setLong(1, id);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Statement stat = conn.createStatement()) {
            ResultSet result = stat.executeQuery(sql);
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastName"));
                user.setAge(result.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String sqlClean = "DELETE FROM users";
        try (Statement stat = conn.createStatement()) {
            stat.executeUpdate(sqlClean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
