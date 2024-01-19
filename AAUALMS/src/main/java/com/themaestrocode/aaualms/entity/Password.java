package com.themaestrocode.aaualms.entity;

import java.sql.Timestamp;

public class Password {

    private int passwordId;
    private String hash;
    private String salt;
    private String status;
    private Timestamp dateCreated;

    public Password(int passwordId, String hash, String salt, String status, Timestamp dateCreated) {
        this.passwordId = passwordId;
        this.hash = hash;
        this.salt = salt;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    public int getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(int passwordId) {
        this.passwordId = passwordId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }
}
