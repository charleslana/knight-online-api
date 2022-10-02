package com.charles.knightonlineapi.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_item")
public class ItemEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "item_sequence", sequenceName = "item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sequence")
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

    @Column(name = "critical damage")
    private Integer criticalDamage;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
