package webModulePackage.dto.EntitiesDTO;

import lombok.*;
import webModulePackage.dto.BaseDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class MovieDto extends BaseDto {
    private String title;
    private int price;
}
