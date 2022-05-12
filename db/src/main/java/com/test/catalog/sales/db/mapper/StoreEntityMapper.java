package com.test.catalog.sales.db.mapper;

import com.test.catalog.sales.db.dto.*;
import com.test.catalog.sales.db.entity.price.PriceStatusHistoryEntity;
import com.test.catalog.sales.db.entity.store.StoreEntity;
import com.test.catalog.sales.db.entity.store.StoreOfferingChannelEntity;
import com.test.catalog.sales.db.entity.product.StoreProductEntity;
import com.test.catalog.sales.db.entity.store.StoreOfferingChannelProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreEntityMapper {

    public List<StoreProductDto> storeProductEntityListToStoreProductDtoList(List<StoreProductEntity> storeProductEntities, @MappingTarget List<StoreProductDto> storeProductDtos);

    public List<StoreOfferingChannelDto> storeOfferingChannelEntityListToStoreOfferingChannelDtoList(List<StoreOfferingChannelEntity> storeOfferingChannelEntities, @MappingTarget List<StoreOfferingChannelDto> storeOfferingChannelDtos);

    public List<StoreOfferingChannelEntity> storeOfferingChannelDtoListToStoreOfferingChannelEntityList(List<StoreOfferingChannelDto> storeOfferingChannelDtos, @MappingTarget List<StoreOfferingChannelEntity> storeOfferingChannelEntities);

    List<StoreOfferingChannelProductDto> storeOfferingChannelProductEntityListToDtoList(List<StoreOfferingChannelProductEntity> storeOfferingChannelProductEntities, @MappingTarget List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos);

    StoreEntity storeDtoToStoreEntity(StoreDto storeDto, @MappingTarget StoreEntity storeEntity);

    StoreDto storeEntityToStoreDto( StoreEntity storeEntity, @MappingTarget StoreDto storeDto);

    StoreOfferingChannelDto storeOfferingChannelEntityToStoreOfferingChannelDto(StoreOfferingChannelEntity storeOfferingChannelEntity,@MappingTarget StoreOfferingChannelDto storeOfferingChannelDto );

    @Mapping(target = "releaseDate", ignore = true)
    StoreOfferingChannelProductDto storeOfferingChannelProductEntityToDto(StoreOfferingChannelProductEntity storeOfferingChannelProductEntity, @MappingTarget StoreOfferingChannelProductDto storeOfferingChannelProductDto);

    StoreOfferingChannelProductEntity storeOfferingChannelProductDtoListToEntity(StoreOfferingChannelProductDto storeOfferingChannelProductDto, @MappingTarget StoreOfferingChannelProductEntity storeOfferingChannelProductEntity);

    StoreProductDto storeProductEntityToStoreProductDto(StoreProductEntity storeProductEntity, @MappingTarget StoreProductDto storeProductDto);

    StoreProductEntity storeProductDtoToStoreProductEntity(StoreProductDto storeProductDto, @MappingTarget StoreProductEntity storeProductEntity);

    PriceStatusHistoryDto storePriceStatusHistoryToPriceStatusHistoryDto(PriceStatusHistoryEntity priceStatusHistoryEntity, @MappingTarget PriceStatusHistoryDto priceStatusHistoryDto);

    PriceStatusHistoryEntity priceStatusHistoryDtoToPriceStatusHistoryEntity(PriceStatusHistoryDto priceStatusHistoryDto, @MappingTarget PriceStatusHistoryEntity priceStatusHistoryEntity);

}
