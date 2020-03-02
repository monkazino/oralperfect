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

import com.monkazino.consultorio.app.models.entity.LocalidadEntity;
import com.monkazino.consultorio.app.models.entity.CiudadEntity;
import com.monkazino.consultorio.app.models.service.ILocalidadService;
import com.monkazino.consultorio.app.util.general.EstadoActivoInactivoEnum;
import com.monkazino.consultorio.app.models.service.ICiudadService;

@Controller
@SessionAttributes("localidadEntity")
public class LocalidadController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ILocalidadService localidadService;

	@Autowired
	private ICiudadService ciudadService;

	@GetMapping("/parametrizacionGeografica/formLocalidad/ciudad/{ciudad}")
	public String crear(@PathVariable(value = "ciudad") Long ciudad, Map<String, Object> model, RedirectAttributes flash) {
		CiudadEntity ciudadEntity = ciudadService.findOne(ciudad);
		if (ciudadEntity == null) {
			flash.addFlashAttribute("error", "La localidad no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listBarriosLocalidad";
		}
		LocalidadEntity localidadEntity= new LocalidadEntity();
		localidadEntity.setCiudadEntity(ciudadEntity);
		localidadEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		model.put("localidadEntity", localidadEntity);
		model.put("lblTituloFormLocalidad", "Localidad");
		return "parametrizacionGeografica/formLocalidad";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionGeografica/formLocalidad/localidad/{localidad}")
	public String editarLocalidad(@PathVariable(value = "localidad") Long localidad, Map<String, Object> model, RedirectAttributes flash) {
		LocalidadEntity localidadEntity = null;
		if (localidad > 0) {
			localidadEntity = localidadService.findOne(localidad);
			if (localidadEntity == null) {
				flash.addFlashAttribute("error", "El ID de la localidad no existe en la BBDD!");
				return "redirect:/parametrizacionGeografica/listBarriosLocalidad";
			}
		} else {
			flash.addFlashAttribute("error", "El ID de la localidad no puede ser cero!");
			return "redirect:/parametrizacionGeografica/listBarriosLocalidad";
		}
		model.put("localidadEntity", localidadEntity);
		model.put("lblTituloFormLocalidad", "Localidad");
		return "parametrizacionGeografica/formLocalidad";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionGeografica/formLocalidad", method = RequestMethod.POST)
	public String guardarLocalidad(@Valid LocalidadEntity localidadEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		int countLocalidadCodigo = 0;
		if (result.hasErrors()) {
			return "parametrizacionGeografica/formLocalidad";
		}
		if (localidadEntity.getLocalidad() == null) {
			countLocalidadCodigo = localidadService.consultarCountLocalidadByCodigoCiudad(localidadEntity.getCodigo().toUpperCase(), localidadEntity.getCiudadEntity().getCiudad());
		} else {
			countLocalidadCodigo = localidadService.consultarCountLocalidadByCodigoLocalidadCiudad(localidadEntity.getCodigo().toUpperCase(), localidadEntity.getLocalidad(), localidadEntity.getCiudadEntity().getCiudad());
		}
		if (countLocalidadCodigo == 0) {
			localidadEntity.setCodigo(localidadEntity.getCodigo().toUpperCase());
			localidadEntity.setDescripcion(localidadEntity.getDescripcion().toUpperCase());
			localidadService.save(localidadEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:/parametrizacionGeografica/listLocalidadesCiudad/" + localidadEntity.getCiudadEntity().getCiudad();
		} else {
			model.put("mensajeErrorLocalidad", "El código ya se encuentra registrado");
			return "parametrizacionGeografica/formLocalidad";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarLocalidad/{localidad}")
	public String eliminarLocalidad(@PathVariable(value = "localidad") Long localidad, RedirectAttributes flash) {
		LocalidadEntity localidadEntity = null;
		Long ciudad = 0L;
		if (localidad > 0) {
			localidadEntity = localidadService.fetchByIdWithBarrios(localidad);
			if (localidadEntity != null) {
				ciudad = localidadEntity.getCiudadEntity().getCiudad();
				if (localidadEntity.getBarrios().isEmpty() || localidadEntity.getBarrios() == null) {
					localidadService.delete(localidad);
					flash.addFlashAttribute("success", "Localidad eliminado con éxito");
				} else {
					flash.addFlashAttribute("success", "Localidad no se puede eliminar, tiene asociado localidades");
				}
			} else {
				flash.addFlashAttribute("error", "El ID del localidad no existe en la BBDD!");
			} 
		}
		return "redirect:/parametrizacionGeografica/listLocalidadesCiudad/" + ciudad;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/parametrizacionGeografica/listBarriosLocalidad/{id}")
	public String listBarriosLocalidad(@PathVariable(value = "localidad") Long localidad, Map<String, Object> model, RedirectAttributes flash) {
		LocalidadEntity localidadEntity = localidadService.fetchByIdWithBarrios(localidad);
		if (localidadEntity == null) {
			flash.addFlashAttribute("error", "La localidad no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listBarriosLocalidad";
		}
		model.put("localidadEntity", localidadEntity);
		model.put("lblTituloDetalleLocalidad", "Detalle localidad: " + localidadEntity.getDescripcion());
		return "parametrizacionGeografica/listBarriosLocalidad";
	}
}

