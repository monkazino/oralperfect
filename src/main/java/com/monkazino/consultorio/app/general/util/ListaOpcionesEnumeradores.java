package com.monkazino.consultorio.app.general.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.monkazino.consultorio.app.general.enums.EstadoActivoInactivoEnum;
import com.monkazino.consultorio.app.general.enums.TipoMovimientoInventarioEnum;

public class ListaOpcionesEnumeradores {
	
	@ModelAttribute("listTipoMovimientoInventario")
	public static Map<String, String> getListTipoMovimientoInventario() {
		Map<String, String> listTipoMovimientoInventario = new HashMap<String, String>();
		listTipoMovimientoInventario.put(TipoMovimientoInventarioEnum.ENTRADA.getCodigo(), TipoMovimientoInventarioEnum.ENTRADA.getDescripcion());
		listTipoMovimientoInventario.put(TipoMovimientoInventarioEnum.SALIDA.getCodigo(), TipoMovimientoInventarioEnum.SALIDA.getDescripcion());
		return listTipoMovimientoInventario;
	}
	
	@ModelAttribute("listTipoMovimientoInventario")
	public static Map<String, String> getListEstado() {
		Map<String, String> listEstado = new HashMap<String, String>();
		listEstado.put(EstadoActivoInactivoEnum.ACTIVO.getCodigo(), EstadoActivoInactivoEnum.ACTIVO.getDescripcion());
		listEstado.put(EstadoActivoInactivoEnum.INACTIVO.getCodigo(), EstadoActivoInactivoEnum.INACTIVO.getDescripcion());
		return listEstado;
	}
}
