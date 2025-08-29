package com.idbs.ewb.spreadsheet.pojo;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JSON containing All input data given by IDBS EWB
 * 
 * @author zifo
 */
@Getter
@Setter
@NoArgsConstructor
public class RequestDTO {

	private Spreadsheet spreadsheet;

	private Parent parent;

	private Action action;

	private Oauth oauth;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
