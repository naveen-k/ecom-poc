package com.test.catalog.sales.db.repository.product;

import com.test.catalog.sales.db.dto.ProductCategory;
import com.test.catalog.sales.db.entity.product.StoreProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface  ProductCategoryRepository extends JpaRepository<StoreProductEntity, Integer> {

    @Query(value = "select pc.product_category_key,CAST(pc.category_id AS VARCHAR),CAST(pc.product_id AS VARCHAR),pc.record_status_id,pc.display_sequence_number," +
            "pc.create_datetime,pc.version_id from  {h-schema}product_category pc  INNER JOIN " +
            "            ( SELECT category_id,product_id, " +
            "              MAX(create_datetime) AS maxdate from  {h-schema}product_category group by " +
            "    category_id,product_id) resultset1 " +
            "    on   pc.category_id  = resultset1.category_id " +
            "    and   pc.product_id  = resultset1.product_id and   pc.record_status_id  in(:recordStatusId ) " +
            "    and   pc.category_id in " +
            "            (select pc_inner.category_id from  {h-schema}product_category pc_inner INNER JOIN ( " +
            "                    SELECT MAX(create_datetime) AS maxdate ,product_id, category_id " +
            "    FROM  {h-schema}product_category group by " +
            "            (product_id, category_id))  resultset2 " +
            "    on  pc_inner.category_id  = resultset2.category_id " +
            "    and   pc_inner.product_id = resultset2.product_id " +
            "    and pc_inner.record_status_id In (:recordStatusId) " +
            "    and   pc_inner.create_datetime = resultset2.maxdate " +
            "    and  (pc_inner.release_datetime IS NULL " +
            "                    OR pc_inner.release_datetime>=(select timezone('UTC', now())))  ) " +
            "    and    pc.create_datetime = resultset1.maxdate " +
            "    WHERE   pc.product_id in(:productIds)",
    nativeQuery = true)
    List<ProductCategory> findLatest(@Param("productIds") List<UUID> productIds, @Param("recordStatusId") int recordStatusId);

}
