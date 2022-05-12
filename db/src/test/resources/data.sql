--story 301 and test data for story 550 

INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id,record_status_id, version_id) VALUES('00038', 'Instorecatering' ,'web'  ,'mdm-p-4' ,'2' ,'' );
INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id,record_status_id, version_id) VALUES('00038', 'Instorecatering' ,'web'  ,'mdm-p-51' ,'2' ,'' );
INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id,record_status_id, version_id) VALUES('30', 'Instorecatering' ,'web'  ,'mdm-p-9','2' ,'' );
INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id,record_status_id, version_id) VALUES('00021', 'Instorecatering' ,'web'  ,'mdm-p-9','2' ,'');
INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id,record_status_id, version_id) VALUES('00021', 'Instorecatering' ,'web'  ,'mdm-p-51','2' ,'');
INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id,record_status_id, version_id) VALUES('00096', 'Instorecatering' ,'web'  ,'mdm-p-8','2' ,'');
INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id,record_status_id, version_id) VALUES('00096', 'cateringlite' ,'web'  ,'mdm-p-8','2' ,'');
INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id,record_status_id, version_id) VALUES('00038', 'Instorecatering' ,'web'  ,'mdm-p-5' ,'2' ,'' );
INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id,record_status_id, version_id) VALUES('00138', 'Instorecatering' ,'web'  ,'mdm-p-3' ,'2' ,'' );

--story 550 GET store product(This process successfully happens when the product is published)

-- product 4 and 51 share same sap article

