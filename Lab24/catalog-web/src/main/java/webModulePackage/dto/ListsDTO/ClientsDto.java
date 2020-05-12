package webModulePackage.dto.ListsDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webModulePackage.dto.EntitiesDTO.ClientDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientsDto {
    private List<ClientDto> clientDtos;
}
