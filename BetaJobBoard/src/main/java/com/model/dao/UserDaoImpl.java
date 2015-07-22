package com.model.dao;


import com.model.beans.User;
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
public class UserDaoImpl implements com.model.interfaces.UserDao {


    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createOrUpdateUser(User user){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }


    @Override
    public void deleteUser(int id){
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.createCriteria(User.class)
                .add(Restrictions.idEq(id)).uniqueResult();

        if (user != null){
            session.delete(user);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers(){
        Session session = sessionFactory.getCurrentSession();
        List<User> users = session.createQuery("from User").list();
        return users;
    }

    @Override
    public User getUserById(int id){
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.createQuery("select u from User u where u.email = :email")
                .setString("email", email)
                .uniqueResult();
        return user;
    }

    @Override
    public User getUserByUsername(String username){
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.createQuery("select u from User u where u.username = :username")
                .setString("username", username)
                .uniqueResult();
        return user;
    }
}
