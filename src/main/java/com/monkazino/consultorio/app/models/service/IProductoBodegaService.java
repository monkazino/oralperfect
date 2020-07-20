package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.ProductoBodegaEntity;

public interface IProductoBodegaService {

	public void save(ProductoBodegaEntity productoBodegaEntity);
	
	public ProductoBodegaEntity consultarByProductoBodega(Long producto, Long bodega);

}
