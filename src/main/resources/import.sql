/* Populate tables */
INSERT INTO TB_PARAM_TIPO_PARAMETRO (TIPO_PARAMETRO, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES(1, 'TIPIDENT', 'Tipos de identificación', 'Describe los tipos de identificación de los documentos de personas N o J', NOW(), 'A');
INSERT INTO TB_PARAM_TIPO_PARAMETRO (TIPO_PARAMETRO, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES(2, 'TIPPAR002', 'Sucursales', 'Describe las sucursal del consultorio', NOW(), 'A');

/* Populate tables */
INSERT INTO TB_PARAM_PARAMETRO (PARAMETRO, TIPO_PARAMETRO, CODIGO, DESCRIPCION, OBSERVACION, FECHA_CREACION, ESTADO) VALUES(1, 1 , 'CC', 'Cédula de Ciudadania', 'Tipo de documento cédula de ciudadania ', NOW(), 'A');

