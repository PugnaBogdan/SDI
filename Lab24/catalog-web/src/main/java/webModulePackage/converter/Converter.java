package webModulePackage.converter;

import coreModulePackage.Entities.BaseEntity;
import webModulePackage.dto.BaseDto;

public interface Converter<Model extends BaseEntity<Integer>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}

