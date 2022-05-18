package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getInstance().getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                session.createSQLQuery("""
                        CREATE TABLE IF NOT EXISTS USER (ID bigint PRIMARY KEY AUTO_INCREMENT NOT NULL,
                        NAME varchar(64) NOT NULL,
                        LASTNAME varchar(100) NOT NULL,
                        AGE tinyint NOT NULL)""").executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                session.createSQLQuery("DROP TABLE IF EXISTS USER").executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                session.save(new User(name, lastName, age));
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                User user = session.get(User.class, id);
                session.delete(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                users = session.createQuery("from User")
                        .getResultList();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                session.createSQLQuery("truncate table User").executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
    }
}
