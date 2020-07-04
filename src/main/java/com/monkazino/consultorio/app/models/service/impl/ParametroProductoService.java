package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.general.enums.EstadoActivoInactivoEnum;
import com.monkazino.consultorio.app.models.dao.IParametroProductoDao;
import com.monkazino.consultorio.app.models.entity.ParametroProductoEntity;
import com.monkazino.consultorio.app.models.service.IParametroProductoService;

@Service
public class ParametroProductoService implements IParametroProductoService {

	@Autowired
	private IParametroProductoDao parametroProductoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ParametroProductoEntity> findAll() {
		return (List<ParametroProductoEntity>) parametroProductoDao.findAll();
	}

	@Override
	@Transactional
	public void save(ParametroProductoEntity parametroProductoEntity) {
		parametroProductoDao.save(parametroProductoEntity);

	}

	@Override
	@Transactional(readOnly = true)
	public ParametroProductoEntity findOne(Long parametroProducto) {
		return parametroProductoDao.findById(parametroProducto).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long parametroProducto) {
		parametroProductoDao.deleteById(parametroProducto);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<ParametroProductoEntity> findAll(Pageable pageable) {
		return parametroProductoDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ParametroProductoEntity> consultarParametrosProductoTipoParametroProducto(String codigoTipoParametroProducto) {
		return (List<ParametroProductoEntity>) parametroProductoDao.consultarParametrosProductoTipoParametroProducto(codigoTipoParametroProducto, EstadoActivoInactivoEnum.ACTIVO.getCodigo());
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountParametroProductoByCodigoTipoParametroProducto(String codigo, Long tipoParametroProducto) {
		return parametroProductoDao.consultarCountParametroProductoByCodigoTipoParametroProducto(codigo, tipoParametroProducto);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountParametroProductoByCodigoParametroProductoTipoParametroProducto(String codigo, Long parametroProducto, Long tipoParametroProducto) {
		return parametroProductoDao.consultarCountParametroProductoByCodigoParametroProductoTipoParametroProducto(codigo, parametroProducto, tipoParametroProducto);
	}

}
