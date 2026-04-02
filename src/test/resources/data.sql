DELETE FROM data.georreferencias_personas;
DELETE FROM data.georreferencias_notas;
delete from data.ugs_notas;
DELETE FROM data.georreferencias;
DELETE FROM data.ugs;

-- INSERTS PARA LA TABLA UG
INSERT INTO data.ugs (id, nombre_corto, nombre_largo, accesos, descripcion, barrio_id, localidad_id, fecha_creacion, fecha_actualizacion) 
VALUES (1, 'UG-CENTRO-1', 'Unidad de Gestion Centro 1', 'Acceso Principal', 'Zona céntrica comercial', 10, 100, NOW(), NULL);
INSERT INTO data.ugs (id, nombre_corto, nombre_largo, accesos, descripcion, barrio_id, localidad_id, fecha_creacion, fecha_actualizacion) 
VALUES (2, 'UG-NORTE-2', 'Unidad de Gestion Norte 2', 'Acceso Lateral', 'Zona norte residencial', 20, 100, NOW(), NULL);

-- INSERTS PARA LA TABLA GEORREFERENCIA
INSERT INTO data.georreferencias (id, estado_edilicio, edificio_electoral, calle, entre1, entre2, numero, depto, observaciones, ug_id, localidad_id, barrio_id, ciudad_id, provincia_id, fecha_creacion, fecha_actualizacion) 
VALUES (1, 'BUENO', true, 'Calle 50', 'Calle 6', 'Calle 7', '1234', 'A', 'Cerca de plaza principal', 1, 100, 10, 500, 1, NOW(), NULL);
INSERT INTO data.georreferencias (id, estado_edilicio, edificio_electoral, calle, entre1, entre2, numero, depto, observaciones, ug_id, localidad_id, barrio_id, ciudad_id, provincia_id, fecha_creacion, fecha_actualizacion) 
VALUES (2, 'REGULAR', false, 'Av. 7', 'Calle 50', 'Calle 51', '800', NULL, 'Edificio antiguo', 1, 100, 10, 500, 1, NOW(), NULL);
INSERT INTO data.georreferencias (id, estado_edilicio, edificio_electoral, calle, entre1, entre2, numero, depto, observaciones, ug_id, localidad_id, barrio_id, ciudad_id, provincia_id, fecha_creacion, fecha_actualizacion) 
VALUES (3, 'EXCELENTE', true, 'Calle 13', 'Calle 32', 'Calle 33', '45', '2B', 'Zona de escuelas', 2, 100, 20, 500, 1, NOW(), NULL);

INSERT INTO data.georreferencias_personas (georreferencia_id, persona_id) VALUES (2, 1);
-- Persona 10 con dos georreferencias
INSERT INTO data.georreferencias (id, estado_edilicio, calle, numero, ug_id, fecha_creacion) 
VALUES (10, 'BUENO', 'Calle Combinada', '100', 1, NOW());
INSERT INTO data.georreferencias (id, estado_edilicio, calle, numero, ug_id, fecha_creacion) 
VALUES (11, 'MALO', 'Calle Vieja', '200', 1, NOW());

-- Nota compartida (ID 50)
INSERT INTO data.notas (id, texto, fecha_creacion, delete) 
VALUES (50, 'Nota preexistente importante', NOW(), false);

-- Relaciones
INSERT INTO data.georreferencias_personas (persona_id, georreferencia_id) VALUES (10, 10);
INSERT INTO data.georreferencias_personas (persona_id, georreferencia_id) VALUES (10, 11);

-- Nota vinculada a la geo 10
INSERT INTO data.georreferencias_notas (nota_id, georreferencia_id) VALUES (50, 10);