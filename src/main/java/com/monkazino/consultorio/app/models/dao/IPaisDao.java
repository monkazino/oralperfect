package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.monkazino.consultorio.app.models.entity.PaisEntity;

public interface IPaisDao extends PagingAndSortingRepository<PaisEntity, Long> {
	
	@Query("select p from PaisEntity p left join fetch p.departamentos d where p.id=?1")
	public PaisEntity fetchByIdWithDepartamentos(Long id);

}
