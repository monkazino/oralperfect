package com.monkazino.consultorio.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.monkazino.consultorio.app.general.enums.EstadoActivoInactivoEnum;
import com.monkazino.consultorio.app.models.entity.DepartamentoEntity;
import com.monkazino.consultorio.app.models.entity.PaisEntity;
import com.monkazino.consultorio.app.models.service.IDepartamentoService;
import com.monkazino.consultorio.app.models.service.IPaisService;

@Controller
@SessionAttributes("departamentoEntity")
public class DepartamentoController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IDepartamentoService departamentoService;

	@Autowired
	private IPaisService paisService;

	@GetMapping("/parametrizacionSistema/parametrizacionGeografica/formDepartamento/pais/{pais}")
	public String crear(@PathVariable(value = "pais") Long pais, Map<String, Object> model, RedirectAttributes flash) {
		PaisEntity paisEntity = paisService.findOne(pais);
		if (paisEntity == null) {
			flash.addFlashAttribute("error", "El pais no existe en la base de datos");
			return "redirect:/parametrizacionSistema/parametrizacionGeografica/listPais";
		}
		DepartamentoEntity departamentoEntity= new DepartamentoEntity();
		departamentoEntity.setPaisEntity(paisEntity);
		departamentoEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		model.put("departamentoEntity", departamentoEntity);
		model.put("lblTituloFormDepartamento", "Departamento");
		return "parametrizacionSistema/parametrizacionGeografica/formDepartamento";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionSistema/parametrizacionGeografica/formDepartamento/departamento/{departamento}")
	public String editarDepartamento(@PathVariable(value = "departamento") Long departamento, Map<String, Object> model, RedirectAttributes flash) {
		DepartamentoEntity departamentoEntity = null;
		if (departamento > 0) {
			departamentoEntity = departamentoService.findOne(departamento);
			if (departamentoEntity == null) {
				flash.addFlashAttribute("error", "El ID del departamento no existe en la BBDD!");
				return "redirect:/parametrizacionSistema/parametrizacionGeografica/listCiudadesDepartamento";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del departamento no puede ser cero!");
			return "redirect:/parametrizacionSistema/parametrizacionGeografica/listCiudadesDepartamento";
		}
		model.put("departamentoEntity", departamentoEntity);
		model.put("lblTituloFormDepartamento", "Departamento");
		return "parametrizacionSistema/parametrizacionGeografica/formDepartamento";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/parametrizacionGeografica/formDepartamento", method = RequestMethod.POST)
	public String guardarDepartamento(@Valid DepartamentoEntity departamentoEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		int countDepartamentoCodigo = 0;
		if (result.hasErrors()) {
			return "parametrizacionSistema/parametrizacionGeografica/formDepartamento";
		}
		if (departamentoEntity.getDepartamento() == null) {
			countDepartamentoCodigo = departamentoService.consultarCountDepartamentoByCodigoPais(departamentoEntity.getCodigo().toUpperCase(), departamentoEntity.getPaisEntity().getPais());
		} else {
			countDepartamentoCodigo = departamentoService.consultarCountDepartamentoByCodigoDepartamentoPais(departamentoEntity.getCodigo().toUpperCase(), departamentoEntity.getDepartamento(), departamentoEntity.getPaisEntity().getPais());
		}
		if (countDepartamentoCodigo == 0) {
			departamentoEntity.setCodigo(departamentoEntity.getCodigo().toUpperCase());
			departamentoEntity.setDescripcion(departamentoEntity.getDescripcion().toUpperCase());
			departamentoService.save(departamentoEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:/parametrizacionSistema/parametrizacionGeografica/listDepartamentosPais/" + departamentoEntity.getPaisEntity().getPais();
		} else {
			model.put("mensajeErrorDepartamento", "El código ya se encuentra registrado");
			return "parametrizacionSistema/parametrizacionGeografica/formDepartamento";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarDepartamento/{departamento}")
	public String eliminarDepartamento(@PathVariable(value = "departamento") Long departamento, RedirectAttributes flash) {
		DepartamentoEntity departamentoEntity = null;
		Long pais = 0L;
		if (departamento > 0) {
			departamentoEntity = departamentoService.fetchByIdWithCiudades(departamento);
			if (departamentoEntity != null) {
				pais = departamentoEntity.getPaisEntity().getPais();
				if (departamentoEntity.getCiudades().isEmpty() || departamentoEntity.getCiudades() == null) {
					departamentoService.delete(departamento);
					flash.addFlashAttribute("success", "Departamento eliminado con éxito");
				} else {
					flash.addFlashAttribute("success", "Departamento no se puede eliminar, tiene asociado ciudades");
				}
			} else {
				flash.addFlashAttribute("error", "El ID del departamento no existe en la BBDD!");
			} 
		}
		return "redirect:/parametrizacionSistema/parametrizacionGeografica/listDepartamentosPais/" + pais;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/parametrizacionSistema/parametrizacionGeografica/listCiudadesDepartamento/{departamento}")
	public String listCiudadesDepartamento(@PathVariable(value = "departamento") Long departamento, Map<String, Object> model, RedirectAttributes flash) {
		DepartamentoEntity departamentoEntity = departamentoService.fetchByIdWithCiudades(departamento);
		if (departamentoEntity == null) {
			flash.addFlashAttribute("error", "El departamento no existe en la base de datos");
			return "redirect:/parametrizacionSistema/parametrizacionGeografica/listCiudadesDepartamento";
		}
		model.put("departamentoEntity", departamentoEntity);
		model.put("lblTituloDetalleDepartamento", "Ciudades: " + departamentoEntity.getDescripcion());
		return "parametrizacionSistema/parametrizacionGeografica/listCiudadesDepartamento";
	}
}
