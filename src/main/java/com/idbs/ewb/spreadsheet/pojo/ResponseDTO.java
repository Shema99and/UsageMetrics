package com.idbs.ewb.spreadsheet.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JSON containing All Output data given by IDBS EWB
 * 
 * @author zifo 
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseDTO {
	
	private String status;
	
	private String shortMessage;
	
	private String longMessage;
	
	private Options options;

}
