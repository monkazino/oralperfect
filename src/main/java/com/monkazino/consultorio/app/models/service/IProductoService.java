package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.ProductoEntity;

public interface IProductoService {

public void save(ProductoEntity productoEntity);
	
	public void delete(Long producto);
	
	public ProductoEntity findOne(Long producto);
	
	public List<ProductoEntity> findAll();
	
	public Page<ProductoEntity> findAll(Pageable pageable);

	public int consultarCountProductoByCodigo(String codigo);
	
	public int consultarCountProductoByCodigoProducto(String codigo, Long producto);

}
