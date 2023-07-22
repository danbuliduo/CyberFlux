package io.cyberflux.cloud.core.model;



import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "admin_account")
public class AdminAccountModel implements Serializable {

    @Id
	@Column("secret_id")
    private String secretId;

    @Column("secret_key")
    private String secretKey;

    @Column("permission")
    private String permission;

    public AdminAccountModel(String secretId, String secretKey, String permission) {
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