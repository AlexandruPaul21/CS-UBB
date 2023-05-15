package org.example.persistance.orm;

import org.example.Agency;
import org.example.repository.IAgencyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class AgencyOrmRepository implements IAgencyRepository {
    static SessionFactory sessionFactory;
    static void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

    public AgencyOrmRepository() {
        initialize();
    }

    @Override
    public Agency findOne(String s) {
        return null;
    }

    @Override
    public Iterable<Agency> findAll() {
        return null;
    }

    @Override
    public Agency save(Agency entity) {
        return null;
    }

    @Override
    public boolean existsUser(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Agency agency = session.createQuery("from Agency where id = :username and password = :password", Agency.class)
                        .setParameter("username", username)
                        .setParameter("password", password)
                        .uniqueResult();
                tx.commit();
                return agency != null;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return false;
    }
}
