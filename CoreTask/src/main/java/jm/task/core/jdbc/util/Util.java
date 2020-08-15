package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static SessionFactory sessionFactory;

    public static Connection getMyConnection() throws SQLException {
        String hostName = "localhost";
        String dbName = "mydtb";
        String userName = "root";
        String password = "QNrwLztz21";

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?useSSL=false";

        return DriverManager.getConnection(connectionURL, userName, password);
    }
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().addResource("hibernate.cfg.xml").configure();
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

    public static void closeFactory() {
        if(!sessionFactory.isClosed()) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}
