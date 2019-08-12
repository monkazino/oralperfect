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

import com.monkazino.consultorio.app.models.entity.ParametroEntity;
import com.monkazino.consultorio.app.models.entity.TipoParametroEntity;
import com.monkazino.consultorio.app.models.service.IParametroService;
import com.monkazino.consultorio.app.models.service.ITipoParametroService;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("parametroEntity")
public class ParametroController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IParametroService parametroService;

	@Autowired
	private ITipoParametroService tipoParametroService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/parametro/verParametro/{id}")
	public String verParametro(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		ParametroEntity parametroEntity = parametroService.findOne(id);
		if (parametroEntity == null) {
			flash.addFlashAttribute("error", "El parametro no existe en la base de datos");
			return "redirect:/parametro/listarParametro";
		}

		model.put("parametroEntity", parametroEntity);
		model.put("titulo", "Detalle parametro: " + parametroEntity.getDescripcion());
		return "parametro/verParametro";
	}

	@RequestMapping(value = {"/parametro/listarParametro"}, method = RequestMethod.GET)
	public String listarParametro(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
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

		Page<ParametroEntity> parametros = parametroService.findAll(pageRequest);

		PageRender<ParametroEntity> pageRender = new PageRender<ParametroEntity>("/parametro/listarParametro", parametros);
		model.addAttribute("titulo", "Listado de parametros");
		model.addAttribute("parametros", parametros);
		model.addAttribute("page", pageRender);
		return "parametro/listarParametro";
	}
	
	@GetMapping("/parametro/formParametro/tipoParametro/{tipoParametro}")
	public String crear(@PathVariable(value = "tipoParametro") Long tipoParametro, Map<String, Object> model,
			RedirectAttributes flash) {

		TipoParametroEntity tipoParametroEntity = tipoParametroService.findOne(tipoParametro);

		if (tipoParametroEntity == null) {
			flash.addFlashAttribute("error", "El tipo parametro no existe en la base de datos");
			return "redirect:/tipoParametro/listarTipoParametro";
		}

		ParametroEntity parametroEntity= new ParametroEntity();
		parametroEntity.setTipoParametroEntity(tipoParametroEntity);

		model.put("parametroEntity", parametroEntity);
		model.put("titulo", "Crear Parametro");

		return "parametro/formParametro";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametro/formParametro/parametro/{id}")
	public String editarParametro(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		ParametroEntity parametroEntity = null;

		if (id > 0) {
			parametroEntity = parametroService.findOne(id);
			if (parametroEntity == null) {
				flash.addFlashAttribute("error", "El ID del parametro no existe en la BBDD!");
				return "redirectparametro/listarParametro";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del parametro no puede ser cero!");
			return "redirect:/parametro/listarParametro";
		}
		model.put("parametroEntity", parametroEntity);
		model.put("titulo", "Editar Parametro");
		return "parametro/formParametro";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametro/formParametro", method = RequestMethod.POST)
	public String guardarParametro(@Valid ParametroEntity parametroEntity, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Parametro");
			return "parametro/formParametro";
		}

		String mensajeFlash = (parametroEntity.getParametro() != null) ? "Parametro editado con éxito!" : "Parametro creado con éxito!";

		parametroService.save(parametroEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/tipoParametro/verTipoParametro/" + parametroEntity.getTipoParametroEntity().getTipoParametro();
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarParametro/{id}")
	public String eliminarParametro(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			
			parametroService.delete(id);
			flash.addFlashAttribute("success", "Parametro eliminado con éxito!");
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
