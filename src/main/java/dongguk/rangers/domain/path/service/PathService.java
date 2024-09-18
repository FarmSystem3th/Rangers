package dongguk.rangers.domain.path.service;

import dongguk.rangers.domain.path.dto.PathDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;

public interface PathService {

    public PathDTO.PathResponseDTO savePath(PathRequestDTO pathRequestDTO);
}
