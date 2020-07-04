package com.monkazino.consultorio.app.general.enums;

public enum TipoMovimientoInventarioEnum {
	
	ENTRADA ("E", "ENTRADA"),
	SALIDA ("S", "SALIDA");
	
	private String codigo;
	private String descripcion;
	
	private TipoMovimientoInventarioEnum (String codigo, String descripcion) {
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
