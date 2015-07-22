package com.service;

import com.model.interfaces.UserDao;
import com.model.interfaces.VerificationTokenDao;
import com.model.beans.User;
import com.model.beans.VerificationToken;
import com.utility.EmailExistsException;
import com.utility.SendEmail;
import com.utility.TokenGenerator;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by michal on 03.07.15.
 */
@Service
@Transactional
public class UserService {

    private UserDao userDao;
    private VerificationTokenDao verificationTokenDao;
    @Autowired
    private SendEmail sendEmail;


    @Autowired
    public void setVerificationTokenDao(VerificationTokenDao verificationTokenDao) {
        this.verificationTokenDao = verificationTokenDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUser(User user){
        userDao.createOrUpdateUser(user);
    }

    public void updateUser(User user){
        userDao.createOrUpdateUser(user);
    }

    public void deleteUser(int id){
        userDao.deleteUser(id);
    }

    public List<User> getUsersList(){
        return userDao.getAllUsers();
    }

    public User getUserByUsername(String username){
        return userDao.getUserByUsername(username);
    }

    public User getUserById(int id){
        return userDao.getUserById(id);
    }

    public User registerAccount(User user) throws EmailExistsException {
        if (emailExist(user.getEmail())){
            throw new EmailExistsException("There is an account with that email adress");
        }
            String password = user.getPassword();
            user.setPassword(DigestUtils.md5Hex(password));
            user.setAuthority("ROLE_NEW");
            addUser(user);

            VerificationToken token = createToken(user);
            verificationTokenDao.createOrUpdateToken(token);
            sendEmail.send(user.getEmail(), token.getToken());

        return user;
    }

    private boolean emailExist(String email){
        User user = userDao.getUserByEmail(email);
        return user != null;
    }

    private VerificationToken createToken(User user){
        String token = TokenGenerator.generateToken();
        return new VerificationToken(token, user);

    }



}
