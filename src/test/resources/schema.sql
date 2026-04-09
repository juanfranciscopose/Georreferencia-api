create schema if not exists data;

CREATE SEQUENCE IF NOT EXISTS data.migracion_ugs_seq 
     START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS data.ugs_seq 
     START WITH 100000 INCREMENT BY 1;

create table if not exists data.ugs(
    id BIGINT PRIMARY KEY,
    NOMBRE_CORTO VARCHAR2(20) NOT NULL,
    NOMBRE_LARGO VARCHAR2(150) NOT NULL,
    ACCESOS VARCHAR2(255),
    DESCRIPCION VARCHAR2(255),
    BARRIO_ID BIGINT,
    LOCALIDAD_ID BIGINT NOT NULL,
    CIUDAD_ID BIGINT,
    PROVINCIA_ID INT,
    FECHA_CREACION TIMESTAMP NOT NULL,
    FECHA_ACTUALIZACION TIMESTAMP,
    DESTACADO BOOLEAN
);

CREATE SEQUENCE IF NOT EXISTS data.migracion_georreferencias_seq
     START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS data.georreferencias_seq
     START WITH 100000 INCREMENT BY 1;

create table if not exists data.georreferencias(
    id BIGINT PRIMARY KEY,
    EDIFICIO_ELECTORAL BOOLEAN,
    CALLE VARCHAR2(100),
    ENTRE1 VARCHAR2(100),
    ENTRE2 VARCHAR2(100),
    NUMERO VARCHAR2(20),
    DEPTO VARCHAR2(20),
    OBSERVACIONES VARCHAR2(255),
    UG_ID BIGINT,
    LOCALIDAD_ID BIGINT,
    BARRIO_ID BIGINT,
    CIUDAD_ID BIGINT,
    PROVINCIA_ID INT,
    FECHA_CREACION TIMESTAMP NOT NULL, 
    FECHA_ACTUALIZACION TIMESTAMP,
    ESTADO_EDILICIO_ID VARCHAR2(1)
);

create table if not exists data.georreferencias_instituciones(
    institucion_id BIGINT,
    georreferencia_id BIGINT,
    PRIMARY KEY (institucion_id, georreferencia_id),
    FOREIGN KEY (georreferencia_id) REFERENCES data.georreferencias(id)
);

create table if not exists data.georreferencias_personas(
    persona_id BIGINT,
    georreferencia_id BIGINT,
    PRIMARY KEY (persona_id, georreferencia_id),
    FOREIGN KEY (georreferencia_id) REFERENCES data.georreferencias(id)
);

create table if not exists data.georreferencias_notas(
    nota_id BIGINT,
    georreferencia_id BIGINT,
    PRIMARY KEY (nota_id, georreferencia_id),
    FOREIGN KEY (georreferencia_id) REFERENCES data.georreferencias(id)
);

CREATE SEQUENCE IF NOT EXISTS data.notas_seq
     START WITH 1 INCREMENT BY 1;

create table if not exists data.notas(
    ID BIGINT PRIMARY KEY,
    TEXTO VARCHAR2(255),
    FECHA_CREACION TIMESTAMP NOT NULL,
    DELETE BOOLEAN,
    FECHA_RECORDATORIO TIMESTAMP,
    RECORDATORIO_CANCELADO BOOLEAN
);

CREATE TABLE IF NOT EXISTS data.ugs_notas (
    ug_id BIGINT NOT NULL,
    nota_id BIGINT NOT NULL,
    PRIMARY KEY (ug_id, nota_id),
    FOREIGN KEY (ug_id) REFERENCES data.ugs(id)
);