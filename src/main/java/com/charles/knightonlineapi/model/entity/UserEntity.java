package com.charles.knightonlineapi.model.entity;

import com.charles.knightonlineapi.enums.GenderEnum;
import com.charles.knightonlineapi.enums.RoleEnum;
import com.charles.knightonlineapi.enums.StatusEnum;
import com.charles.knightonlineapi.model.dto.UserBasicDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Entity
@NamedNativeQuery(
        name = "find_user_basic_dto",
        query = """
        select *,
            u.level * 100 as life,
            u.level * 200 as armor,
            u.level * 50 as damage,
            case
                when cast(u.level * 15 / 100.50 as numeric(10, 2)) > 100 then 100
                else cast(u.level * 15 / 100.50 as numeric(10, 2))
                end as criticalRate,
            case
                when cast(u.level + 100 * 2 / 2.1 as numeric(10, 2)) > 1000 then 1000
                else cast(u.level + 100 * 2 / 2.1 as numeric(10, 2))
                end as criticalDamage
            from tb_user u where u.id = :id
        """,
        resultSetMapping = "user_basic_dto"
)
@SqlResultSetMapping(
        name = "user_basic_dto",
        classes = @ConstructorResult(
                targetClass = UserBasicDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "gender", type = GenderEnum.class),
                        @ColumnResult(name = "level", type = Integer.class),
                        @ColumnResult(name = "experience", type = Long.class),
                        @ColumnResult(name = "gold", type = Long.class),
                        @ColumnResult(name = "silver", type = Long.class),
                        @ColumnResult(name = "trophy", type = Long.class),
                        @ColumnResult(name = "life", type = Long.class),
                        @ColumnResult(name = "armor", type = Long.class),
                        @ColumnResult(name = "damage", type = Long.class),
                        @ColumnResult(name = "criticalRate", type = BigDecimal.class),
                        @ColumnResult(name = "criticalDamage", type = BigDecimal.class)
                }
        )
)
@Table(name = "tb_user")
public class UserEntity implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", unique = true, length = 20)
    private String name;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "level", nullable = false)
    private Integer level = 1;

    @Column(name = "experience", nullable = false)
    private Long experience = 0L;

    @Column(name = "gold", nullable = false)
    private Long gold = 0L;

    @Column(name = "silver", nullable = false)
    private Long silver = 5000L;

    @Column(name = "trophy", nullable = false)
    private Long trophy = 0L;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.USER;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return email;
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
        return StatusEnum.ACTIVE.equals(status);
    }
}
