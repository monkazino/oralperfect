package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.TipoDocumentoInventarioEntity;

public interface ITipoDocumentoInventarioService {

	public void save(TipoDocumentoInventarioEntity tipoDocumentoInventarioEntity);
	
	public void delete(Long tipoParametro);
	
	public TipoDocumentoInventarioEntity findOne(Long tipoDocumentoInventario);
	
	public List<TipoDocumentoInventarioEntity> findAll();
	
	public Page<TipoDocumentoInventarioEntity> findAll(Pageable pageable);

	public int consultarCountTipoDocumentoInventarioByCodigo(String codigo);
	
	public int consultarCountTipoDocumentoInventarioByCodigoTipoDocumentoInventario(String codigo, Long tipoDocumentoInventario);
	
}
