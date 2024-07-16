package dev.juan.estevez.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private int id;
	private String name;
	private String email;
	private String phone;
	private String username;
	private String password;
	private String status;
	private String registerBy;
	private LocalDateTime registerDate;
	private LocalDateTime modificationDate;

	public User(int id, String name, String email, String phone, String username,
			String password, String status, String registerBy) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.username = username;
		this.password = password;
		this.status = status;
		this.registerBy = registerBy;
	}

}
