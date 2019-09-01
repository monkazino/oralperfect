package com.monkazino.consultorio.app.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

import com.monkazino.consultorio.app.models.entity.PacienteEntity;
import com.monkazino.consultorio.app.models.entity.ParametroPersonaEntity;
import com.monkazino.consultorio.app.models.service.IPacienteService;
import com.monkazino.consultorio.app.models.service.IParametroPersonaService;
import com.monkazino.consultorio.app.util.general.EstadoPacienteEnum;
import com.monkazino.consultorio.app.util.general.ParametroPacienteEnum;
import com.monkazino.consultorio.app.util.paginator.PageRender;

@Controller
@SessionAttributes("pacienteEntity")
public class PacienteController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IPacienteService pacienteService;

	@Autowired
	private IParametroPersonaService parametroPersonaService;
	
	private List<ParametroPersonaEntity> listaParamTipoIdentificacion;
	private List<ParametroPersonaEntity> listaParamGenero;
	private List<ParametroPersonaEntity> listaParamEstadoCivil;
	private List<ParametroPersonaEntity> listaParamGrupoSanguineo;
	private List<ParametroPersonaEntity> listaParamNivelAcademico;
	private List<ParametroPersonaEntity> listaParamRaza;
	private List<ParametroPersonaEntity> listaParamOcupacion;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/paciente/verPaciente/{id}")
	public String verPaciente(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		PacienteEntity pacienteEntity = pacienteService.findOne(id);
		if (pacienteEntity == null) {
			flash.addFlashAttribute("error", "El paciente no existe en la base de datos");
			return "redirect:/paciente/listarPaciente";
		}

		model.put("pacienteEntity", pacienteEntity);
		model.put("lblTituloDetallePaciente", "Detalle paciente: " + pacienteEntity.getPrimerNombre() + " " + pacienteEntity.getSegundoNombre() + " " + pacienteEntity.getPrimerApellido() + " " + pacienteEntity.getSegundoApellido());
		return "paciente/verPaciente";
	}

	@RequestMapping(value = {"/paciente/listarPaciente"}, method = RequestMethod.GET)
	public String listarPaciente(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
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

		Page<PacienteEntity> pacientes = pacienteService.findAll(pageRequest);

		PageRender<PacienteEntity> pageRender = new PageRender<PacienteEntity>("/paciente/listarPaciente", pacientes);
		model.addAttribute("lblTituloListadoPaciente", "Listado de pacientes");
		model.addAttribute("pacientes", pacientes);
		model.addAttribute("page", pageRender);
		return "paciente/listarPaciente";
	}

	public void inicializarParametrosPersona() {
		listaParamTipoIdentificacion = new ArrayList<ParametroPersonaEntity>();
		listaParamTipoIdentificacion = parametroPersonaService.consultarParametrosPersonaTipoParametro(ParametroPacienteEnum.TIPO_IDENTIFICACION.getCodigo());
		
		listaParamGenero = new ArrayList<ParametroPersonaEntity>();
		listaParamGenero = parametroPersonaService.consultarParametrosPersonaTipoParametro(ParametroPacienteEnum.GENERO.getCodigo());
		
		listaParamEstadoCivil = new ArrayList<ParametroPersonaEntity>();
		listaParamEstadoCivil = parametroPersonaService.consultarParametrosPersonaTipoParametro(ParametroPacienteEnum.ESTADO_CIVIL.getCodigo());
		
		listaParamGrupoSanguineo = new ArrayList<ParametroPersonaEntity>();
		listaParamGrupoSanguineo = parametroPersonaService.consultarParametrosPersonaTipoParametro(ParametroPacienteEnum.GRUPO_SANGUINEO.getCodigo());
		
		listaParamNivelAcademico = new ArrayList<ParametroPersonaEntity>();
		listaParamNivelAcademico = parametroPersonaService.consultarParametrosPersonaTipoParametro(ParametroPacienteEnum.NIVEL_ACADEMICO.getCodigo());
		
		listaParamRaza = new ArrayList<ParametroPersonaEntity>();
		listaParamRaza = parametroPersonaService.consultarParametrosPersonaTipoParametro(ParametroPacienteEnum.RAZA.getCodigo());
		
		listaParamOcupacion = new ArrayList<ParametroPersonaEntity>();
		listaParamOcupacion = parametroPersonaService.consultarParametrosPersonaTipoParametro(ParametroPacienteEnum.OCUPACION.getCodigo());
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/paciente/formPaciente")
	public String crearPaciente(Map<String, Object> model) {
		inicializarParametrosPersona();

		PacienteEntity pacienteEntity = new PacienteEntity();
		pacienteEntity.setEstado(EstadoPacienteEnum.ACTIVO.getCodigo());
		
		model.put("pacienteEntity", pacienteEntity);
		model.put("listaParamTipoIdentificacion", listaParamTipoIdentificacion);
		model.put("listaParamGenero", listaParamGenero);
		model.put("listaParamEstadoCivil", listaParamEstadoCivil);
		model.put("listaParamGrupoSanguineo", listaParamGrupoSanguineo);
		model.put("listaParamNivelAcademico", listaParamNivelAcademico);
		model.put("listaParamRaza", listaParamRaza);
		model.put("listaParamOcupacion", listaParamOcupacion);
		model.put("lblTituloFormularioPaciente", "Paciente");
		model.put("lblBotonGuardar", "Guardar");
		return "paciente/formPaciente";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/paciente/formPaciente/{id}")
	public String editarPaciente(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		inicializarParametrosPersona();
		PacienteEntity pacienteEntity = null;

		if (id > 0) {
			pacienteEntity = pacienteService.findOne(id);
			if (pacienteEntity == null) {
				flash.addFlashAttribute("error", "El ID del paciente no existe en la BBDD!");
				return "redirect:/paciente/listarPaciente";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del paciente no puede ser cero!");
			return "redirect:/paciente/listarPaciente";
		}
		model.put("pacienteEntity", pacienteEntity);
		model.put("listaParamTipoIdentificacion", listaParamTipoIdentificacion);
		model.put("listaParamGenero", listaParamGenero);
		model.put("listaParamEstadoCivil", listaParamEstadoCivil);
		model.put("listaParamGrupoSanguineo", listaParamGrupoSanguineo);
		model.put("listaParamNivelAcademico", listaParamNivelAcademico);
		model.put("listaParamRaza", listaParamRaza);
		model.put("listaParamOcupacion", listaParamOcupacion);
		model.put("lblTituloFormularioPaciente", "Paciente");
		model.put("lblBotonGuardar", "Guardar");
		return "paciente/formPaciente";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/paciente/formPacienteReferencia/{id}")
	public String crearPacienteReferencia(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		inicializarParametrosPersona();
		PacienteEntity pacienteEntity = null;

		if (id > 0) {
			pacienteEntity = pacienteService.findOne(id);
			if (pacienteEntity == null) {
				flash.addFlashAttribute("error", "El ID del paciente no existe en la BBDD!");
				return "redirect:/paciente/listarPaciente";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del paciente no puede ser cero!");
			return "redirect:/paciente/listarPaciente";
		}
		model.put("pacienteEntity", pacienteEntity);
		model.put("lblBotonGuardar", "Guardar");
		return "paciente/formPacienteReferencia";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/paciente/formPaciente", method = RequestMethod.POST)
	public String guardarPaciente(@Valid PacienteEntity pacienteEntity, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("lblTituloFormularioPaciente", "Paciente");
			model.addAttribute("listaParamTipoIdentificacion", listaParamTipoIdentificacion);
			model.addAttribute("listaParamGenero", listaParamGenero);
			model.addAttribute("listaParamEstadoCivil", listaParamEstadoCivil);
			model.addAttribute("listaParamGrupoSanguineo", listaParamGrupoSanguineo);
			model.addAttribute("listaParamNivelAcademico", listaParamNivelAcademico);
			model.addAttribute("listaParamRaza", listaParamRaza);
			model.addAttribute("listaParamOcupacion", listaParamOcupacion);
			model.addAttribute("lblBotonGuardar", "Guardar");
			return "paciente/formPaciente";
		}

		String mensajeFlash = (pacienteEntity.getPaciente() != null) ? "Paciente editado con éxito!" : "Paciente creado con éxito!";

		pacienteService.save(pacienteEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/paciente/formPacienteReferencia/" + pacienteEntity.getPaciente();
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarPaciente/{id}")
	public String eliminarPaciente(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			pacienteService.delete(id);
			flash.addFlashAttribute("success", "Paciente eliminado con éxito!");
		}
		return "redirect:/paciente/listarPaciente";
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

	public List<ParametroPersonaEntity> getListaParamTipoIdentificacion() {
		return listaParamTipoIdentificacion;
	}

	public void setListaParamTipoIdentificacion(List<ParametroPersonaEntity> listaParamTipoIdentificacion) {
		this.listaParamTipoIdentificacion = listaParamTipoIdentificacion;
	}

	public List<ParametroPersonaEntity> getListaParamGenero() {
		return listaParamGenero;
	}

	public void setListaParamGenero(List<ParametroPersonaEntity> listaParamGenero) {
		this.listaParamGenero = listaParamGenero;
	}

	public List<ParametroPersonaEntity> getListaParamEstadoCivil() {
		return listaParamEstadoCivil;
	}

	public void setListaParamEstadoCivil(List<ParametroPersonaEntity> listaParamEstadoCivil) {
		this.listaParamEstadoCivil = listaParamEstadoCivil;
	}

	public List<ParametroPersonaEntity> getListaParamGrupoSanguineo() {
		return listaParamGrupoSanguineo;
	}

	public void setListaParamGrupoSanguineo(List<ParametroPersonaEntity> listaParamGrupoSanguineo) {
		this.listaParamGrupoSanguineo = listaParamGrupoSanguineo;
	}

	public List<ParametroPersonaEntity> getListaParamNivelAcademico() {
		return listaParamNivelAcademico;
	}

	public void setListaParamNivelAcademico(List<ParametroPersonaEntity> listaParamNivelAcademico) {
		this.listaParamNivelAcademico = listaParamNivelAcademico;
	}

	public List<ParametroPersonaEntity> getListaParamRaza() {
		return listaParamRaza;
	}

	public void setListaParamRaza(List<ParametroPersonaEntity> listaParamRaza) {
		this.listaParamRaza = listaParamRaza;
	}

	public List<ParametroPersonaEntity> getListaParamOcupacion() {
		return listaParamOcupacion;
	}

	public void setListaParamOcupacion(List<ParametroPersonaEntity> listaParamOcupacion) {
		this.listaParamOcupacion = listaParamOcupacion;
	}

}
