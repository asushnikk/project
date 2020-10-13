package training.project.dto;

import lombok.Data;
import training.project.domain.Score;
import training.project.enums.Category;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class OperationDto {

    private Score score;

    @Min(value = 0, message = ">0")
    private Integer amount;

    private LocalDateTime created;

    @NotNull
    private Category category;
}