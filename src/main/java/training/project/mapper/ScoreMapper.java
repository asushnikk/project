package training.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import training.project.domain.Score;
import training.project.domain.User;
import training.project.dto.ScoreDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface ScoreMapper {

    @Mapping(target = "owner", source = "user")
    @Mapping(target = "name", source = "scoreDto.name")
    @Mapping(target = "limit", expression = "java(setLimit(scoreDto))")
    @Mapping(target = "users", expression = "java(setUsers(scoreDto, user))")
    @Mapping(target = "id", ignore = true)
    Score toScoreWithOwner(ScoreDto scoreDto, User user);

    @Mapping(target = "id", source = "scoreId")
    @Mapping(target = "owner", source = "user")
    @Mapping(target = "name", source = "score.name")
    @Mapping(target = "limit", expression = "java(setLimit(score))")
    @Mapping(target = "users", expression = "java(setUsers(score, user))")
    Score toScoreWithOwnerAndId(ScoreDto scoreDto, User user, Long scoreId);

    default Integer setLimit(ScoreDto scoreDto) {
        if (scoreDto.getLimit() == null) {
            scoreDto.setLimit(scoreDto.getBalance());
        }

        return scoreDto.getLimit();
    }

    default List<User> setUsers(ScoreDto score, User user) {
        Stream<Long> all = score.getUsers().stream()
                .filter(Objects::nonNull)
                .map(User::getId);

        if (all.noneMatch(u -> u.equals(user.getId()))) {
            score.getUsers().add(user);
        }

        return score.getUsers();
    }
}