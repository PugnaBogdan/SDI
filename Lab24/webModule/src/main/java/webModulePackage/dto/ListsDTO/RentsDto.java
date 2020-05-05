package webModulePackage.dto.ListsDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webModulePackage.dto.EntitiesDTO.RentDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentsDto {
    private List<RentDto> rentsDtos;
}
