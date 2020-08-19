package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createSQLQuery("create table Users(" +
                    "id integer auto_increment primary key," +
                    "name varchar(255) not null," +
                    "last_name varchar(255) not null," +
                    "age integer not null);").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tx.rollback();
        } finally {
            session.close();
        }
        System.out.println("Таблица создана");
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createSQLQuery("drop table Users").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tx.rollback();
        } finally {
            session.close();
        }
        System.out.println("Таблица удалена");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createSQLQuery("insert into Users(name , last_name, age)" +
                    " values ('" + name + "', '" + lastName + "', " + age + ");").executeUpdate();
            tx.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            tx.rollback();
        } finally {
            session.close();
        }
        System.out.println("Добавлен User: " + name + "', '" + lastName + "', " + age);
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createSQLQuery("delete from Users where id = " + id).executeUpdate();
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        }
        System.out.println("Удалён User id: " + id);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            List<Object[]> objects = session.createSQLQuery("select * from Users").list();
            for (Object[] temp : objects) {
                User user = new User();

                user.setId(Long.valueOf(temp[0].toString()));
                user.setName(temp[1].toString());
                user.setLastName(temp[2].toString());
                user.setAge(Byte.valueOf(temp[3].toString()));

                list.add(user);

                System.out.println("----------------------");
                System.out.println("User id: " + user.getId());
                System.out.println("User name: " + user.getName());
                System.out.println("User lastName: " + user.getLastName());
                System.out.println("User age: " + user.getAge());
            }
            System.out.println("----------------------");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();

        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createSQLQuery("delete from Users").executeUpdate();
            System.out.println("Все User удалены");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }
    public void close() {
        Util.closeFactory();
    }
}
