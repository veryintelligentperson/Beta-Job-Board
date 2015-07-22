package com.model.interfaces;


import com.model.beans.Application;

import java.util.List;

/**
 * Created by michal on 29.06.15.
 */
public interface ApplicationDao {

    void createOrUpdateApplication(Application application);

    void deleteApplication(int id);

    List<Application> getAllApplications();

    Application getApplicationById(int id);
}
