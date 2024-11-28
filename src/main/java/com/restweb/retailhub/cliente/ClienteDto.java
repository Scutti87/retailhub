package com.restweb.retailhub.cliente;

import java.sql.Date;
import com.restweb.retailhub.persona.PersonaDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClienteDto extends PersonaDto {
	

	private long id;
	@NotNull
	private Date dataRegistrazione;
	

}
