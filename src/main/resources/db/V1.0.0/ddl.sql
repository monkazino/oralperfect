CREATE SCHEMA DB_ORALPERFECT ;
USE DB_ORALPERFECT ;

CREATE TABLE TB_GEO_PAIS (
	PAIS BIGINT(20) NOT NULL AUTO_INCREMENT,
	CODIGO VARCHAR(30) NOT NULL,
	DESCRIPCION VARCHAR(300) NOT NULL,
	ESTADO CHAR(1) NOT NULL,
	PRIMARY KEY (PAIS),
	UNIQUE INDEX UNI_CODIGO_01 (CODIGO ASC)
)
ENGINE = INNODB DEFAULT CHARACTER SET = UTF8;

CREATE TABLE TB_GEO_DEPARTAMENTO (
	DEPARTAMENTO BIGINT(20) NOT NULL AUTO_INCREMENT,
	CODIGO VARCHAR(30) NOT NULL,
	DESCRIPCION VARCHAR(300) NOT NULL,
	PAIS BIGINT(20) NOT NULL,
	ESTADO CHAR(1) NOT NULL,
	PRIMARY KEY (DEPARTAMENTO),
	INDEX FRK_GEO_PAIS_01 (PAIS ASC),
	UNIQUE INDEX UNI_CODIGO_01 (CODIGO ASC, PAIS ASC),
	CONSTRAINT FRK_GEO_PAIS_01 FOREIGN KEY (PAIS) REFERENCES TB_GEO_PAIS (PAIS)
)
ENGINE = INNODB DEFAULT CHARACTER SET = UTF8;

CREATE TABLE TB_GEO_CIUDAD (
	CIUDAD BIGINT(20) NOT NULL AUTO_INCREMENT,
	CODIGO VARCHAR(30) NOT NULL,
	DESCRIPCION VARCHAR(300) NOT NULL,
	DEPARTAMENTO BIGINT(20) NOT NULL,
	ESTADO CHAR(1) NOT NULL,
	PRIMARY KEY (CIUDAD),
	INDEX FRK_GEO_DEPARTAMENTO_01 (DEPARTAMENTO ASC),
	UNIQUE INDEX UNI_CODIGO_01 (CODIGO ASC, DEPARTAMENTO ASC),
	CONSTRAINT FRK_GEO_DEPARTAMENTO_01 FOREIGN KEY (DEPARTAMENTO) REFERENCES TB_GEO_DEPARTAMENTO (DEPARTAMENTO)
)
ENGINE = INNODB DEFAULT CHARACTER SET = UTF8;

CREATE TABLE TB_GEO_LOCALIDAD (
	LOCALIDAD BIGINT(20) NOT NULL AUTO_INCREMENT,
	CODIGO VARCHAR(30) NOT NULL,
	DESCRIPCION VARCHAR(300) NOT NULL,
	CIUDAD BIGINT(20) NOT NULL,
	ESTADO CHAR(1) NOT NULL,
	PRIMARY KEY (LOCALIDAD),
	INDEX FRK_GEO_CIUDAD_01 (CIUDAD ASC),
	UNIQUE INDEX UNI_CODIGO (CODIGO ASC, CIUDAD ASC),
	CONSTRAINT FRK_GEO_CIUDAD_01 FOREIGN KEY (CIUDAD) REFERENCES TB_GEO_CIUDAD (CIUDAD)
)
ENGINE = INNODB DEFAULT CHARACTER SET = UTF8;

CREATE TABLE TB_GEO_BARRIO (
	BARRIO BIGINT(20) NOT NULL AUTO_INCREMENT,
	CODIGO VARCHAR(30) NOT NULL,
	DESCRIPCION VARCHAR(300) NOT NULL,
	LOCALIDAD BIGINT(20) NOT NULL,
	ESTADO CHAR(1) NOT NULL,
	PRIMARY KEY (BARRIO),
	INDEX FRK_GEO_LOCALIDAD_01 (LOCALIDAD ASC),
	UNIQUE INDEX UNI_CODIGO_01 (CODIGO ASC, LOCALIDAD ASC),
	CONSTRAINT FRK_GEO_LOCALIDAD_01 FOREIGN KEY (LOCALIDAD) REFERENCES TB_GEO_LOCALIDAD (LOCALIDAD)
)
ENGINE = INNODB DEFAULT CHARACTER SET = UTF8;

