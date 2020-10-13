package training.project.mapper;

import org.mapstruct.Mapper;
import training.project.domain.Operation;
import training.project.dto.OperationDto;

@Mapper(componentModel = "spring")
public interface OperationMapper {
    Operation toOperation(OperationDto operationDto);
}