package com.game.ws.game.db;

import com.game.ws.game.beans.DefaultRequestBean;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by abhishekrai on 21/10/2016.
 */
@Repository
@Transactional
public class GenericDBService implements DBService{
    private static Logger logger = Logger.getLogger(GenericDBService.class);
    @Autowired
    private SessionFactory sessionFactory;
    public List getAllRecords(Class className) {
        return sessionFactory.getCurrentSession().createCriteria(className)
                .list();
    }

    /*return the rowId of the Inserted row*/
    public int insertRecord(DefaultRequestBean request) {
        int id = (Integer) sessionFactory.getCurrentSession().save(request);
        return id;
    }

    //TODO Batch insert from Redis to DB can be done. Scheduler will  utilize the ,method
}
