package com.charles.knightonlineapi.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_hero")
public class HeroEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "hero_sequence", sequenceName = "hero_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hero_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "life")
    private Long life;

    @Column(name = "armor")
    private Long armor;

    @Column(name = "damage")
    private Long damage;

    @Column(name = "critical_rate")
    private Integer criticalRate;

    @Column(name = "critical_damage")
    private Integer criticalDamage;

    @Column(name = "block_chance")
    private Integer blockChance;

    @Column(name = "recover_life_chance")
    private Integer recoverLifeChance;

    @Column(name = "double_damage_chance")
    private Integer doubleDamageChance;

    @Column(name = "reflected_damage_chance")
    private Integer reflectedDamageChance;

    @Column(name = "poison_chance")
    private Integer poisonChance;

    @Column(name = "recover_energy_chance")
    private Integer recoverEnergyChance;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "hero", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserHeroEntity> heroEntityList;
}
