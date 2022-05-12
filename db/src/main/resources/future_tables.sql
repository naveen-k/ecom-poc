CREATE TABLE    mastercatalogschema.store_channel
(
store_channel_key   SERIAL PRIMARY KEY NOT NULL,
store_id    character varying(30) NOT NULL,
channel_id  character varying(30) NOT NULL,
version_id  character varying(30),
record_status_id integer,
create_datetime     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE    mastercatalogschema.store_offering
(
store_offering_key  SERIAL PRIMARY KEY NOT NULL,
store_id    character varying(30) NOT NULL,
offering_id character varying(30) NOT NULL,
version_id  character varying(30),
record_status_id integer,
create_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


