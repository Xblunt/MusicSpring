package dev.MusicSpring.db.entities.auth;

import dev.MusicSpring.db.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="user_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleUserEntity extends BaseEntity {
    private String username;
    @Enumerated(EnumType.STRING)
    private BaseRole role;
}
