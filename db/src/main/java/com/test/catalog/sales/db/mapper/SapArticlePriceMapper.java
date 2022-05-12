package com.test.catalog.sales.db.mapper;

import com.test.catalog.sales.db.dto.SapArticlePriceDto;
import com.test.catalog.sales.db.entity.price.SapArticlePriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SapArticlePriceMapper {

    public List<SapArticlePriceDto> sapArticlePriceEntityListToSapArticlePriceDtoList(List<SapArticlePriceEntity> sapArticlePrices, @MappingTarget List<SapArticlePriceDto> sapArticlePriceDtos);

    public SapArticlePriceDto sapArticlePriceEntityToSapArticlePriceDto(SapArticlePriceEntity sapArticlePrice, @MappingTarget SapArticlePriceDto sapArticlePriceDto);

    public SapArticlePriceEntity sapArticlePriceDtoListToSapArticlePriceEntity(SapArticlePriceDto sapArticlePricesDtos, @MappingTarget SapArticlePriceEntity sapArticlePriceEntity);

}
