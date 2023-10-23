package dev.juan.estevez.models;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
@Data
public class Client {

	private int id;
	private String name;
	private String email;
	private String phone;
	private String address;
	private String lastModification;
	private LocalDateTime creationDate;
	private LocalDateTime modificationDate;
	private List<Equipment> equipmentList;

	public Client() {
	}

	public Client(int id, String name, String email, String phone, String address, String lastModification) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.lastModification = lastModification;
	}
}
