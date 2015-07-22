package com.model.interfaces;

import com.model.beans.Ad;

import java.util.List;

/**
 * Created by michal on 29.06.15.
 */
public interface AdDao {

        void createOrUpdateAd(Ad ad);

        void deleteAd(int id);

        List<Ad> getAllAds();

        Ad getAdById(int id);

}
