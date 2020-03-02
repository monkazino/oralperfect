package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.TipoParametroProductoEntity;

public interface ITipoParametroProductoService {

	public void save(TipoParametroProductoEntity tipoParametroProductoEntity);
	
	public void delete(Long tipoParametro);
	
	public TipoParametroProductoEntity findOne(Long tipoParametro);
	
	public List<TipoParametroProductoEntity> findAll();
	
	public Page<TipoParametroProductoEntity> findAll(Pageable pageable);

	public TipoParametroProductoEntity fetchByIdWithParametrosProducto(Long tipoParametroProducto);

	public int consultarCountTipoParametroProductoByCodigo(String codigo);
	
	public int consultarCountTipoParametroProductoByCodigoTipoParametroProducto(String codigo, Long tipoParametroProducto);
	
}