INSERT INTO mastercatalogschema.store_product (Store_ID, Product_ID, Price, Version_ID, Record_Status_ID, Release_Datetime) VALUES('00096' ,'mdm-p-8' ,'0' ,'' ,'2' ,'2020-06-11 12:30:00-0000 ');
INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00096' ,'Instorecatering' ,'web' ,'','2' );
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-8' ,'00096' ,'web' ,'UOM1' ,'2020-07-11' ,'2020-08-11' ,'1.1');
INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00096' ,'cateringlite' ,'web' ,'','2' );
INSERT INTO mastercatalogschema.product (Product_ID, Product_Name, Item_SKU, Item_SKU_Size, Product_Description, Serving_Size, EBT_Eligible_Flag, Final_Selling_Item_Flag, Sodium_Disclaimer_Flag, Image_URL, Version_ID, Product_Type_ID, Unit_Of_Measure_ID, Product_Status_Type_ID, Flavor_Type_ID, Record_Status_ID, Product_Friendly_Name, Release_Datetime, SAP_Article_ID) VALUES('mdm-p-8' ,'Donut Box Bundle' ,'prod-sap-6' ,'Catering size' ,'Donut Box Bundle' ,'1' ,'N' ,'Y' ,'N' ,'url8' ,'' ,'OO' ,'UOM1' ,'' ,'FLAV2' ,'4' ,'Donut Box Bundle' ,'2020-07-15 12:30:00-0000 ' ,'sap-art-8' );
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO mastercatalogschema.store_product (Store_ID, Product_ID, Price, Version_ID, Record_Status_ID, Release_Datetime) VALUES('00038' ,'mdm-p-4' ,'0' ,'' ,'2' ,'2020-06-11 12:30:00-0000 ');
INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00038' ,'Instorecatering' ,'web' ,'','2' );
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-51' ,'00038' ,'web' ,'UOM1' ,'2020-07-11' ,'2020-08-11' ,'3');
INSERT INTO mastercatalogschema.product (Product_ID, Product_Name, Item_SKU, Item_SKU_Size, Product_Description, Serving_Size, EBT_Eligible_Flag, Final_Selling_Item_Flag, Sodium_Disclaimer_Flag, Image_URL, Version_ID, Product_Type_ID, Unit_Of_Measure_ID, Product_Status_Type_ID, Flavor_Type_ID, Record_Status_ID, Product_Friendly_Name, Release_Datetime, SAP_Article_ID) VALUES('mdm-p-4' ,'Catering Sides & Toppings' ,'prod-sap-3' ,'Catering size' ,'Catering Sides & Toppings' ,'1' ,'N' ,'N' ,'N' ,'url4' ,'' ,'FPC' ,'UOM2' ,'' ,'FLAV2' ,'4' ,'Cheese Hoagie' ,'2020-07-15 12:30:00-0000 ' ,'sap-art-51' );
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO mastercatalogschema.store_product (Store_ID, Product_ID, Price, Version_ID, Record_Status_ID, Release_Datetime) VALUES('00021' ,'mdm-p-51' ,'0' ,'' ,'2' ,'2020-06-11 12:30:00-0000 ');
INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00021' ,'Instorecatering' ,'web' ,'','2' );
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-51' ,'00021' ,'web' ,'UOM1' ,'2020-07-11' ,'2020-08-11' ,'3.3');
INSERT INTO mastercatalogschema.product (Product_ID, Product_Name, Item_SKU, Item_SKU_Size, Product_Description, Serving_Size, EBT_Eligible_Flag, Final_Selling_Item_Flag, Sodium_Disclaimer_Flag, Image_URL, Version_ID, Product_Type_ID, Unit_Of_Measure_ID, Product_Status_Type_ID, Flavor_Type_ID, Record_Status_ID, Product_Friendly_Name, Release_Datetime, SAP_Article_ID) VALUES('mdm-p-51' ,'Shorti Hoagie' ,'prod-sap-2' ,'SINGLE SERVE' ,'Short Hoagie-Featured 2' ,'1' ,'Y' ,'N' ,'N' ,'url311' ,'' ,'FPC' ,'UOM2' ,'' ,'FLAV2' ,'4' ,'Italian Hoagie' ,'2020-07-15 12:30:00-0000 ' ,'sap-art-51' );
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO mastercatalogschema.store_product (Store_ID, Product_ID, Price, Version_ID, Record_Status_ID, Release_Datetime) VALUES('00021' ,'mdm-p-9' ,'0' ,'' ,'2' ,'2020-06-12 12:30:00-0000 ');
--product_offering already present
--store_offering_channel already present
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-9' ,'00021' ,'web' ,'UOM1' ,'2020-07-11' ,'2020-08-11' ,'3.5');
--product already present
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO mastercatalogschema.store_product (Store_ID, Product_ID, Price, Version_ID, Record_Status_ID, Release_Datetime) VALUES('30' ,'mdm-p-9' ,'0' ,'' ,'2' ,'2020-06-12 12:30:00-0000 ');
INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('30' ,'Instorecatering' ,'web' ,'','2' );
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-9' ,'30' ,'web' ,'UOM1' ,'2020-07-11' ,'2020-08-11' ,'3.2');
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-9' ,'30' ,'web' ,'UOM1' ,'2020-08-12' ,'2020-09-11' ,'3.7');
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-9' ,'30' ,'web' ,'UOM1' ,'2020-09-12' ,'2020-10-11' ,'3.8'); -- same effective dates changed to different
INSERT INTO mastercatalogschema.product (Product_ID, Product_Name, Item_SKU, Item_SKU_Size, Product_Description, Serving_Size, EBT_Eligible_Flag, Final_Selling_Item_Flag, Sodium_Disclaimer_Flag, Image_URL, Version_ID, Product_Type_ID, Unit_Of_Measure_ID, Product_Status_Type_ID, Flavor_Type_ID, Record_Status_ID, Product_Friendly_Name, Release_Datetime, SAP_Article_ID) VALUES('mdm-p-9' ,'Bagel Box' ,'prod-sap-7' ,'Catering size' ,'Bagel Box' ,'1' ,'N' ,'Y' ,'N' ,'url9' ,'' ,'OG' ,'UOM1' ,'' ,'FLAV2' ,'4' ,'Bagel Box' ,'2020-07-15 12:30:00-0000 ' ,'sap-art-9' );
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- rel date greater
INSERT INTO mastercatalogschema.store_product (Store_ID, Product_ID, Price, Version_ID, Record_Status_ID, Release_Datetime) VALUES('30' ,'mdm-p-16' ,'0' ,'' ,'2' ,'2021-06-12 12:30:00-0000 ');
--store_offering_channel already present
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-16' ,'30' ,'web' ,'UOM1' ,'2020-07-11' ,'2020-08-11' ,'3.1');
INSERT INTO mastercatalogschema.product (Product_ID, Product_Name, Item_SKU, Item_SKU_Size, Product_Description, Serving_Size, EBT_Eligible_Flag, Final_Selling_Item_Flag, Sodium_Disclaimer_Flag, Image_URL, Version_ID, Product_Type_ID, Unit_Of_Measure_ID, Product_Status_Type_ID, Flavor_Type_ID, Record_Status_ID, Product_Friendly_Name, Release_Datetime, SAP_Article_ID) VALUES('mdm-p-16' ,'Herrs Kettle Cooked REGULAR' ,'99063' ,'SINGLE SERVE' ,'Herrs Kettle Cooked REGULAR' ,'1' ,'N' ,'Y' ,'N' ,'url16' ,'' ,'Simple Item (FPS)' ,'UOM2' ,'' ,'FLAV2' ,'4' ,'Herrs Kettle Cooked REGULAR' ,'2020-07-15 12:30:00-0000 ' ,'sap-art-16' );
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- no product_offering
INSERT INTO mastercatalogschema.store_product (Store_ID, Product_ID, Price, Version_ID, Record_Status_ID, Release_Datetime) VALUES('7' ,'mdm-p-87' ,'0' ,'' ,'2' ,'2020-06-11 12:30:00-0000 ');
-- INSERT INTO mastercatalogschema.product_offering (Version_ID, Offering_ID, Product_ID, Record_Status_ID) VALUES('' ,'Instorecatering' ,'mdm-p-87' ,'2');
INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('7' ,'Instorecatering' ,'web' ,'','2' );
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-87' ,'7' ,'web' ,'UOM1' ,'2020-07-11' ,'2020-08-11' ,'3.9');
INSERT INTO mastercatalogschema.product (Product_ID, Product_Name, Item_SKU, Item_SKU_Size, Product_Description, Serving_Size, EBT_Eligible_Flag, Final_Selling_Item_Flag, Sodium_Disclaimer_Flag, Image_URL, Version_ID, Product_Type_ID, Unit_Of_Measure_ID, Product_Status_Type_ID, Flavor_Type_ID, Record_Status_ID, Product_Friendly_Name, Release_Datetime, SAP_Article_ID) VALUES('mdm-p-87' ,'Herrs Kettle Cooked Sour Cream & Onion' ,'99080' ,'SINGLE SERVE' ,'Herrs Kettle Cooked Sour Cream & Onion' ,'1' ,'N' ,'Y' ,'N' ,'url17' ,'' ,'Simple Item (FPS)' ,'UOM2' ,'' ,'FLAV2' ,'4' ,'Herrs Kettle Cooked Sour Cream & Onion' ,'2020-07-15 12:30:00-0000 ' ,'sap-art-87' );
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- no store_offering_channel
INSERT INTO mastercatalogschema.store_product (Store_ID, Product_ID, Price, Version_ID, Record_Status_ID, Release_Datetime) VALUES('100' ,'mdm-p-18' ,'0' ,'' ,'2' ,'2020-06-11 12:30:00-0000 ');
-- INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('100' ,'Instorecatering' ,'web' ,'','2' );
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-18' ,'100' ,'web' ,'UOM1' ,'2020-07-11' ,'2020-08-11' ,'4.1');
INSERT INTO mastercatalogschema.product (Product_ID, Product_Name, Item_SKU, Item_SKU_Size, Product_Description, Serving_Size, EBT_Eligible_Flag, Final_Selling_Item_Flag, Sodium_Disclaimer_Flag, Image_URL, Version_ID, Product_Type_ID, Unit_Of_Measure_ID, Product_Status_Type_ID, Flavor_Type_ID, Record_Status_ID, Product_Friendly_Name, Release_Datetime, SAP_Article_ID) VALUES('mdm-p-18' ,'Herrs Kettle Cooked Veggie ' ,'9999' ,'SINGLE SERVE' ,'Herrs Kettle Cooked Veggie ' ,'1' ,'N' ,'Y' ,'N' ,'url18' ,'' ,'Simple Item (FPS)' ,'UOM2' ,'' ,'FLAV2' ,'4' ,'Herrs Kettle Cooked Veggie ' ,'2020-07-15 12:30:00-0000 ' ,'sap-art-18' );
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Note:product 4 and 51 share same sap article
--record_status=3(changed to 2 to test pricing orch story)
INSERT INTO mastercatalogschema.store_product (Store_ID, Product_ID, Price, Version_ID, Record_Status_ID, Release_Datetime) VALUES('00038' ,'mdm-p-51' ,'0' ,'' ,'2' ,'2020-06-11 12:30:00-0000 ');
--product_offering already present
--store_offering_channel already present
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-51' ,'00038' ,'web' ,'UOM1' ,'2020-08-12' ,'2020-09-11' ,'4.2'); 
--product already present
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--product not published yet
INSERT INTO mastercatalogschema.store_product (Store_ID, Product_ID, Price, Version_ID, Record_Status_ID, Release_Datetime) VALUES('00038' ,'mdm-p-5' ,'0' ,'' ,'2' ,'2020-06-11 12:30:00-0000 ');
-- INSERT INTO mastercatalogschema.product_offering (Version_ID, Offering_ID, Product_ID, Record_Status_ID) VALUES('' ,'Instorecatering' ,'mdm-p-5' ,'2');
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-5' ,'00038' ,'web' ,'UOM2' ,'2020-07-11' ,'2020-08-11' ,'4.4'); 
-- INSERT INTO mastercatalogschema.product (Product_ID, Product_Name, Item_SKU, Item_SKU_Size, Product_Description, Serving_Size, EBT_Eligible_Flag, Final_Selling_Item_Flag, Sodium_Disclaimer_Flag, Image_URL, Version_ID, Product_Type_ID, Unit_Of_Measure_ID, Product_Status_Type_ID, Flavor_Type_ID, Record_Status_ID, Product_Friendly_Name, Release_Datetime, SAP_Article_ID) VALUES('mdm-p-5' ,'Extra Catering Sides & Toppings' ,'prod-sap-4' ,'Catering size' ,'Extra Catering Sides & Toppings' ,'1' ,'N' ,'N' ,'N' ,'url5' ,'' ,'FPC' ,'UOM2' ,'' ,'FLAV2' ,'2' ,'Veggie Hoagie' ,'2020-07-15 12:30:00-0000 ' ,'sap-art-5' );
--No storefront exists in CT
INSERT INTO mastercatalogschema.store_product (Store_ID, Product_ID, Price, Version_ID, Record_Status_ID, Release_Datetime) VALUES('00138' ,'mdm-p-3' ,'0' ,'' ,'2' ,'2020-06-11 12:30:00-0000 ');
-- INSERT INTO mastercatalogschema.product_offering (Version_ID, Offering_ID, Product_ID, Record_Status_ID) VALUES('' ,'Instorecatering' ,'mdm-p-3' ,'2');
INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00138' ,'Instorecatering' ,'web' ,'','2' );
INSERT INTO mastercatalogschema.sap_article_price (sap_article_id, store_id, channel_id, unit_of_measure_id, effective_start_Datetime,effective_end_Datetime, price) VALUES('sap-art-3' ,'00138' ,'web' ,'UOM2' ,'2020-07-11' ,'2020-08-11' ,'4.965'); 
INSERT INTO mastercatalogschema.product (Product_ID, Product_Name, Item_SKU, Item_SKU_Size, Product_Description, Serving_Size, EBT_Eligible_Flag, Final_Selling_Item_Flag, Sodium_Disclaimer_Flag, Image_URL, Version_ID, Product_Type_ID, Unit_Of_Measure_ID, Product_Status_Type_ID, Flavor_Type_ID, Record_Status_ID, Product_Friendly_Name, Release_Datetime, SAP_Article_ID) VALUES('mdm-p-3' ,'Shorti Hoagie' ,'prod-sap-2' ,'SINGLE SERVE' ,'Short Hoagie-Featured 1' ,'1' ,'Y' ,'N' ,'N' ,'url3' ,'' ,'FPC' ,'UOM2' ,'' ,'FLAV2' ,'4' ,'Oven Roasted Turkey Hoagie' ,'2020-07-15 12:30:00-0000 ' ,'sap-art-3' );

