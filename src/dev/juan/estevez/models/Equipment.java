package dev.juan.estevez.models;

import java.util.Objects;

public class Equipment {

	private int equipmentID;
	private int clientID;
	private String equipmentType;
	private String mark;
	private String model;
	private String serialNumber;
	private String admissionDay;
	private String admissionMonth;
	private String admissionYear;
	private String observation;
	private String status;
	private String lastModification;
	private String technicalComments;
	private String technicalRevisionOf;

	public Equipment() {
	}

	public Equipment(int equipmentID, int clientID, String equipmentType, String mark, String model,
			String serialNumber, String admissionDay, String admissionMonth, String admissionYear, String observation,
			String status, String lastModification, String technicalComments, String technicalRevisionOf) {
		super();
		this.equipmentID = equipmentID;
		this.clientID = clientID;
		this.equipmentType = equipmentType;
		this.mark = mark;
		this.model = model;
		this.serialNumber = serialNumber;
		this.admissionDay = admissionDay;
		this.admissionMonth = admissionMonth;
		this.admissionYear = admissionYear;
		this.observation = observation;
		this.status = status;
		this.lastModification = lastModification;
		this.technicalComments = technicalComments;
		this.technicalRevisionOf = technicalRevisionOf;
	}

	public int getEquipmentID() {
		return equipmentID;
	}

	public void setEquipmentID(int equipmentID) {
		this.equipmentID = equipmentID;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAdmissionDay() {
		return admissionDay;
	}

	public void setAdmissionDay(String admissionDay) {
		this.admissionDay = admissionDay;
	}

	public String getAdmissionMonth() {
		return admissionMonth;
	}

	public void setAdmissionMonth(String admissionMonth) {
		this.admissionMonth = admissionMonth;
	}

	public String getAdmissionYear() {
		return admissionYear;
	}

	public void setAdmissionYear(String admissionYear) {
		this.admissionYear = admissionYear;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastModification() {
		return lastModification;
	}

	public void setLastModification(String lastModification) {
		this.lastModification = lastModification;
	}

	public String getTechnicalComments() {
		return technicalComments;
	}

	public void setTechnicalComments(String technicalComments) {
		this.technicalComments = technicalComments;
	}

	public String getTechnicalRevisionOf() {
		return technicalRevisionOf;
	}

	public void setTechnicalRevisionOf(String technicalRevisionOf) {
		this.technicalRevisionOf = technicalRevisionOf;
	}

	@Override
	public int hashCode() {
		return Objects.hash(admissionDay, admissionMonth, admissionYear, clientID, equipmentID, equipmentType,
				lastModification, mark, model, observation, serialNumber, status, technicalComments,
				technicalRevisionOf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipment other = (Equipment) obj;
		return Objects.equals(admissionDay, other.admissionDay) && Objects.equals(admissionMonth, other.admissionMonth)
				&& Objects.equals(admissionYear, other.admissionYear) && clientID == other.clientID
				&& equipmentID == other.equipmentID && Objects.equals(equipmentType, other.equipmentType)
				&& Objects.equals(lastModification, other.lastModification) && Objects.equals(mark, other.mark)
				&& Objects.equals(model, other.model) && Objects.equals(observation, other.observation)
				&& Objects.equals(serialNumber, other.serialNumber) && Objects.equals(status, other.status)
				&& Objects.equals(technicalComments, other.technicalComments)
				&& Objects.equals(technicalRevisionOf, other.technicalRevisionOf);
	}

	@Override
	public String toString() {
		return "Equipment [equipmentID=" + equipmentID + ", clientID=" + clientID + ", equipmentType=" + equipmentType
				+ ", mark=" + mark + ", model=" + model + ", serialNumber=" + serialNumber + ", admissionDay="
				+ admissionDay + ", admissionMonth=" + admissionMonth + ", admissionYear=" + admissionYear
				+ ", observation=" + observation + ", status=" + status + ", lastModification=" + lastModification
				+ ", technicalComments=" + technicalComments + ", technicalRevisionOf=" + technicalRevisionOf + "]";
	}

}
