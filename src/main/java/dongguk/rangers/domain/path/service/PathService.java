package dongguk.rangers.domain.path.service;

import dongguk.rangers.domain.path.dto.PathDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;

public interface PathService {

    public PathDTO.PathResponseDTO savePath(PathRequestDTO pathRequestDTO);

    // userId로 경로를 조회하는 메소드 추가
    PathDTO.PathResponseDTO getPathByUserId(Long userId);
}
