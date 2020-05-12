package webModulePackage.dto.ListsDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webModulePackage.dto.EntitiesDTO.MovieDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MoviesDto {
    private List<MovieDto> movieDtos;
}
