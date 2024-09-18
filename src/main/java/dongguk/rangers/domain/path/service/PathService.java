package dongguk.rangers.domain.path.service;

import dongguk.rangers.domain.path.dto.PathDTO.PathResponseDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;
import dongguk.rangers.domain.path.dto.DangerDTO.DangerResponseDTO;
import dongguk.rangers.domain.path.dto.SafeDTO.SafeResponseDTO;

import java.util.List;

public interface PathService {

    // userId로 경로를 조회하는 메소드 추가
    public PathResponseDTO getPathByUserId(Long userId);
    public PathResponseDTO savePath(PathRequestDTO pathRequestDTO);
    public List<DangerResponseDTO> getAllDangerousZones();
    public List<SafeResponseDTO> getAllSafeZones();
    public PathResponseDTO completePath(Long pathId);
}
