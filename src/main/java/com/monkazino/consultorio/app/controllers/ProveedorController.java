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

import com.monkazino.consultorio.app.models.entity.ProveedorEntity;
import com.monkazino.consultorio.app.models.service.IProveedorService;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("proveedorEntity")
public class ProveedorController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IProveedorService proveedorService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/proveedor/verInformacionProveedor/{proveedor}")
	public String verInformacionProveedor(@PathVariable(value = "proveedor") Long proveedor, Map<String, Object> model, RedirectAttributes flash) {

		ProveedorEntity proveedorEntity = proveedorService.fetchByIdWithDocumentosInventario(proveedor);
		if (proveedorEntity == null) {
			flash.addFlashAttribute("error", "El proveedor no existe en la base de datos");
			return "redirect:/proveedor/listProveedor";
		}

		model.put("proveedorEntity", proveedorEntity);
		model.put("lblTituloDetalleProveedor", "Detalle proveedor: " + proveedorEntity.getRazonSocial());
		return "proveedor/verInformacionProveedor";
	}

	@RequestMapping(value = {"/proveedor/listProveedor"}, method = RequestMethod.GET)
	public String listProveedor(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
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

		Page<ProveedorEntity> proveedors = proveedorService.findAll(pageRequest);

		PageRender<ProveedorEntity> pageRender = new PageRender<ProveedorEntity>("/proveedor/listProveedor", proveedors);
		model.addAttribute("lblTituloListadoProveedor", "Listado de proveedors");
		model.addAttribute("proveedors", proveedors);
		model.addAttribute("page", pageRender);
		return "proveedor/listProveedor";
	}

	public void inicializarVariablesProveedor() {
		
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/proveedor/formProveedor")
	public String crearProveedor(Map<String, Object> model) {
		inicializarVariablesProveedor();
		ProveedorEntity proveedorEntity = new ProveedorEntity();
		model.put("proveedorEntity", proveedorEntity);
		model.put("lblTituloFormularioProveedor", "Proveedor");
		return "proveedor/formProveedor";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/proveedor/formProveedor/{id}")
	public String editarProveedor(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		inicializarVariablesProveedor();
		ProveedorEntity proveedorEntity = null;

		if (id > 0) {
			proveedorEntity = proveedorService.findOne(id);
			if (proveedorEntity == null) {
				flash.addFlashAttribute("error", "El ID del proveedor no existe en la BBDD!");
				return "redirect:/proveedor/listProveedor";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del proveedor no puede ser cero!");
			return "redirect:/proveedor/listProveedor";
		}
		model.put("proveedorEntity", proveedorEntity);
		model.put("lblTituloFormularioProveedor", "Proveedor");
		return "proveedor/formProveedor";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/proveedor/formProveedor", method = RequestMethod.POST)
	public String guardarProveedor(@Valid ProveedorEntity proveedorEntity, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("lblTituloFormularioProveedor", "Proveedor");
			return "proveedor/formProveedor";
		}

		String mensajeFlash = (proveedorEntity.getProveedor() != null) ? "Proveedor editado con éxito!" : "Proveedor creado con éxito!";

		proveedorService.save(proveedorEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/proveedor/listProveedor";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarProveedor/{id}")
	public String eliminarProveedor(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			proveedorService.delete(id);
			flash.addFlashAttribute("success", "Proveedor eliminado con éxito!");
		}
		return "redirect:/proveedor/listProveedor";
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
	}

}