-- --story 70(Sales Catalog: Create price)

-- -- INSERT INTO mastercatalogschema.product_offering (Version_ID, Offering_ID, Product_ID, Record_Status_ID) VALUES('' ,'Instorecatering' ,'mdm-p-4' ,'2');
-- INSERT INTO mastercatalogschema.product_offering (Version_ID, Offering_ID, Product_ID, Record_Status_ID) VALUES('' ,'curbsideorder' ,'mdm-p-4' ,'2');
-- INSERT INTO mastercatalogschema.product_offering (Version_ID, Offering_ID, Product_ID, Record_Status_ID) VALUES('' ,'weborder' ,'mdm-p-4' ,'2');
-- INSERT INTO mastercatalogschema.product_offering (Version_ID, Offering_ID, Product_ID, Record_Status_ID) VALUES('' ,'webcatering' ,'mdm-p-4' ,'2');
-- INSERT INTO mastercatalogschema.product_offering (Version_ID, Offering_ID, Product_ID, Record_Status_ID) VALUES('' ,'cateringlite' ,'mdm-p-4' ,'2');
-- INSERT INTO mastercatalogschema.product_offering (Version_ID, Offering_ID, Product_ID, Record_Status_ID) VALUES('' ,'Instorecatering' ,'mdm-p-16' ,'2');
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('5102' ,'Instorecatering' ,'web' ,'','2' );
--  INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('7' ,'Instorecatering' ,'web' ,'','2' );
--  INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('7' ,'curbsideorder' ,'web' ,'','2' );
--  INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('7' ,'weborder' ,'web' ,'','2' );
--  INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('7' ,'cateringlite' ,'web' ,'','2' );
--  INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('7' ,'webcatering' ,'web' ,'','2' );
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id, record_status_id, version_id) VALUES('5102', 'Instorecatering' ,'web'  ,'mdm-p-16' ,'2' ,'');
-- INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id, record_status_id, version_id) VALUES('7', 'Instorecatering' ,'web'  ,'mdm-p-4' ,'2' ,'');
-- INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id, record_status_id, version_id) VALUES('7', 'curbsideorder' ,'web'  ,'mdm-p-4' ,'3' ,'' );
-- INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id, record_status_id, version_id) VALUES('7', 'weborder' ,'web'  ,'mdm-p-4' ,'2' ,'');
-- INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id, record_status_id, version_id) VALUES('7', 'webcatering' ,'web'  ,'mdm-p-4' ,'4' ,'');

