package training.project.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String password;
}