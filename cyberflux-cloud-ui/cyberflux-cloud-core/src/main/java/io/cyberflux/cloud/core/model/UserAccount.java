package io.cyberflux.cloud.core.model;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "admin_account")
public class UserAccount implements Serializable {

    @Id
	@Column(name = "secret_id")
    private String secretId;

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "permission")
    private String permission;


    public UserAccount(String secretId, String secretKey, String permission) {
		this.secretId = secretId;
		this.secretKey = secretKey;
		this.permission = permission;
	}

	public String getSecretId() {
        return secretId;
    }
    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }
    public String getSecretKey() {
        return secretKey;
    }
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
}