-- --story 609

-- INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id, record_status_id, create_datetime, version_id) VALUES('5103', 'Instorecatering' ,'web'  ,'mdm-p-51' ,'4' ,'2020-09-29 02:07:51.066+00' ,'');
-- INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id, record_status_id, create_datetime, version_id) VALUES('5103','Instorecatering' ,'web' ,'mdm-p-5' ,'4' ,'2020-09-29 02:07:51.066+00' ,'');
-- INSERT INTO mastercatalogschema.store_offering_channel_product (store_id, offering_id, channel_id, product_id, record_status_id, create_datetime, version_id) VALUES('30', 'Instorecatering' ,'web' ,'mdm-p-5' ,'4' ,'2020-09-29 02:07:53.068+00' ,'');
-- --same sap_article_id , 2 stores

--story 554(GET store component)

INSERT INTO mastercatalogschema.store_component (store_id, component_id, version_id, Record_Status_ID, release_datetime) VALUES('00056' ,'mdm-i-24' ,'', '2','2020-07-15 12:30:00-0000' );
INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00056' ,'Instorecatering' ,'web' ,'','4' );
INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-24' ,'Oven Roasted Turkey' ,'4' ,'Oven Roasted Turkey' ,'' ,'2020-07-13 12:30:00-0000 ' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-41' ,'mdm-item-41' ,'' ,'Test 41' ,'4' ,'url41' ,'' ,'ing-sap-GTIN41' ,'' ,'UOM2' ,'mdm-i-24' ,'IC');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00056' ,'mdm-item-41' ,'0','', '4');

