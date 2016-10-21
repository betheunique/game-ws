package com.game.ws.game.access;

import com.game.ws.game.beans.DefaultRequestBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;


/**
 * Created by abhishekrai on 21/10/2016.
 */
@Entity
@Table(name="g_session_state")
public class SessionIDAccess extends DefaultRequestBean{

    @Id
    @GeneratedValue
    private int id;

    @Column(name="session_id")
    private String sessionId;

    @Column(name="total_value")
    private String totalValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "SessionIDAccess [ id="+ id + "sessionId="+ sessionId + "totalValue=" + totalValue + "]";
    }
}
