package com.monkazino.consultorio.app.util.general;

public enum EstadoParametroEnum {
	
	ACTIVO ("A", "Activo"),
	INACTIVO ("I", "Inactivo");
	
	private String codigo;
	private String descripcion;
	
	private EstadoParametroEnum (String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
