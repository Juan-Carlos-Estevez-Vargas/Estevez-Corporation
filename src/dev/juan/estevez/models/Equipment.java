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
public class Equipment {

	private int id;
	private int clientId;
	private String type;
	private String mark;
	private String model;
	private String serialNumber;
	private LocalDateTime admissionDate;
	private LocalDateTime modificationDate;
	private String observations;
	private String status;
	private String lastModification;
	private String technicalComments;
	private String technicalRevisionOf;

	public Equipment(int id, int clientId, String type, String mark, String model,
			String serialNumber, LocalDateTime admissionDate, String observations, String status,
			String lastModification, String technicalComments, String technicalRevisionOf) {
		this.id = id;
		this.clientId = clientId;
		this.type = type;
		this.mark = mark;
		this.model = model;
		this.serialNumber = serialNumber;
		this.admissionDate = admissionDate;
		this.observations = observations;
		this.status = status;
		this.lastModification = lastModification;
		this.technicalComments = technicalComments;
		this.technicalRevisionOf = technicalRevisionOf;
	}

}
