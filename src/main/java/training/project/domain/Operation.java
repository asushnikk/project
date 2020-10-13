package training.project.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import training.project.enums.Category;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(of = {"id", "amount"})
@ToString(of = {"id", "amount"})
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "score_id")
    private Score score;

    @NotNull
    private Integer amount;

    @NotNull
    private LocalDateTime created;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;
}
