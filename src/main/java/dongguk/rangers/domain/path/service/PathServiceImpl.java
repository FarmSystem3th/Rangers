package dongguk.rangers.domain.path.service;


import dongguk.rangers.domain.path.converter.PathConverter;
import dongguk.rangers.domain.path.entity.Path;
import dongguk.rangers.domain.path.entity.PathState;
import dongguk.rangers.domain.path.repository.PathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dongguk.rangers.domain.path.dto.PathDTO.PathResponseDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PathServiceImpl implements PathService {

    private final PathRepository pathRepository;

    public PathResponseDTO savePath(PathRequestDTO pathRequestDTO) {
        Path path = PathConverter.toPath(pathRequestDTO);
        path = pathRepository.save(path);

        return PathConverter.toSavePathResponse(path);
    }

    // userId로 경로를 조회하는 메소드 구현
    @Override
    public PathResponseDTO getPathByUserId(Long userId) {
        Path path = pathRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 경로를 찾을 수 없습니다."));
        return PathConverter.toSavePathResponse(path);
    }
}
