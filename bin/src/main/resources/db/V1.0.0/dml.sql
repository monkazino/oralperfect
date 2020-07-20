INSERT INTO TB_GEO_PAIS (CODIGO, DESCRIPCION, ESTADO) VALUES ('COL', 'COLOMBIA', 'A');

INSERT INTO TB_GEO_DEPARTAMENTO (PAIS, CODIGO, DESCRIPCION, ESTADO) VALUES ((SELECT PAIS FROM TB_GEO_PAIS WHERE CODIGO = 'COL'), '27', 'CHOCO', 'A');

INSERT INTO TB_GEO_CIUDAD (DEPARTAMENTO, CODIGO, DESCRIPCION, ESTADO) VALUES ((SELECT DEPARTAMENTO FROM TB_GEO_DEPARTAMENTO WHERE CODIGO = '27'), '001', 'QUIBDO', 'A');
INSERT INTO TB_GEO_CIUDAD (DEPARTAMENTO, CODIGO, DESCRIPCION, ESTADO) VALUES ((SELECT DEPARTAMENTO FROM TB_GEO_DEPARTAMENTO WHERE CODIGO = '27'), '002', 'ISMINIA', 'A');

INSERT INTO TB_GEO_LOCALIDAD (CIUDAD, CODIGO, DESCRIPCION, ESTADO) VALUES ((SELECT CIUDAD FROM TB_GEO_CIUDAD WHERE CODIGO = '001' AND DEPARTAMENTO = (SELECT DEPARTAMENTO FROM TB_GEO_DEPARTAMENTO WHERE CODIGO = '27')), 'NAPL', 'NO APLICA', 'A');

INSERT INTO TB_GEO_BARRIO (LOCALIDAD, CODIGO, DESCRIPCION, ESTADO) VALUES ((SELECT LOCALIDAD FROM TB_GEO_LOCALIDAD WHERE CODIGO = 'NAPL' AND CIUDAD = (SELECT CIUDAD FROM TB_GEO_CIUDAD WHERE CODIGO = '001' AND DEPARTAMENTO = (SELECT DEPARTAMENTO FROM TB_GEO_DEPARTAMENTO WHERE CODIGO = '27'))), '01', 'CENTRO', 'A');

