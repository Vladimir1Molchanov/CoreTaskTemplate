package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sf = Util.getSessionFactory();
    Transaction tx;

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(20) NOT NULL,\n" +
                    "  `lastname` VARCHAR(20) NOT NULL,\n" +
                    "  `age` INT(3) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `idusers_UNIQUE` (`id` ASC));")
                    .addEntity(User.class).
                    executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.out.println("Не смог содать таблицу, check ->");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery("drop table if exists users")
                    .executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.out.println("Не смог дропнуть таблицу, check ->");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            tx.rollback();
            System.out.println("Не смог сохранить пользователя, check ->");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.delete(session.get(User.class, id));
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.out.println("Не смог удалить пользователя, check ->");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            result = session.createQuery("from User", User.class).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.out.println("Не смог получить данные пользователей, check ->");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery("truncate users").executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.out.println("Не смог очистить данные пользователей, check ->");
            e.printStackTrace();
        }
    }
}
