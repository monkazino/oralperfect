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

import com.monkazino.consultorio.app.models.entity.CiudadEntity;
import com.monkazino.consultorio.app.models.entity.DepartamentoEntity;
import com.monkazino.consultorio.app.models.service.ICiudadService;
import com.monkazino.consultorio.app.models.service.IDepartamentoService;
import com.monkazino.consultorio.app.util.general.EstadoActivoInactivoEnum;

@Controller
@SessionAttributes("ciudadEntity")
public class CiudadController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ICiudadService ciudadService;

	@Autowired
	private IDepartamentoService departamentoService;

	@GetMapping("/parametrizacionGeografica/formCiudad/departamento/{departamento}")
	public String crear(@PathVariable(value = "departamento") Long departamento, Map<String, Object> model, RedirectAttributes flash) {
		DepartamentoEntity departamentoEntity = departamentoService.findOne(departamento);
		if (departamentoEntity == null) {
			flash.addFlashAttribute("error", "La ciudad no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listLocalidadesCiudad";
		}
		CiudadEntity ciudadEntity= new CiudadEntity();
		ciudadEntity.setDepartamentoEntity(departamentoEntity);
		ciudadEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		model.put("ciudadEntity", ciudadEntity);
		model.put("lblTituloFormCiudad", "Ciudad");
		return "parametrizacionGeografica/formCiudad";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionGeografica/formCiudad/ciudad/{ciudad}")
	public String editarCiudad(@PathVariable(value = "ciudad") Long ciudad, Map<String, Object> model, RedirectAttributes flash) {
		CiudadEntity ciudadEntity = null;
		if (ciudad > 0) {
			ciudadEntity = ciudadService.findOne(ciudad);
			if (ciudadEntity == null) {
				flash.addFlashAttribute("error", "El ID de la ciudad no existe en la BBDD!");
				return "redirect:/parametrizacionGeografica/listLocalidadesCiudad";
			}
		} else {
			flash.addFlashAttribute("error", "El ID de la ciudad no puede ser cero!");
			return "redirect:/parametrizacionGeografica/listLocalidadesCiudad";
		}
		model.put("ciudadEntity", ciudadEntity);
		model.put("lblTituloFormCiudad", "Ciudad");
		return "parametrizacionGeografica/formCiudad";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionGeografica/formCiudad", method = RequestMethod.POST)
	public String guardarCiudad(@Valid CiudadEntity ciudadEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		int countCiudadCodigo = 0;
		if (result.hasErrors()) {
			return "parametrizacionGeografica/formCiudad";
		}
		if (ciudadEntity.getCiudad() == null) {
			countCiudadCodigo = ciudadService.consultarCountCiudadByCodigoDepartamento(ciudadEntity.getCodigo().toUpperCase(), ciudadEntity.getDepartamentoEntity().getDepartamento());
		} else {
			countCiudadCodigo = ciudadService.consultarCountCiudadByCodigoCiudadDepartamento(ciudadEntity.getCodigo().toUpperCase(), ciudadEntity.getCiudad(), ciudadEntity.getDepartamentoEntity().getDepartamento());
		}
		if (countCiudadCodigo == 0) {
			ciudadEntity.setCodigo(ciudadEntity.getCodigo().toUpperCase());
			ciudadEntity.setDescripcion(ciudadEntity.getDescripcion().toUpperCase());
			ciudadService.save(ciudadEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:/parametrizacionGeografica/listCiudadesDepartamento/" + ciudadEntity.getDepartamentoEntity().getDepartamento();
		} else {
			model.put("mensajeErrorCiudad", "El código ya se encuentra registrado");
			return "parametrizacionGeografica/formCiudad";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarCiudad/{ciudad}")
	public String eliminarCiudad(@PathVariable(value = "ciudad") Long ciudad, RedirectAttributes flash) {
		CiudadEntity ciudadEntity = null;
		Long departamento = 0L;
		if (ciudad > 0) {
			ciudadEntity = ciudadService.fetchByIdWithLocalidades(ciudad);
			if (ciudadEntity != null) {
				departamento = ciudadEntity.getDepartamentoEntity().getDepartamento();
				if (ciudadEntity.getLocalidades().isEmpty() || ciudadEntity.getLocalidades() == null) {
					ciudadService.delete(ciudad);
					flash.addFlashAttribute("success", "Ciudad eliminado con éxito");
				} else {
					flash.addFlashAttribute("success", "Ciudad no se puede eliminar, tiene asociado localidades");
				}
			} else {
				flash.addFlashAttribute("error", "El ID del ciudad no existe en la BBDD!");
			} 
		}
		return "redirect:/parametrizacionGeografica/listCiudadesDepartamento/" + departamento;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/parametrizacionGeografica/listLocalidadesCiudad/{id}")
	public String listLocalidadesCiudad(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		CiudadEntity ciudadEntity = ciudadService.fetchByIdWithLocalidades(id);
		if (ciudadEntity == null) {
			flash.addFlashAttribute("error", "La ciudad no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listLocalidadesCiudad";
		}
		model.put("ciudadEntity", ciudadEntity);
		model.put("lblTituloDetalleCiudad", "Detalle ciudad: " + ciudadEntity.getDescripcion());
		return "parametrizacionGeografica/listLocalidadesCiudad";
	}
}
