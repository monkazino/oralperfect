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

import com.monkazino.consultorio.app.general.enums.EstadoActivoInactivoEnum;
import com.monkazino.consultorio.app.models.entity.PaisEntity;
import com.monkazino.consultorio.app.models.service.IPaisService;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("paisEntity")
public class PaisController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IPaisService paisService;

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/parametrizacionGeografica/formPais")
	public String crearPais(Map<String, Object> model) {
		PaisEntity paisEntity = new PaisEntity();
		paisEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		model.put("paisEntity", paisEntity);
		model.put("lblTituloFormPais", "Pais");
		return "parametrizacionSistema/parametrizacionGeografica/formPais";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/parametrizacionSistema/parametrizacionGeografica/formPais/{pais}")
	public String editarPais(@PathVariable(value = "pais") Long pais, Map<String, Object> model, RedirectAttributes flash) {
		PaisEntity paisEntity = null;
		if (pais > 0) {
			paisEntity = paisService.findOne(pais);
			if (paisEntity == null) {
				flash.addFlashAttribute("error", "El ID del pais no existe en la BBDD!");
				return "redirect:/parametrizacionSistema/parametrizacionGeografica/listPais";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del pais no puede ser cero!");
			return "redirect:/parametrizacionSistema/parametrizacionGeografica/listPais";
		}
		model.put("paisEntity", paisEntity);
		model.put("lblTituloFormPais", "Pais");
		return "parametrizacionSistema/parametrizacionGeografica/formPais";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/parametrizacionSistema/parametrizacionGeografica/formPais", method = RequestMethod.POST)
	public String guardarPais(@Valid PaisEntity paisEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		int countPaisCodigo = 0;
		if (result.hasErrors()) {
			return "parametrizacionSistema/parametrizacionGeografica/formPais";
		}
		if (paisEntity.getPais() == null) {
			countPaisCodigo = paisService.consultarCountPaisByCodigo(paisEntity.getCodigo().toUpperCase());
		} else {
			countPaisCodigo = paisService.consultarCountPaisByCodigoPais(paisEntity.getCodigo().toUpperCase(), paisEntity.getPais());
		}
		if (countPaisCodigo == 0) {
			paisEntity.setCodigo(paisEntity.getCodigo().toUpperCase());
			paisEntity.setDescripcion(paisEntity.getDescripcion().toUpperCase());
			paisService.save(paisEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:listPais";
		} else {
			model.put("mensajeErrorPais", "El código ya se encuentra registrado");
			return "parametrizacionSistema/parametrizacionGeografica/formPais";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarPais/{pais}")
	public String eliminarPais(@PathVariable(value = "pais") Long pais, RedirectAttributes flash) {
		if (pais > 0) {
			PaisEntity paisEntity = paisService.fetchByIdWithDepartamentos(pais);
			if (paisEntity == null) {
				flash.addFlashAttribute("error", "El ID del pais no existe en la BBDD!");
			} else if (paisEntity.getDepartamentos().isEmpty() || paisEntity.getDepartamentos() == null) {
				paisService.delete(pais);
				flash.addFlashAttribute("success", "Pais eliminado con éxito");
			} else {
				flash.addFlashAttribute("success", "Pais no se puede eliminar, tiene asociado departamentos");
			}
		}
		return "redirect:/parametrizacionSistema/parametrizacionGeografica/listPais";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/parametrizacionSistema/parametrizacionGeografica/listDepartamentosPais/{pais}")
	public String listDepartamentosPais(@PathVariable(value = "pais") Long pais, Map<String, Object> model, RedirectAttributes flash) {
		PaisEntity paisEntity = paisService.fetchByIdWithDepartamentos(pais);
		if (paisEntity == null) {
			flash.addFlashAttribute("error", "El pais no existe en la base de datos");
			return "redirect:/parametrizacionSistema/parametrizacionGeografica/listPais";
		}
		model.put("paisEntity", paisEntity);
		model.put("lblTituloDetallePais", "Departamentos: " + paisEntity.getDescripcion());
		return "parametrizacionSistema/parametrizacionGeografica/listDepartamentosPais";
	}

	@RequestMapping(value = {"/parametrizacionSistema/parametrizacionGeografica/listPais"}, method = RequestMethod.GET)
	public String listPais(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication, HttpServletRequest request) {

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

		PageRender<PaisEntity> pageRender = new PageRender<PaisEntity>("/parametrizacionSistema/parametrizacionGeografica/listPais", paises);
		model.addAttribute("lblTituloAplicacion", "Monkazino Soft");
		model.addAttribute("lblTituloListadoPais", "Paises");
		model.addAttribute("paises", paises);
		model.addAttribute("page", pageRender);
		return "parametrizacionSistema/parametrizacionGeografica/listPais";
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
