package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

}
