package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.ITipoParametroProductoDao;
import com.monkazino.consultorio.app.models.entity.TipoParametroProductoEntity;
import com.monkazino.consultorio.app.models.service.ITipoParametroProductoService;

@Service
public class TipoParametroProductoServiceImpl implements ITipoParametroProductoService {

	@Autowired
	private ITipoParametroProductoDao tipoParametroProductoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoParametroProductoEntity> findAll() {
		return (List<TipoParametroProductoEntity>) tipoParametroProductoDao.findAll();
	}

	@Override
	@Transactional
	public void save(TipoParametroProductoEntity tipoParametroProductoEntity) {
		tipoParametroProductoDao.save(tipoParametroProductoEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public TipoParametroProductoEntity findOne(Long tipoParametroProducto) {
		return tipoParametroProductoDao.findById(tipoParametroProducto).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long tipoParametroProducto) {
		tipoParametroProductoDao.deleteById(tipoParametroProducto);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<TipoParametroProductoEntity> findAll(Pageable pageable) {
		return tipoParametroProductoDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoParametroProductoEntity fetchByIdWithParametrosProducto(Long tipoParametroProducto) {
		return tipoParametroProductoDao.fetchByIdWithParametrosProducto(tipoParametroProducto);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountTipoParametroProductoByCodigo(String codigo) {
		return tipoParametroProductoDao.consultarCountTipoParametroProductoByCodigo(codigo);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountTipoParametroProductoByCodigoTipoParametroProducto(String codigo, Long tipoParametroProducto) {
		return tipoParametroProductoDao.consultarCountTipoParametroProductoByCodigoTipoParametroProducto(codigo, tipoParametroProducto);
	}

}