INSERT INTO mastercatalogschema.store_component (store_id, component_id, version_id, Record_Status_ID, release_datetime) VALUES('00054' ,'mdm-i-24' ,'', '2','2020-07-15 12:30:00-0000' );
INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00054' ,'Instorecatering' ,'web' ,'','4' );
INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-24' ,'Oven Roasted Turkey' ,'4' ,'Oven Roasted Turkey' ,'' ,'2020-07-13 12:30:00-0000 ' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-41' ,'mdm-item-41' ,'' ,'Test 41' ,'4' ,'url41' ,'' ,'ing-sap-GTIN41' ,'' ,'UOM2' ,'mdm-i-24' ,'IC');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00054' ,'mdm-item-41' ,'0','', '4');

INSERT INTO mastercatalogschema.store_component (store_id, component_id, version_id, Record_Status_ID, release_datetime) VALUES('00054' ,'mdm-i-4' ,'', '2','2020-07-15 12:30:00-0000' );
-- INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00054' ,'Instorecatering' ,'web' ,'','4' );
INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-4' ,'Yellow Mustard' ,'4' ,'Yellow Mustard' ,'' ,'2020-07-13 12:30:00-0000 ' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-12' ,'mdm-item-12' ,'' ,'Test 12' ,'4' ,'url12' ,'' ,'ing-sap-GTIN12' ,'' ,'UOM2' ,'mdm-i-4' ,'IC');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00054' ,'mdm-item-12' ,'0','', '4');

