package lk.rythmo.userauth.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

@Getter
@Setter
@Entity(name = "user_table")
public class User implements UserDetails {
    @Id
    private String id;
    private String username;
    private String password;
    private static final long serialVersionUID = 2396654715019746670L;

    @JsonCreator
    public User(@JsonProperty("id")  String id,
                @JsonProperty("username") String username,
                @JsonProperty("password") String password) {
        super();
        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
    }

    public User() {
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
