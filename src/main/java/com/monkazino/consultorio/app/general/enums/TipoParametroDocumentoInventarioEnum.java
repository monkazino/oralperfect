package com.monkazino.consultorio.app.general.enums;

public enum TipoParametroDocumentoInventarioEnum {
	
	TIPO_IDENTIFICACION ("TIPIDENT"),
	GENERO("GENERO"),
	ESTADO_CIVIL("ESTCIVIL"),
	GRUPO_SANGUINEO("GRUPSANG"),
	NIVEL_ACADEMICO("NIVACAD"),
	RAZA("RAZA"),
	OCUPACION("OCUPACION");
	
	private String codigo;
	
	private TipoParametroDocumentoInventarioEnum (String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
