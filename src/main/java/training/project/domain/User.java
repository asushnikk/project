package training.project.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Component
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"id", "name"})
@Table(name = "proj_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Score> scores;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Score> ownScores;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singleton((GrantedAuthority) () -> "ROLE_USER");
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}