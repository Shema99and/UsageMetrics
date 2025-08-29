package com.idbs.ewb.spreadsheet.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zifo
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ProjectPojo {
	
	private String number;
	
	private String id;

	private String description;

	private String industryPhase;

	private String compoundAssetId;

	private String mechanismOfAction;

	private String therapyType;

	private String medicalCondition;

	private String therapeuticArea;

	private String routeOfAdministration;

	private String owner;

	private String lastModified;
	
	private String projectShortDesc;

}
