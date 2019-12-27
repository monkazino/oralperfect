package com.monkazino.consultorio.app.controllers;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
@SessionAttributes("ciudadEntity")
public class CiudadController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ICiudadService ciudadService;

	@Autowired
	private IDepartamentoService departamentoService;

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

	@GetMapping("/parametrizacionGeografica/formCiudad/departamento/{departamento}")
	public String crear(@PathVariable(value = "departamento") Long departamento, Map<String, Object> model,
			RedirectAttributes flash) {

		DepartamentoEntity departamentoEntity = departamentoService.findOne(departamento);

		if (departamentoEntity == null) {
			flash.addFlashAttribute("error", "La ciudad no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listarDepartamento";
		}

		CiudadEntity ciudadEntity= new CiudadEntity();
		ciudadEntity.setDepartamentoEntity(departamentoEntity);
		
		model.put("ciudadEntity", ciudadEntity);
		model.put("lblTituloFormCiudad", "Ciudad");
		model.put("lblBotonGuardar", "Guardar");
		return "parametrizacionGeografica/formCiudad";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionGeografica/formCiudad/ciudad/{id}")
	public String editarCiudad(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		CiudadEntity ciudadEntity = null;

		if (id > 0) {
			ciudadEntity = ciudadService.findOne(id);
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
		model.put("lblBotonGuardar", "Guardar");
		return "parametrizacionGeografica/formCiudad";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionGeografica/formCiudad", method = RequestMethod.POST)
	public String guardarCiudad(@Valid CiudadEntity ciudadEntity, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Ciudad");
			model.addAttribute("lblBotonGuardar", "Guardar");
			return "parametrizacionGeografica/formCiudad";
		}

		String mensajeFlash = (ciudadEntity.getCiudad() != null) ? "Ciudad editado con éxito!" : "Ciudad creado con éxito!";

		ciudadService.save(ciudadEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/parametrizacionGeografica/listCiudadesDepartamento/" + ciudadEntity.getDepartamentoEntity().getDepartamento();
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarCiudad/{id}")
	public String eliminarCiudad(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		CiudadEntity ciudadEntity = null;
		Long departamento = 0L; 
		if (id > 0) {			
			ciudadEntity = ciudadService.findOne(id);
			if (ciudadEntity == null) {
				flash.addFlashAttribute("error", "El ID del ciudad no existe en la BBDD!");
				return "redirect:/parametrizacionGeografica/listLocalidadesCiudad";
			}
			departamento = ciudadEntity.getDepartamentoEntity().getDepartamento();
			ciudadService.delete(id);
			flash.addFlashAttribute("success", "Ciudad eliminado con éxito!");
		}
		return "redirect:/parametrizacionGeografica/listCiudadesDepartamento/" + departamento;
	}
	
	private boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
		/*
		 * for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				logger.info("Hola usuario ".concat(auth.getName()).concat(" tu role es: ".concat(authority.getAuthority())));
				return true;
			}
		}
		
		return false;
		*/
		
	}
}
