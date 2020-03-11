package com.monkazino.consultorio.app.controllers;

import java.util.Collection;
import java.util.Date;
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

import com.monkazino.consultorio.app.models.entity.ParametroProductoEntity;
import com.monkazino.consultorio.app.models.entity.TipoParametroProductoEntity;
import com.monkazino.consultorio.app.models.service.IParametroProductoService;
import com.monkazino.consultorio.app.models.service.ITipoParametroProductoService;
import com.monkazino.consultorio.app.util.general.EstadoActivoInactivoEnum;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("parametroProductoEntity")
public class ParametroProductoController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IParametroProductoService parametroProductoService;

	@Autowired
	private ITipoParametroProductoService tipoParametroProductoService;

	@GetMapping("/tipoParametroProducto/formParametroProducto/tipoParametroProducto/{tipoParametroProducto}")
	public String crearParametroProducto(@PathVariable(value = "tipoParametroProducto") Long tipoParametroProducto, Map<String, Object> model, RedirectAttributes flash) {
		TipoParametroProductoEntity tipoParametroProductoEntity = tipoParametroProductoService.findOne(tipoParametroProducto);
		if (tipoParametroProductoEntity == null) {
			flash.addFlashAttribute("error", "El tipo parametro producto no existe en la base de datos");
			return "redirect:/tipoParametroProducto/listTipoParametroProducto";
		}
		ParametroProductoEntity parametroProductoEntity= new ParametroProductoEntity();
		parametroProductoEntity.setTipoParametroProductoEntity(tipoParametroProductoEntity);
		parametroProductoEntity.setFechaCreacion(new Date());
		parametroProductoEntity.setEstado(EstadoActivoInactivoEnum.ACTIVO.getCodigo());
		model.put("parametroProductoEntity", parametroProductoEntity);
		model.put("lblTituloFormularioParametroProducto", "Parametro Producto");
		return "tipoParametroProducto/formParametroProducto";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/tipoParametroProducto/formParametroProducto/parametroProducto/{parametroProducto}")
	public String editarParametroProducto(@PathVariable(value = "parametroProducto") Long parametroProducto, Map<String, Object> model, RedirectAttributes flash) {
		ParametroProductoEntity parametroProductoEntity = null;
		if (parametroProducto > 0) {
			parametroProductoEntity = parametroProductoService.findOne(parametroProducto);
			if (parametroProductoEntity == null) {
				flash.addFlashAttribute("error", "El ID del parametro producto no existe en la BBDD!");
				return "redirectparametroProducto/listParametroProducto";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del parametro producto no puede ser cero!");
			return "redirect:/parametroProducto/listParametroProducto";
		}
		model.put("parametroProductoEntity", parametroProductoEntity);
		model.put("lblTituloFormularioParametroProducto", "Parametro Producto");
		return "tipoParametroProducto/formParametroProducto";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/tipoParametroProducto/formParametroProducto", method = RequestMethod.POST)
	public String guardarParametroProducto(@Valid ParametroProductoEntity parametroProductoEntity, BindingResult result, Map<String, Object> model, RedirectAttributes flash, SessionStatus status) {
		int countParametroProductoCodigo = 0;
		if (result.hasErrors()) {
			return "/tipoParametroProducto/formParametroProducto";
		}
		if (parametroProductoEntity.getParametroProducto() == null) {
			countParametroProductoCodigo = parametroProductoService.consultarCountParametroProductoByCodigoTipoParametroProducto(parametroProductoEntity.getCodigo().toUpperCase(), parametroProductoEntity.getTipoParametroProductoEntity().getTipoParametroProducto());
		} else {
			countParametroProductoCodigo = parametroProductoService.consultarCountParametroProductoByCodigoParametroProductoTipoParametroProducto(parametroProductoEntity.getCodigo().toUpperCase(), parametroProductoEntity.getParametroProducto(), parametroProductoEntity.getTipoParametroProductoEntity().getTipoParametroProducto());
		}
		if (countParametroProductoCodigo == 0) {
			parametroProductoEntity.setCodigo(parametroProductoEntity.getCodigo().toUpperCase());
			parametroProductoEntity.setDescripcion(parametroProductoEntity.getDescripcion().toUpperCase());
			parametroProductoService.save(parametroProductoEntity);
			status.setComplete();
			flash.addFlashAttribute("success", "Registro almacenado correctamente");
			return "redirect:/tipoParametroProducto/listParametroProducto/" + parametroProductoEntity.getTipoParametroProductoEntity().getTipoParametroProducto();
		} else {
			model.put("mensajeErrorParametroProducto", "El código ya se encuentra registrado");
			return "tipoParametroProducto/formParametroProducto";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarParametroProducto/{parametroProducto}")
	public String eliminarParametroProducto(@PathVariable(value = "parametroProducto") Long parametroProducto, RedirectAttributes flash) {
		ParametroProductoEntity parametroProductoEntity = null;
		Long tipoParametroProducto = 0L;
		if (parametroProducto > 0) {
			parametroProductoEntity = parametroProductoService.findOne(parametroProducto);
			if (parametroProductoEntity != null) {
				tipoParametroProducto = parametroProductoEntity.getTipoParametroProductoEntity().getTipoParametroProducto();
				
				/*if (parametroProductoEntity.getCiudades().isEmpty() || parametroProductoEntity.getCiudades() == null) {
					parametroProductoService.delete(parametroProducto);
					flash.addFlashAttribute("success", "Parametro producto eliminado con éxito");
				} else {
					flash.addFlashAttribute("success", "Parametro producto no se puede eliminar, tiene asociado ciudades");
				}*/
			} else {
				flash.addFlashAttribute("error", "El ID del parametro producto no existe en la BBDD!");
			} 
		}
		return "redirect:/tipoParametroProducto/listParametroProducto/" + tipoParametroProducto;
	}
	
	@RequestMapping(value = {"/parametroProducto/listParametroProducto"}, method = RequestMethod.GET)
	public String listParametroProducto(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
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

		Page<ParametroProductoEntity> parametrosProducto = parametroProductoService.findAll(pageRequest);

		PageRender<ParametroProductoEntity> pageRender = new PageRender<ParametroProductoEntity>("/parametroProducto/listParametroProducto", parametrosProducto);
		model.addAttribute("titulo", "Listado de parametros producto");
		model.addAttribute("parametrosProducto", parametrosProducto);
		model.addAttribute("page", pageRender);
		return "parametroProducto/listParametroProducto";
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
