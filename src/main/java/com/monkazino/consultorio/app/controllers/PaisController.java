package com.monkazino.consultorio.app.controllers;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.monkazino.consultorio.app.models.entity.PaisEntity;
import com.monkazino.consultorio.app.models.service.IPaisService;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("paisEntity")
public class PaisController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IPaisService paisService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/parametrizacionGeografica/listDepartamentosPais/{id}")
	public String listDepartamentosPais(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		PaisEntity paisEntity = paisService.fetchByIdWithDepartamentos(id);
		if (paisEntity == null) {
			flash.addFlashAttribute("error", "El pais no existe en la base de datos");
			return "redirect:/parametrizacionGeografica/listPais";
		}

		model.put("paisEntity", paisEntity);
		model.put("lblTituloDetallePais", "Departamentos: " + paisEntity.getDescripcion());
		return "parametrizacionGeografica/listDepartamentosPais";
	}

	@RequestMapping(value = {"/parametrizacionGeografica/listPais"}, method = RequestMethod.GET)
	public String listPais(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {

		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
		}
		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");
		
		if(securityContext.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}

		if(request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}	
		
		Pageable pageRequest = PageRequest.of(page, 4);

		Page<PaisEntity> paises = paisService.findAll(pageRequest);

		PageRender<PaisEntity> pageRender = new PageRender<PaisEntity>("/parametrizacionGeografica/listPais", paises);
		model.addAttribute("lblTituloAplicacion", "Monkazino Soft");
		model.addAttribute("lblTituloListadoPais", "Paises");
		model.addAttribute("paises", paises);
		model.addAttribute("page", pageRender);
		return "parametrizacionGeografica/listPais";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionGeografica/formPais")
	public String crearPais(Map<String, Object> model) {
		PaisEntity paisEntity = new PaisEntity();
																																							
		model.put("paisEntity", paisEntity);
		model.put("lblTituloFormPais", "Pais");
		model.put("lblBotonGuardar", "Guardar");
		return "parametrizacionGeografica/formPais";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionGeografica/formPais/{id}")
	public String editarPais(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		PaisEntity paisEntity = null;

		if (id > 0) {
			paisEntity = paisService.findOne(id);
			if (paisEntity == null) {
				flash.addFlashAttribute("error", "El ID del pais no existe en la BBDD!");
				return "redirect:/parametrizacionGeografica/listPais";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del pais no puede ser cero!");
			return "redirect:/parametrizacionGeografica/listPais";
		}
		model.put("paisEntity", paisEntity);
		model.put("lblTituloFormPais", "Pais");
		model.put("lblBotonGuardar", "Guardar");
		return "parametrizacionGeografica/formPais";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionGeografica/formPais", method = RequestMethod.POST)
	public String guardarPais(@Valid PaisEntity paisEntity, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Pais");
			model.addAttribute("lblBotonGuardar", "Guardar");
			return "parametrizacionGeografica/formPais";
		}

		String mensajeFlash = (paisEntity.getPais() != null) ? "Pais editado con éxito!" : "Pais creado con éxito!";

		paisService.save(paisEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listPais";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarPais/{id}")
	public String eliminarPais(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			paisService.delete(id);
			flash.addFlashAttribute("success", "Pais eliminado con éxito!");
		}
		return "redirect:/parametrizacionGeografica/listPais";
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
