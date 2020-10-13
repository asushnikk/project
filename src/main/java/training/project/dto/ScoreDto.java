package training.project.dto;

import lombok.Data;
import training.project.domain.Operation;
import training.project.domain.User;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ScoreDto {

    @NotNull
    private String name;

    private List<User> users;

    private Integer balance;

    private Integer limit;

    private User owner;

    private List<Operation> operations;
}