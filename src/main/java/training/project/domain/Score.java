package training.project.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"id", "name"})
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "score2user",
            joinColumns = {@JoinColumn(name = "score_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users;

    @NotNull
    private Integer balance;

    @Column(name = "\"limit\"")
    private Integer limit;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "own_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "score")
    private List<Operation> operations;
}