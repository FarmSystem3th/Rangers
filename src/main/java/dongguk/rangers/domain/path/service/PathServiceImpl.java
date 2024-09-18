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
}