INSERT INTO TB_PARAM_TIPO_PARAMETRO_PERSONA (CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES('TIPIDENT', 'TIPOS DE IDENTIFICACIÓN', 'DESCRIBE LOS TIPOS DE IDENTIFICACÓN DE LOS DOCUMENTOS DE PERSONAS', NOW(), 'A');
INSERT INTO TB_PARAM_TIPO_PARAMETRO_PERSONA (CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES('GENERO', 'GENERO DE LA PERSONA', 'DESCRIBE LOS GENEROS DEL PACIENTE', NOW(), 'A');
INSERT INTO TB_PARAM_TIPO_PARAMETRO_PERSONA (CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES('ESTCIVIL', 'ESTADO CIVIL', 'DESCRIBE EL ESTADO CIVIL DE LA PERSONA', NOW(), 'A');
INSERT INTO TB_PARAM_TIPO_PARAMETRO_PERSONA (CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES('GRUPSANG', 'GRUPO SANGUINEO', 'DESCRIBE GRUPO SANGUINEO', NOW(), 'A');
INSERT INTO TB_PARAM_TIPO_PARAMETRO_PERSONA (CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES('NIVACAD', 'NIVEL ACADÉMICO', 'DESCRIBE EL NIVEL ACADEMICO DE LA PERSONA', NOW(), 'A');
INSERT INTO TB_PARAM_TIPO_PARAMETRO_PERSONA (CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES('RAZA', 'RAZAS', 'DESCRIBE LA RAZA DE LA PERSONA', NOW(), 'A');
INSERT INTO TB_PARAM_TIPO_PARAMETRO_PERSONA (CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES('OCUPACION', 'OCUPACIÓN', 'DESCRIBE LA OCUPACIÓN DE LA PERSONA', NOW(), 'A');

INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'TIPIDENT'), 'CC', 'CÉDULA DE CIUDADANIA', 'TIPO DE DOCUMENTO DE PERSONA CÉDULA DE CIDADANIA', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'TIPIDENT'), 'TI', 'TARJETA DE IDENTIDAD', 'TIPO DE DOCUMENTO DE PERSONA TARJETA DE IDENTIDAD', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'GENERO'), 'M', 'MASCULINO', 'GENERO DE PERSONA MASCULINO', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'GENERO'), 'F', 'FEMENINO', 'GENERO DE PERSONA FEMENINO', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'ESTCIVIL'), 'S', 'SOLTERO', 'ESTADO CIVIL DE PERSONA SOLTERO', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'ESTCIVIL'), 'C', 'CASADO', 'ESTADO CIVIL DE PERSONA CASADO', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'GRUPSANG'), 'OP', 'O+', 'GRUPO SANGUINEO DE PERSONA O POSITIVO', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'GRUPSANG'), 'ON', 'O-', 'GRUPO SANGUINEO DE PERSONA O NEGATIVO', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'NIVACAD'), 'PRI', 'PRIMARIA', 'NIVEL ACADÉMICO DE PERSONA PRIMARIA', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'NIVACAD'), 'SEC', 'SECUNDARIA', 'NIVEL ACADEMICO DE PERSONA SECUNDARIA', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'RAZA'), 'AFR', 'AFRODECENDIENTE', 'RAZA DE PERSONA AFRODECENDIENTE', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'RAZA'), 'IND', 'INDIGENA', 'RAZA DE PERSONA INDIGENA', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'OCUPACION'), 'EST', 'ESTUDIANTE', 'OCUPACION DE PERSONA ESTUDIANTE', NOW(), 'A');
INSERT INTO TB_PARAM_PARAMETRO_PERSONA (TIPO_PARAMETRO_PERSONA, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES((SELECT TIPO_PARAMETRO_PERSONA FROM TB_PARAM_TIPO_PARAMETRO_PERSONA WHERE CODIGO = 'OCUPACION'), 'EMP', 'EMPLEADO', 'OCUPACION DE PERSONA EMPLEADO', NOW(), 'A');


INSERT INTO TB_PARAM_TIPO_PARAMETRO_PRODUCTO (CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES('TIPO_PRODUCTO', 'TIPOS DE PRODUCTO', 'DESCRIBE LOS TIPOS DE PRODUCTOS DEL INVENTARIO', NOW(), 'A');
INSERT INTO TB_PARAM_TIPO_PARAMETRO_PRODUCTO (CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES('MARCA', 'MARCAS', 'DESCRIBE LAS MARCAS DE LOS PRODUCTOS DEL INVENTARIO', NOW(), 'A');
INSERT INTO TB_PARAM_TIPO_PARAMETRO_PRODUCTO (CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES('CATEGORIA', 'CATEGORIA', 'DESCRIBE LAS CATEGORIA DE LOS PRODUCTOS DEL INVENTARIO', NOW(), 'A');

INSERT INTO TB_PARAM_PARAMETRO_PRODUCTO (CODIGO,DESCRIPCION,FECHA_CREACION,OBSERVACION,TIPO_PARAMETRO_PRODUCTO,ESTADO) VALUES ('COD01','INSTRUMENTAL','2020-03-10','instrumental',1,'A');
INSERT INTO TB_PARAM_PARAMETRO_PRODUCTO (CODIGO,DESCRIPCION,FECHA_CREACION,OBSERVACION,TIPO_PARAMETRO_PRODUCTO,ESTADO) VALUES ('COD01','MARCA','2020-03-10','marca',2,'A');
INSERT INTO TB_PARAM_PARAMETRO_PRODUCTO (CODIGO,DESCRIPCION,FECHA_CREACION,OBSERVACION,TIPO_PARAMETRO_PRODUCTO,ESTADO) VALUES ('COD01','CATEGORIA','2020-03-10','categoria',3,'A');

/* Populate tables */
INSERT INTO TB_PERS_PACIENTE (PACIENTE, PARAM_TIPO_IDENTIFICACION, NUMERO_IDENTIFICACION, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, FECHA_NACIMIENTO, PARAM_GENERO, PARAM_ESTADO_CIVIL, PARAM_OCUPACION, DIRECCION_RESIDENCIA, TELEFONO_RESIDENCIA, CELULAR, EMAIL, PARAM_GRUPO_SANGUINEO, PARAM_NIVEL_ACADEMICO, PARAM_RAZA, ESTADO) VALUES  (1, 1, '67876788', 'CARLOS', 'ALBERTO', 'GONZALEZ', 'ORTIZ', NOW(), 3, 5, 13, 'CALLE 4 NO.23-58', '2789865', '31257955', 'loksh@hotmail.com', 7, 9, 11, 'A');


COMMIT;