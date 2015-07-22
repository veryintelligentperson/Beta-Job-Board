package com.model.dao;

import com.model.interfaces.VerificationTokenDao;
import com.model.beans.VerificationToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by michal on 04.07.15.
 */
@Repository
@Transactional
public class VerificationTokenDaoImpl implements VerificationTokenDao {



    private SessionFactory sessionFactory;

    @Autowired
    public VerificationTokenDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void createOrUpdateToken(VerificationToken token){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(token);
    }


    @Override
    public void deleteToken(int id){
        Session session = sessionFactory.getCurrentSession();
        VerificationToken token = (VerificationToken) session.createCriteria(VerificationToken.class)
                .add(Restrictions.idEq(id)).uniqueResult();

        if (token != null){
            session.delete(token);
        }
    }

    @Override
    public VerificationToken getTokenById(int id){
        Session session = sessionFactory.getCurrentSession();
        VerificationToken token = (VerificationToken) session.get(VerificationToken.class, id);
        return token;
    }

    @Override
    public VerificationToken getVerificationTokenByToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        VerificationToken verificationToken =
                (VerificationToken) session.createQuery("select u from VerificationToken u where u.token = :token")
                .setString("token", token)
                .uniqueResult();
        return verificationToken;
    }

}
