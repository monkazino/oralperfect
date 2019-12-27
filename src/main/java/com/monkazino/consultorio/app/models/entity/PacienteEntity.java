package com.monkazino.consultorio.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TB_PERS_PACIENTE")
public class PacienteEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "PACIENTE")
	private Long paciente;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARAM_TIPO_IDENTIFICACION")
	private ParametroPersonaEntity paramTipoIdentificacion;
	
	@NotNull
	@NotEmpty
	@Column (name = "NUMERO_IDENTIFICACION")
	private String numeroIdentificacion;

	@NotNull
	@NotEmpty
	@Column (name = "PRIMER_NOMBRE")
	private String primerNombre;
	
	@Column (name = "SEGUNDO_NOMBRE")
	private String segundoNombre;
	
	@NotNull
	@NotEmpty
	@Column (name = "PRIMER_APELLIDO")
	private String primerApellido;
	
	@Column (name = "SEGUNDO_APELLIDO")
	private String segundoApellido;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Column(name = "FECHA_NACIMIENTO")
	private Date fechaNacimiento;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARAM_GENERO")
	private ParametroPersonaEntity paramGenero;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARAM_ESTADO_CIVIL")
	private ParametroPersonaEntity paramEstadoCivil;
		
	@NotNull
	@NotEmpty
	@Column (name = "DIRECCION_RESIDENCIA")
	private String direccionResidencia;
	
	@Column (name = "TELEFONO_RESIDENCIA")
	private String telefonoResidencia;
	
	@NotNull
	@NotEmpty
	@Column (name = "CELULAR")
	private String celular;
	
	@Email
	@Column (name = "EMAIL")
	private String email;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARAM_GRUPO_SANGUINEO")
	private ParametroPersonaEntity paramGrupoSanguineo;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARAM_NIVEL_ACADEMICO")
	private ParametroPersonaEntity paramNivelAcademico;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARAM_RAZA")
	private ParametroPersonaEntity paramRaza;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARAM_OCUPACION")
	private ParametroPersonaEntity paramOcupacion;
	
	@NotNull
	@NotEmpty
	@Column (name = "ESTADO")
	private String estado;
	
	public PacienteEntity() {
		this.paramTipoIdentificacion = new ParametroPersonaEntity();
		this.paramGenero = new ParametroPersonaEntity();
		this.paramEstadoCivil = new ParametroPersonaEntity();
		this.paramGrupoSanguineo = new ParametroPersonaEntity();
		this.paramNivelAcademico = new ParametroPersonaEntity();
		this.paramRaza = new ParametroPersonaEntity();
		this.paramOcupacion = new ParametroPersonaEntity();
	}
	
	public ParametroPersonaEntity getParamTipoIdentificacion() {
		return this.paramTipoIdentificacion;
	}
	
	public void setParamTipoIdentificacion(ParametroPersonaEntity paramTipoIdentificacion) {
		this.paramTipoIdentificacion = paramTipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public Long getPaciente() {
		return paciente;
	}

	public void setPaciente(Long paciente) {
		this.paciente = paciente;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public ParametroPersonaEntity getParamGenero() {
		return paramGenero;
	}

	public void setParamGenero(ParametroPersonaEntity paramGenero) {
		this.paramGenero = paramGenero;
	}

	public ParametroPersonaEntity getParamEstadoCivil() {
		return paramEstadoCivil;
	}

	public void setParamEstadoCivil(ParametroPersonaEntity paramEstadoCivil) {
		this.paramEstadoCivil = paramEstadoCivil;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDireccionResidencia() {
		return direccionResidencia;
	}

	public void setDireccionResidencia(String direccionResidencia) {
		this.direccionResidencia = direccionResidencia;
	}

	public String getTelefonoResidencia() {
		return telefonoResidencia;
	}

	public void setTelefonoResidencia(String telefonoResidencia) {
		this.telefonoResidencia = telefonoResidencia;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ParametroPersonaEntity getParamGrupoSanguineo() {
		return paramGrupoSanguineo;
	}

	public void setParamGrupoSanguineo(ParametroPersonaEntity paramGrupoSanguineo) {
		this.paramGrupoSanguineo = paramGrupoSanguineo;
	}

	public ParametroPersonaEntity getParamNivelAcademico() {
		return paramNivelAcademico;
	}

	public void setParamNivelAcademico(ParametroPersonaEntity paramNivelAcademico) {
		this.paramNivelAcademico = paramNivelAcademico;
	}

	public ParametroPersonaEntity getParamRaza() {
		return paramRaza;
	}

	public void setParamRaza(ParametroPersonaEntity paramRaza) {
		this.paramRaza = paramRaza;
	}

	public ParametroPersonaEntity getParamOcupacion() {
		return paramOcupacion;
	}

	public void setParamOcupacion(ParametroPersonaEntity paramOcupacion) {
		this.paramOcupacion = paramOcupacion;
	}

}
