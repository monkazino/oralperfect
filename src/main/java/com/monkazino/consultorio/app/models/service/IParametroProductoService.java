package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.ParametroProductoEntity;

public interface IParametroProductoService {

	public void save(ParametroProductoEntity parametroProductoEntity);
	
	public void delete(Long parametroProducto);
	
	public ParametroProductoEntity findOne(Long parametroProducto);
	
	public List<ParametroProductoEntity> findAll();
	
	public Page<ParametroProductoEntity> findAll(Pageable pageable);

	public List<ParametroProductoEntity> consultarParametrosProductoTipoParametroProducto(String codigoTipoParametroProducto);

	public int consultarCountParametroProductoByCodigoTipoParametroProducto(String codigo, Long tipoParametroProducto);
	
	public int consultarCountParametroProductoByCodigoParametroProductoTipoParametroProducto(String codigo, Long parametroProducto, Long tipoParametroProducto);
	
}
