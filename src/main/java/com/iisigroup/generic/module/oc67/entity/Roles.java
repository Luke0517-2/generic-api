package com.iisigroup.generic.module.oc67.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;


/**
 * 角色
 */
@Data
@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "角色")
public class Roles implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 序號 primary key
     */
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    @Schema(description = "序號 primary key")
    private UUID id;

    /**
     * 是否為系統預設，不可刪除編
     */
    @Column(name = "system_default", nullable = false)
    @NotNull(message = "systemDefault is required")
    @Schema(description = "是否為系統預設，不可刪除編")
    private Integer systemDefault;

    /**
     * 角色名稱
     */
    @Column(name = "role_name", nullable = false)
    @NotNull(message = "roleName is required")
    @Schema(description = "角色名稱")
    private String roleName;

    /**
     * companies.ID, 公司用戶可以自建角色
     */
    @Column(name = "company_id")
    @Schema(description = "companies.ID, 公司用戶可以自建角色")
    private UUID companyId;

    /**
     * 建立日期/時間
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "建立日期/時間")
    private OffsetDateTime createdAt;

    /**
     * 編輯日期/時間
     */
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "編輯日期/時間")
    private OffsetDateTime updatedAt;

    /**
     * 建立者帳號
     */
    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    @Schema(description = "建立者帳號")
    private String createdBy;

    /**
     * 編輯者帳號
     */
    @Column(name = "updated_by", nullable = false)
    @LastModifiedBy
    @Schema(description = "編輯者帳號")
    private String updatedBy;

}