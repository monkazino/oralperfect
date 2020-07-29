package com.monkazino.consultorio.app.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IProductoBodegaDao;
import com.monkazino.consultorio.app.models.entity.ProductoBodegaEntity;
import com.monkazino.consultorio.app.models.service.IProductoBodegaService;

@Service
public class ProductoBodegaService implements IProductoBodegaService {

	@Autowired
	private IProductoBodegaDao productoBodegaDao;
	
	@Override
	@Transactional
	public void save(ProductoBodegaEntity productoBodegaEntity) {
		productoBodegaDao.save(productoBodegaEntity);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ProductoBodegaEntity consultarByProductoBodega(Long producto, Long bodega) {
		return productoBodegaDao.consultarByProductoBodega(producto, bodega);
	}

}
