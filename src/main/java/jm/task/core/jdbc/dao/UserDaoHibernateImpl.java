package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Util util = new Util();
    private SessionFactory sessionFactory = util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(20) NOT NULL, "
                + "lastName VARCHAR(20) NOT NULL, "
                + "age INT NOT NULL"
                + ")";

        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(createTableSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users";

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(dropTableSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception f) {
            if (transaction != null) {
                transaction.rollback();
            }
            f.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("from User").list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String cleanTableSQL = "DELETE FROM users";
            session.createNativeQuery(cleanTableSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
