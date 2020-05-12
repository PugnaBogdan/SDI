package webModulePackage.dto.EntitiesDTO;

import lombok.*;
import webModulePackage.dto.BaseDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ClientDto extends BaseDto {
    private String name;
    private int age;
}