CREATE TABLE TB_PARAM_TIPO_PARAMETRO_PERSONA (
	TIPO_PARAMETRO_PERSONA BIGINT(20) NOT NULL AUTO_INCREMENT,
	CODIGO VARCHAR(30) NOT NULL,
	DESCRIPCION VARCHAR(300) NOT NULL,
	FECHA_CREACION DATE NOT NULL,
	OBSERVACION VARCHAR(500) NULL DEFAULT NULL,
	ESTADO CHAR(1) NOT NULL,
	PRIMARY KEY (TIPO_PARAMETRO_PERSONA),
	UNIQUE INDEX UNI_CODIGO_01 (CODIGO ASC)	
)
ENGINE = INNODB DEFAULT CHARACTER SET = UTF8;

CREATE TABLE TB_PARAM_PARAMETRO_PERSONA (
	PARAMETRO_PERSONA BIGINT(20) NOT NULL AUTO_INCREMENT,
	CODIGO VARCHAR(30) NOT NULL,
	DESCRIPCION VARCHAR(300) NOT NULL,
	FECHA_CREACION DATE NOT NULL,
	OBSERVACION VARCHAR(500) NULL DEFAULT NULL,
	TIPO_PARAMETRO_PERSONA BIGINT(20) NULL DEFAULT NULL,
	ESTADO CHAR(1) NOT NULL,
	PRIMARY KEY (PARAMETRO_PERSONA),
	INDEX FRK_PERS_TIPO_PARAMETRO_01 (TIPO_PARAMETRO_PERSONA ASC),
	UNIQUE INDEX UNI_CODIGO_01 (CODIGO ASC, TIPO_PARAMETRO_PERSONA ASC),
	CONSTRAINT FRK_PERS_TIPO_PARAMETRO_01 FOREIGN KEY (TIPO_PARAMETRO_PERSONA) REFERENCES TB_PARAM_TIPO_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA)
)
ENGINE = INNODB DEFAULT CHARACTER SET = UTF8;

CREATE TABLE TB_PROC_PROCEDIMIENTO (
	PROCEDIMIENTO BIGINT(20) NOT NULL AUTO_INCREMENT,
	CODIGO VARCHAR(30) NOT NULL,
	DESCRIPCION VARCHAR(300) NOT NULL,
	ESTADO CHAR(1) NOT NULL,
	UNIQUE INDEX UNI_CODIGO_01 (CODIGO ASC),
	PRIMARY KEY (PROCEDIMIENTO)
)
ENGINE = INNODB DEFAULT CHARACTER SET = UTF8;

CREATE TABLE TB_MED_MEDICAMENTO (
	MEDICAMENTO BIGINT(20) NOT NULL AUTO_INCREMENT,
	CODIGO VARCHAR(30) NOT NULL,
	DESCRIPCION VARCHAR(300) NOT NULL,
	ESTADO CHAR(1) NOT NULL,
	UNIQUE INDEX UNI_CODIGO_01 (CODIGO ASC),
	PRIMARY KEY (MEDICAMENTO)
)
ENGINE = INNODB DEFAULT CHARACTER SET = UTF8;

CREATE TABLE TB_PROD_PRODUCTO (
	PRODUCTO BIGINT(20) NOT NULL AUTO_INCREMENT,
	CODIGO VARCHAR(30) NOT NULL,
	DESCRIPCION VARCHAR(300) NOT NULL,
	ESTADO CHAR(1) NOT NULL,
	UNIQUE INDEX UNI_CODIGO_01 (CODIGO ASC),
	PRIMARY KEY (PRODUCTO)
)
ENGINE = INNODB DEFAULT CHARACTER SET = UTF8;

CREATE TABLE TB_USU_USUARIO (
	USUARIO BIGINT(20) NOT NULL AUTO_INCREMENT,
	NOMBRE_USUARIO VARCHAR(30) NOT NULL,
	PRIMER_APELLIDO VARCHAR(50) NOT NULL,
	PRIMER_NOMBRE VARCHAR(50) NOT NULL,
	SEGUNDO_APELLIDO VARCHAR(50) NULL DEFAULT NULL,
	SEGUNDO_NOMBRE VARCHAR(50) NULL DEFAULT NULL,
	ESTADO CHAR(1) NOT NULL,
	UNIQUE INDEX UNI_CODIGO_01 (NOMBRE_USUARIO ASC),
	PRIMARY KEY (USUARIO)
)
ENGINE = INNODB DEFAULT CHARACTER SET = UTF8;

