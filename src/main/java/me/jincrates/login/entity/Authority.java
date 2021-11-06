package me.jincrates.login.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "authority") @ApiModel(value = "권한 Entity")
public class Authority {

    @ApiModelProperty(value = "권한", required = true)
    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}
