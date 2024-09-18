package dongguk.rangers.domain.path.service;

import dongguk.rangers.domain.path.dto.PathDTO.PathResponseDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;
import dongguk.rangers.domain.path.dto.DangerDTO.DangerResponseDTO;
import dongguk.rangers.domain.path.dto.SafeDTO.SafeResponseDTO;

import java.util.List;

public interface PathService {

    public PathResponseDTO savePath(PathRequestDTO pathRequestDTO);
    public List<DangerResponseDTO> getAllDangerousZones();
    public List<SafeResponseDTO> getAllSafeZones();

    public PathResponseDTO completePath(Long pathId);
}