CREATE TABLE TB_PERS_PACIENTE (
	PACIENTE BIGINT(20) NOT NULL AUTO_INCREMENT,
	CELULAR VARCHAR(255) NOT NULL,
	DIRECCION_RESIDENCIA VARCHAR(255) NOT NULL,
	EMAIL VARCHAR(255) NULL DEFAULT NULL,
	ESTADO CHAR(1) NOT NULL,
	FECHA_NACIMIENTO DATE NOT NULL,
	NUMERO_IDENTIFICACION VARCHAR(255) NOT NULL,
	PRIMER_APELLIDO VARCHAR(255) NOT NULL,
	PRIMER_NOMBRE VARCHAR(255) NOT NULL,
	SEGUNDO_APELLIDO VARCHAR(255) NULL DEFAULT NULL,
	SEGUNDO_NOMBRE VARCHAR(255) NULL DEFAULT NULL,
	TELEFONO_RESIDENCIA VARCHAR(255) NULL DEFAULT NULL,
	PARAM_ESTADO_CIVIL BIGINT(20) NOT NULL,
	PARAM_GENERO BIGINT(20) NOT NULL,
	PARAM_GRUPO_SANGUINEO BIGINT(20) NOT NULL,
	PARAM_NIVEL_ACADEMICO BIGINT(20) NOT NULL,
	PARAM_OCUPACION BIGINT(20) NOT NULL,
	PARAM_RAZA BIGINT(20) NOT NULL,
	PARAM_TIPO_IDENTIFICACION BIGINT(20) NOT NULL,
	PRIMARY KEY (PACIENTE),
	INDEX FRK_PERS_PARAM_PARAMETRO_01 (PARAM_ESTADO_CIVIL ASC),
	INDEX FRK_PERS_PARAM_PARAMETRO_02 (PARAM_GENERO ASC),
	INDEX FRK_PERS_PARAM_PARAMETRO_03 (PARAM_GRUPO_SANGUINEO ASC),
	INDEX FRK_PERS_PARAM_PARAMETRO_04 (PARAM_NIVEL_ACADEMICO ASC),
	INDEX FRK_PERS_PARAM_PARAMETRO_05 (PARAM_OCUPACION ASC),
	INDEX FRK_PERS_PARAM_PARAMETRO_06 (PARAM_RAZA ASC),
	INDEX FRK_PERS_PARAM_PARAMETRO_07 (PARAM_TIPO_IDENTIFICACION ASC),
	CONSTRAINT FRK_PERS_PARAM_PARAMETRO_01 FOREIGN KEY (PARAM_ESTADO_CIVIL) REFERENCES TB_PARAM_PARAMETRO_PERSONA (PARAMETRO_PERSONA),
	CONSTRAINT FRK_PERS_PARAM_PARAMETRO_02 FOREIGN KEY (PARAM_OCUPACION) REFERENCES TB_PARAM_PARAMETRO_PERSONA (PARAMETRO_PERSONA),
	CONSTRAINT FRK_PERS_PARAM_PARAMETRO_03 FOREIGN KEY (PARAM_RAZA) REFERENCES TB_PARAM_PARAMETRO_PERSONA (PARAMETRO_PERSONA),
	CONSTRAINT FRK_PERS_PARAM_PARAMETRO_04 FOREIGN KEY (PARAM_TIPO_IDENTIFICACION) REFERENCES TB_PARAM_PARAMETRO_PERSONA (PARAMETRO_PERSONA),
	CONSTRAINT FRK_PERS_PARAM_PARAMETRO_05 FOREIGN KEY (PARAM_NIVEL_ACADEMICO) REFERENCES TB_PARAM_PARAMETRO_PERSONA (PARAMETRO_PERSONA),
	CONSTRAINT FRK_PERS_PARAM_PARAMETRO_06 FOREIGN KEY (PARAM_GENERO) REFERENCES TB_PARAM_PARAMETRO_PERSONA (PARAMETRO_PERSONA),
	CONSTRAINT FRK_PERS_PARAM_PARAMETRO_07 FOREIGN KEY (PARAM_GRUPO_SANGUINEO) REFERENCES TB_PARAM_PARAMETRO_PERSONA (PARAMETRO_PERSONA)
)
ENGINE = INNODB  DEFAULT CHARACTER SET = UTF8;