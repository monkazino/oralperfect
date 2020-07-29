package com.monkazino.consultorio.app.models.service;

import com.monkazino.consultorio.app.models.entity.ProductoBodegaEntity;

public interface IProductoBodegaService {

	public void save(ProductoBodegaEntity productoBodegaEntity);
	
	public ProductoBodegaEntity consultarByProductoBodega(Long producto, Long bodega);

}
