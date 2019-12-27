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

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/parametrizacionGeografica/listCiudadesDepartamento/{id}")
	public String listCiudadesDepartamento(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		DepartamentoEntity departamentoEntity = departamentoService.fetchByIdWithCiudades(id);
		if (departamentoEntity == null) {
			flash.addFlashAttribute("error", "El departamento no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listCiudadesDepartamento";
		}

		model.put("departamentoEntity", departamentoEntity);
		model.put("lblTituloDetalleDepartamento", "Detalle departamento: " + departamentoEntity.getDescripcion());
		return "parametrizacionGeografica/listCiudadesDepartamento";
	}

	@GetMapping("/parametrizacionGeografica/formDepartamento/pais/{pais}")
	public String crear(@PathVariable(value = "pais") Long pais, Map<String, Object> model,
			RedirectAttributes flash) {

		PaisEntity paisEntity = paisService.findOne(pais);

		if (paisEntity == null) {
			flash.addFlashAttribute("error", "El tipo departamento no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listPais";
		}

		DepartamentoEntity departamentoEntity= new DepartamentoEntity();
		departamentoEntity.setPaisEntity(paisEntity);
		
		model.put("departamentoEntity", departamentoEntity);
		model.put("lblTituloFormDepartamento", "Departamento");
		model.put("lblBotonGuardar", "Guardar");
		return "parametrizacionGeografica/formDepartamento";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionGeografica/formDepartamento/departamento/{id}")
	public String editarDepartamento(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		DepartamentoEntity departamentoEntity = null;

		if (id > 0) {
			departamentoEntity = departamentoService.findOne(id);
			if (departamentoEntity == null) {
				flash.addFlashAttribute("error", "El ID del departamento no existe en la BBDD!");
				return "redirect:/parametrizacionGeografica/listCiudadesDepartamento";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del departamento no puede ser cero!");
			return "redirect:/parametrizacionGeografica/listCiudadesDepartamento";
		}
		model.put("departamentoEntity", departamentoEntity);
		model.put("lblTituloFormDepartamento", "Departamento");
		model.put("lblBotonGuardar", "Guardar");
		return "parametrizacionGeografica/formDepartamento";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionGeografica/formDepartamento", method = RequestMethod.POST)
	public String guardarDepartamento(@Valid DepartamentoEntity departamentoEntity, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Departamento");
			model.addAttribute("lblBotonGuardar", "Guardar");
			return "parametrizacionGeografica/formDepartamento";
		}

		String mensajeFlash = (departamentoEntity.getDepartamento() != null) ? "Departamento editado con éxito!" : "Departamento creado con éxito!";

		departamentoService.save(departamentoEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/parametrizacionGeografica/listDepartamentosPais/" + departamentoEntity.getPaisEntity().getPais();
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarDepartamento/{id}")
	public String eliminarDepartamento(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		DepartamentoEntity departamentoEntity = null;
		Long pais = 0L;
		if (id > 0) {
			departamentoEntity = departamentoService.findOne(id);
			if (departamentoEntity == null) {
				flash.addFlashAttribute("error", "El ID del departamento no existe en la BBDD!");
				return "redirect:/parametrizacionGeografica/listCiudadesDepartamento";
			}
			pais = departamentoEntity.getPaisEntity().getPais();
			departamentoService.delete(id);
			flash.addFlashAttribute("success", "Departamento eliminado con éxito!");
		}
		return "redirect:/parametrizacionGeografica/listDepartamentosPais/" + pais;
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
