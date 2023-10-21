package io.cyberflux.gateway.app.codec;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("account")
public class AccountEntity implements Serializable {
	@Id
	@Column("username")
	private String username;
	@Column("password")
	private String password;
	@Column("remarks")
	private String remarks;
	@Column("permissions")
	private String[] permissions;

	public AccountEntity desensitization() {
		this.setPassword("******");
		return this;
	}
}
