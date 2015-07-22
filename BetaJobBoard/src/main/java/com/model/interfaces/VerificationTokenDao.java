package com.model.interfaces;

import com.model.beans.VerificationToken;

/**
 * Created by michal on 04.07.15.
 */
public interface VerificationTokenDao {
    void createOrUpdateToken(VerificationToken token);

    void deleteToken(int id);

    VerificationToken getTokenById(int id);

    VerificationToken getVerificationTokenByToken(String token);
}