--one ingredient has multiple items associated
INSERT INTO mastercatalogschema.store_component (store_id, component_id, version_id, Record_Status_ID, release_datetime) VALUES('00054' ,'mdm-i-1' ,'', '2','2020-07-15 12:30:00-0000' );
-- INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00054' ,'Instorecatering' ,'web' ,'','4' );
INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-1' ,'Oven Roasted Turkey' ,'4' ,'Oven Roasted Turkey' ,'' ,'2020-07-13 12:30:00-0000 ' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-1' ,'mdm-item-1' ,'' ,'Test 1' ,'4' ,'url1' ,'' ,'ing-sap-GTIN1' ,'' ,'UOM1' ,'mdm-i-1' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-2' ,'mdm-item-2' ,'' ,'Test 2' ,'4' ,'url2' ,'' ,'ing-sap-GTIN2' ,'' ,'UOM1' ,'mdm-i-1' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-3' ,'mdm-item-3' ,'' ,'Test 3' ,'4' ,'url3' ,'' ,'ing-sap-GTIN3' ,'' ,'UOM2' ,'mdm-i-1' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-4' ,'mdm-item-4' ,'' ,'Test 4' ,'4' ,'url4' ,'' ,'ing-sap-GTIN4' ,'' ,'UOM2' ,'mdm-i-1' ,'IC');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00054' ,'mdm-item-1' ,'0','', '4');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00054' ,'mdm-item-2' ,'0','', '4');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00054' ,'mdm-item-3' ,'0','', '4');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00054' ,'mdm-item-4' ,'0','', '4');

--greater rel date
INSERT INTO mastercatalogschema.store_component (store_id, component_id, version_id, Record_Status_ID, release_datetime) VALUES('00054' ,'mdm-i-35' ,'', '2','2021-07-15 12:30:00-0000' );
-- INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00054' ,'Instorecatering' ,'web' ,'','4' );
INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-35' ,'Cheddar' ,'4' ,'Cheddar' ,'' ,'2020-07-13 12:30:00-0000 ' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-52' ,'mdm-item-52' ,'' ,'Test 52' ,'4' ,'url52' ,'' ,'ing-sap-GTIN52' ,'' ,'UOM2' ,'mdm-i-35' ,'IC');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00054' ,'mdm-item-52' ,'0','', '4');
--different rcd_status
INSERT INTO mastercatalogschema.store_component (store_id, component_id, version_id, Record_Status_ID, release_datetime) VALUES('00054' ,'mdm-i-50' ,'', '4','2020-07-15 12:30:00-0000' );
-- INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00054' ,'Instorecatering' ,'web' ,'','4' );
INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-50' ,'Light Cream Cheese' ,'2' ,'Light Cream Cheese' ,'' ,'2020-07-11 12:30:00-0000 ' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-67' ,'mdm-item-67' ,'' ,'Test 67' ,'2' ,'url67' ,'' ,'ing-sap-GTIN67' ,'' ,'UOM2' ,'mdm-i-50' ,'IC');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00054' ,'mdm-item-67' ,'0','', '4');
--ingredient not published
INSERT INTO mastercatalogschema.store_component (store_id, component_id, version_id, Record_Status_ID, release_datetime) VALUES('00054' ,'mdm-i-3' ,'', '2','2020-07-15 12:30:00-0000' );
-- INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00054' ,'Instorecatering' ,'web' ,'','4' );
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-9' ,'mdm-item-9' ,'' ,'Test 9' ,'4' ,'url9' ,'' ,'ing-sap-GTIN9' ,'' ,'UOM1' ,'mdm-i-3' ,'IC');
-- INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-3' ,'Vinegar' ,'4' ,'Vinegar' ,'' ,'2020-07-13 12:30:00-0000 ' ,'IC');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00054' ,'mdm-item-9' ,'0','', '4');
-- No item association
INSERT INTO mastercatalogschema.store_component (store_id, component_id, version_id, Record_Status_ID, release_datetime) VALUES('00054' ,'mdm-i-51' ,'', '2','2020-07-15 12:30:00-0000' );
-- INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00054' ,'Instorecatering' ,'web' ,'','4' );
INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-51' ,'Veggie Cream Cheese' ,'2' ,'Veggie Cream Cheese' ,'' ,'2020-07-11 12:30:00-0000 ' ,'IC');

