package com.model.dao;

import com.model.interfaces.AdDao;
import com.model.beans.Ad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by michal on 29.06.15.
 */
@Repository
@Transactional
public class AdDaoImpl implements AdDao{

    private SessionFactory sessionFactory;

    static Logger logger = LoggerFactory.getLogger(AdDaoImpl.class);

    @Autowired
    public AdDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createOrUpdateAd(Ad ad) {
        sessionFactory.getCurrentSession().saveOrUpdate(ad);
    }

    @Override
    public void deleteAd(int id) {
        Session session = sessionFactory.getCurrentSession();
        Ad ad = (Ad) session.createCriteria(Ad.class)
                .add(Restrictions.idEq(id)).uniqueResult();

        if(ad != null){
            session.delete(ad);
        }
    }

    @Override
    public List<Ad> getAllAds() {
        Session session = sessionFactory.getCurrentSession();
        List<Ad> ads = session.createQuery("from Ad").list();

        return ads;
    }

    @Override
    public Ad getAdById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Ad ad = (Ad) session.get(Ad.class, id);
        return ad;
    }

}
