package com.monkazino.consultorio.app.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monkazino.consultorio.app.models.dao.IProductoDao;
import com.monkazino.consultorio.app.models.entity.ProductoEntity;
import com.monkazino.consultorio.app.models.service.IProductoService;

@Service
public class ProductoService implements IProductoService {

	@Autowired
	private IProductoDao productoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ProductoEntity> findAll() {
		return (List<ProductoEntity>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProductoEntity> findAll(Pageable pageable) {
		return productoDao.findAll(pageable);
	}
	
	@Override
	@Transactional
	public void save(ProductoEntity productoEntity) {
		productoDao.save(productoEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public ProductoEntity findOne(Long producto) {
		return productoDao.findById(producto).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long producto) {
		productoDao.deleteById(producto);

	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountProductoByCodigo(String codigo) {
		return productoDao.consultarCountProductoByCodigo(codigo);
	}

	@Override
	@Transactional(readOnly = true)
	public int consultarCountProductoByCodigoProducto(String codigo, Long producto) {
		return productoDao.consultarCountProductoByCodigoProducto(codigo, producto);
	}
}
