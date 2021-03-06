package com.monkazino.consultorio.app.general.enums;

public enum EstadoActivoInactivoEnum {
	
	ACTIVO ("A", "Activo"),
	INACTIVO ("I", "Inactivo");
	
	private String codigo;
	private String descripcion;
	
	private EstadoActivoInactivoEnum (String codigo, String descripcion) {
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
