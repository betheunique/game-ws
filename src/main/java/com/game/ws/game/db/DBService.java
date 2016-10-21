package com.game.ws.game.db;

import com.game.ws.game.beans.DefaultRequestBean;

import java.util.List;

/**
 * Created by abhishekrai on 21/10/2016.
 */
public interface DBService {
    public List getAllRecords(Class className);
    public int insertRecord(DefaultRequestBean requestBean);
}
