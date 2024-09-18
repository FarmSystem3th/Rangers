package dongguk.rangers.domain.path.service;

import dongguk.rangers.domain.path.dto.PathDTO.PathResponseDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;
import dongguk.rangers.domain.path.dto.DangerDTO.DangerResponseDTO;

import java.util.List;

public interface PathService {

    public PathResponseDTO savePath(PathRequestDTO pathRequestDTO);
    public List<DangerResponseDTO> getAllDangerousZones();
}
