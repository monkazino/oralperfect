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

import com.monkazino.consultorio.app.models.entity.TipoParametroEntity;
import com.monkazino.consultorio.app.models.service.ITipoParametroService;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("tipoParametroEntity")
public class TipoParametroController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private ITipoParametroService tipoParametroService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/tipoParametro/verTipoParametro/{id}")
	public String verTipoParametro(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		TipoParametroEntity tipoParametroEntity = tipoParametroService.fetchByIdWithParametros(id);
		if (tipoParametroEntity == null) {
			flash.addFlashAttribute("error", "El tipo parametro no existe en la base de datos");
			return "redirect:/tipoParametro/listarTipoParametro";
		}

		model.put("tipoParametroEntity", tipoParametroEntity);
		model.put("titulo", "Detalle tipo parametro: " + tipoParametroEntity.getDescripcion());
		return "tipoParametro/verTipoParametro";
	}

	@RequestMapping(value = {"/tipoParametro/listarTipoParametro", "/"}, method = RequestMethod.GET)
	public String listarTipoParametro(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
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

		Page<TipoParametroEntity> tipoParametros = tipoParametroService.findAll(pageRequest);

		PageRender<TipoParametroEntity> pageRender = new PageRender<TipoParametroEntity>("/tipoParametro/listarTipoParametro", tipoParametros);
		model.addAttribute("titulo", "Listado de tipo parametros");
		model.addAttribute("tipoParametros", tipoParametros);
		model.addAttribute("page", pageRender);
		return "tipoParametro/listarTipoParametro";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/tipoParametro/formTipoParametro")
	public String crearTipoParametro(Map<String, Object> model) {

		TipoParametroEntity tipoParametroEntity = new TipoParametroEntity();
		model.put("tipoParametroEntity", tipoParametroEntity);
		model.put("titulo", "Crear Tipo Parametro");
		return "tipoParametro/formTipoParametro";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/tipoParametro/formTipoParametro/{id}")
	public String editarTipoParametro(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		TipoParametroEntity tipoParametroEntity = null;

		if (id > 0) {
			tipoParametroEntity = tipoParametroService.findOne(id);
			if (tipoParametroEntity == null) {
				flash.addFlashAttribute("error", "El ID del tipo parametro no existe en la BBDD!");
				return "redirect:/tipoParametro/listarTipoParametro";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del tipo parametro no puede ser cero!");
			return "redirect:/tipoParametro/listarTipoParametro";
		}
		model.put("tipoParametroEntity", tipoParametroEntity);
		model.put("titulo", "Editar Tipo Parametro");
		return "tipoParametro/formTipoParametro";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/tipoParametro/formTipoParametro", method = RequestMethod.POST)
	public String guardarTipoParametro(@Valid TipoParametroEntity tipoParametroEntity, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Tipo Parametro");
			return "tipoParametro/formTipoParametro";
		}

		String mensajeFlash = (tipoParametroEntity.getTipoParametro() != null) ? "Tipo Parametro editado con éxito!" : "Tipo Parametro creado con éxito!";

		tipoParametroService.save(tipoParametroEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listarTipoParametro";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarTipoParametro/{id}")
	public String eliminarTipoParametro(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			tipoParametroService.delete(id);
			flash.addFlashAttribute("success", "Tipo Parametro eliminado con éxito!");
		}
		return "redirect:/tipoParametro/listarTipoParametro";
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
