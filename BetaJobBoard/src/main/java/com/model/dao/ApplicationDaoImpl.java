package com.model.dao;

import com.model.interfaces.ApplicationDao;
import com.model.beans.Application;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by michal on 29.06.15.
 */

@Repository
@Transactional
public class ApplicationDaoImpl implements ApplicationDao {


    SessionFactory sessionFactory;

    @Autowired
    public ApplicationDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createOrUpdateApplication(Application application) {

        sessionFactory.getCurrentSession().saveOrUpdate(application);
    }

    @Override
    public void deleteApplication(int id) {
        Session session = sessionFactory.getCurrentSession();
        Application application = (Application) session.createCriteria(Application.class)
                .add(Restrictions.idEq(id)).uniqueResult();

        if (application != null){
            session.delete(application);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Application> getAllApplications() {
        Session session = sessionFactory.getCurrentSession();
        List<Application> applications = session.createQuery("from Application").list();
        return applications;
    }

    @Override
    public Application getApplicationById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Application application = (Application) session.get(Application.class, id);
        return application;
    }
}
