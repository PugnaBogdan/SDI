package webModulePackage.converter;

import coreModulePackage.Entities.Client;
import org.springframework.stereotype.Component;
import webModulePackage.converter.BaseConverter;
import webModulePackage.dto.EntitiesDTO.ClientDto;

@Component
public class ClientConverter extends BaseConverter<Client, ClientDto> {
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        Client client = Client.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .build();
        client.setId(dto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto dto = ClientDto.builder()
                .name(client.getName())
                .age(client.getAge())
                .build();
        dto.setId(client.getId());
        return dto;
    }
}
