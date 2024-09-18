package dongguk.rangers.domain.path.service;


import dongguk.rangers.domain.path.converter.DangerConverter;
import dongguk.rangers.domain.path.converter.PathConverter;
import dongguk.rangers.domain.path.dto.DangerDTO;
import dongguk.rangers.domain.path.entity.Danger;
import dongguk.rangers.domain.path.entity.Path;
import dongguk.rangers.domain.path.repository.DangerRepository;
import dongguk.rangers.domain.path.repository.PathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dongguk.rangers.domain.path.dto.PathDTO.PathResponseDTO;
import dongguk.rangers.domain.path.dto.PathDTO.PathRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PathServiceImpl implements PathService {

    private final PathRepository pathRepository;
    private final DangerRepository dangerRepository;

    public PathResponseDTO savePath(PathRequestDTO pathRequestDTO) {
        Path path = PathConverter.toPath(pathRequestDTO);
        path = pathRepository.save(path);

        return PathConverter.toSavePathResponse(path);
    }

    public List<DangerDTO.DangerResponseDTO> getAllDangerousZones() {
        List<Danger> zones = dangerRepository.findAll();
        return zones.stream()
                .map(DangerConverter::toDangerResponseDTO)
                .collect(Collectors.toList());
    }
}
