package com.monkazino.consultorio.app.util.general;

public enum TipoParametroProductoEnum {
	
	TIPO_PRODUCTO ("TIPO_PRODUCTO"),
	CATEGORIA("CATEGORIA"),
	MARCA("MARCA");
	
	private String codigo;
	
	private TipoParametroProductoEnum (String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
