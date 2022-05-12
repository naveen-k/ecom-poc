-- DROP SCHEMA IF EXISTS mastercatalogschema CASCADE;

-- CREATE SCHEMA mastercatalogschema;

-- //  current schema DDLs //

drop table if exists  mastercatalogschema.store;

drop table if exists  mastercatalogschema.store_offering_channel;

drop table if exists  mastercatalogschema.store_offering_channel_product;

drop table if exists  mastercatalogschema.store_product;

drop table if exists  mastercatalogschema.price_status_history;

drop table if exists  mastercatalogschema.sap_article_price;

drop table if exists  mastercatalogschema.store_offering_channel_component;

drop table if exists  mastercatalogschema.store_component;


-- // 3 future schema DDLs //

drop table if exists  mastercatalogschema.store_channel;

drop table if exists  mastercatalogschema.store_item;

drop table if exists  mastercatalogschema.store_offering;
