package com.hermes.talaria.domain.gateway.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GatewayIPResponse {
	String ip;

	@Builder
	public GatewayIPResponse(String ip) {
		this.ip = ip;
	}
}
