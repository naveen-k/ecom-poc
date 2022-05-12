
CREATE TABLE mastercatalogschema.store_offering_channel_product
(
store_offering_channel_prd_key    SERIAL PRIMARY KEY NOT NULL,
store_id  character varying(30) NOT NULL,
offering_id  character varying(30) NOT NULL,
channel_id  character varying(30) NOT NULL,
product_id  character varying(30) NOT NULL,
record_status_id integer,
create_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
version_id character varying(30)
);



CREATE TABLE    mastercatalogschema.store_offering_channel
(
store_offering_channel_key  SERIAL PRIMARY KEY NOT NULL,
store_id    character varying(30) NOT NULL,
offering_id character varying(30) NOT NULL,
channel_id  character varying(30) NOT NULL,
version_id  character varying(30),
record_status_id integer,
create_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE    mastercatalogschema.store_product
(
store_product_key   SERIAL PRIMARY KEY NOT NULL,
store_id    character varying(30) NOT NULL,
product_id  character varying(30) NOT NULL,
price   FLOAT   NOT NULL,
version_id  character varying(30),
record_status_id integer,
create_datetime     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
release_datetime    TIMESTAMP WITH TIME ZONE
);


CREATE TABLE    mastercatalogschema.store
(
store_key   SERIAL PRIMARY KEY NOT NULL,
store_id    character varying(30) NOT NULL,
store_name  character varying(50) NOT NULL,
version_id  character varying(30),
record_status_id integer,
create_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE mastercatalogschema.sap_article_price
(
sales_catalog_price_status_key SERIAL PRIMARY KEY NOT NULL,
store_id character varying(30) NOT NULL,
channel_id character(30) NOT NULL,
unit_of_measure_id character varying(30) NOT NULL,
price float NOT NULL,
sap_article_id character varying(30) NOT NULL,
effective_start_datetime TIMESTAMP NOT NULL,
effective_end_datetime TIMESTAMP NOT NULL,
create_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE mastercatalogschema.price_status_history
(
price_status_hist_key  SERIAL PRIMARY KEY NOT NULL,
store_id  character varying(30) NOT NULL,
offering_id  character varying(30) NOT NULL,
channel_id  character varying(30) NOT NULL,
product_id  character varying(30) NOT NULL, 
effective_start_datetime TIMESTAMP NOT NULL,
effective_end_datetime TIMESTAMP NOT NULL,
price decimal(19,4) NOT NULL,
create_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
record_status_id integer
);

CREATE TABLE mastercatalogschema.store_offering_channel_component	 
(
store_offering_channel_comp_key SERIAL PRIMARY KEY NOT NULL,
store_id character varying(30) NOT NULL,
offering_id character varying(30) NOT NULL,
channel_id character varying(30) NOT NULL,
component_id character varying(30) NOT NULL,
record_status_id integer,
create_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
version_id character varying(30)
);
 
CREATE TABLE mastercatalogschema.store_component	 
(
store_component_key SERIAL PRIMARY KEY NOT NULL,
store_id character varying(30) NOT NULL,
component_id character varying(30) NOT NULL,
version_id character varying(30),
record_status_id integer,
create_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
release_datetime TIMESTAMP WITH TIME ZONE
);

CREATE TABLE    mastercatalogschema.store_item
(
store_item_key  SERIAL PRIMARY KEY NOT NULL,
store_id    character varying(30) NOT NULL,
item_id character varying(30) NOT NULL,
price   float NOT NULL,
version_id  character varying(30),
record_status_id integer,
create_datetime TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
