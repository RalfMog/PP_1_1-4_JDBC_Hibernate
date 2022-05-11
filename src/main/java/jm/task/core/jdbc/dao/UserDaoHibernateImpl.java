package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

import static jm.task.core.jdbc.util.Util.sessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;
    private Transaction transaction;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS USER (" +
                    "ID bigint PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
                    "NAME varchar(64) NOT NULL,\n" +
                    "LASTNAME varchar(100) NOT NULL,\n" +
                    "AGE tinyint NOT NULL)").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            session.createSQLQuery("DROP TABLE IF EXISTS USER").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            users = session.createQuery("from User")
                    .getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            session.createSQLQuery("truncate table User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
}