INSERT INTO mastercatalogschema.store_component (store_id, component_id, version_id, Record_Status_ID, release_datetime) VALUES('00054' ,'mdm-i-52' ,'', '2','2020-07-15 12:30:00-0000' );
-- INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00054' ,'Instorecatering' ,'web' ,'','4' );
INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-52' ,'Mixed Berry Cream Cheese' ,'2' ,'Mixed Berry Cream Cheese' ,'' ,'2020-07-11 12:30:00-0000 ' ,'IC');
INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-52' ,'Mixed Berry Cream Cheese' ,'4' ,'Mixed Berry Cream Cheese' ,'' ,'2020-07-11 12:30:00-0000 ' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-69' ,'mdm-item-69' ,'' ,'Test 69' ,'2' ,'url69' ,'' ,'ing-sap-GTIN69' ,'' ,'UOM2' ,'mdm-i-52' ,'IC');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00054' ,'mdm-item-69' ,'0','', '4');
--item not published
INSERT INTO mastercatalogschema.store_component (store_id, component_id, version_id, Record_Status_ID, release_datetime) VALUES('00054' ,'mdm-i-53' ,'', '2','2020-07-15 12:30:00-0000' );
-- INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00054' ,'Instorecatering' ,'web' ,'','4' );
INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-53' ,'Hazelnut' ,'4' ,'Hazelnut' ,'' ,'2020-07-11 12:30:00-0000 ' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-70' ,'mdm-item-70' ,'' ,'Test 70' ,'2' ,'url70' ,'' ,'ing-sap-GTIN70' ,'' ,'UOM2' ,'mdm-i-53' ,'IC');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00054' ,'mdm-item-70' ,'0','', '4');
--no storefront created in CT
INSERT INTO mastercatalogschema.store_component (store_id, component_id, version_id, Record_Status_ID, release_datetime) VALUES('00154' ,'mdm-i-4' ,'', '2','2020-07-15 12:30:00-0000' );
INSERT INTO mastercatalogschema.store_offering_channel (store_id , offering_id, channel_id, version_id, record_status_id) VALUES('00154' ,'Instorecatering' ,'web' ,'','4' );
INSERT INTO mastercatalogschema.ingredient (Ingredient_ID, Ingredient_Name, Record_Status_ID, Ingredient_Description, Version_ID, Release_Datetime, Product_Type_ID) VALUES('mdm-i-4' ,'Yellow Mustard' ,'4' ,'Yellow Mustard' ,'' ,'2020-07-13 12:30:00-0000 ' ,'IC');
INSERT INTO mastercatalogschema.item (SAP_Article_ID, Item_ID, Item_Name, Item_Description, Record_Status_ID, Image_URL, Version_ID, Item_SKU, Item_SKU_Size, Unit_Of_Measure_ID, Ingredient_ID, Product_Type_ID) VALUES('sap-12' ,'mdm-item-12' ,'' ,'Test 12' ,'4' ,'url12' ,'' ,'ing-sap-GTIN12' ,'' ,'UOM2' ,'mdm-i-4' ,'IC');
INSERT INTO mastercatalogschema.store_item (store_id, item_id, price, version_id, Record_Status_ID) VALUES('00154' ,'mdm-item-12' ,'0','', '4');

