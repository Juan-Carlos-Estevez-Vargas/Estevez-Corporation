package dev.juan.estevez.models;

import java.util.Objects;

public class Cliente {

	private int clientID;
	private String clientName;
	private String clientEmail;
	private String clientPhone;
	private String clientAddress;
	private String lastModification;

	public Cliente() {
	}

	public Cliente(int clientID, String clientName, String clientEmail, String clientPhone, String clientAddress,
			String lastModification) {
		super();
		this.clientID = clientID;
		this.clientName = clientName;
		this.clientEmail = clientEmail;
		this.clientPhone = clientPhone;
		this.clientAddress = clientAddress;
		this.lastModification = lastModification;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public String getClientPhone() {
		return clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getLastModification() {
		return lastModification;
	}

	public void setLastModification(String lastModification) {
		this.lastModification = lastModification;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientAddress, clientEmail, clientID, clientName, clientPhone, lastModification);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(clientAddress, other.clientAddress) && Objects.equals(clientEmail, other.clientEmail)
				&& clientID == other.clientID && Objects.equals(clientName, other.clientName)
				&& Objects.equals(clientPhone, other.clientPhone)
				&& Objects.equals(lastModification, other.lastModification);
	}

	@Override
	public String toString() {
		return "Cliente [clientID=" + clientID + ", clientName=" + clientName + ", clientEmail=" + clientEmail
				+ ", clientPhone=" + clientPhone + ", clientAddress=" + clientAddress + ", lastModification="
				+ lastModification + "]";
	}

}
