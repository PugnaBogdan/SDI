package webModulePackage.dto.EntitiesDTO;

import lombok.*;
import webModulePackage.dto.BaseDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class RentDto extends BaseDto {
    private int clientID;
    private int movieId;
}
