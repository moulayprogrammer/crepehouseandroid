package com.moulay.krepehouse.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Vendor implements Serializable {

    // Add this line to explicitly define serialVersionUID
    private static final long serialVersionUID = 1L;  // Can be any long value
    private static Vendor instance;



    private int uniqueId;
    private String name;
    private String phone;
    private String username;
    private String password;
    private int archive;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Vendor() {
    }

    private Vendor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Vendor getInstance() {
        synchronized (Vendor.class){
            if (instance == null) instance = new Vendor();
        }
        return instance;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getArchive() {
        return archive;
    }

    public void setArchive(int archive) {
        this.archive = archive;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "uniqueId=" + uniqueId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", archive=" + archive +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
