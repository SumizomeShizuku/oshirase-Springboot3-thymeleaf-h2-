package com.anestec.hello.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "CATEGORY", uniqueConstraints = @UniqueConstraint(columnNames = "KUBUN"))  // 为 KUBUN 添加唯一约束
public class Category {

    @Id
    private Long id;  // 主键

    @Column(nullable = false, name = "KUBUN")
    private String kubun;  // 状态值，例如 0, 1, 5

    @Column(nullable = false, name = "DISPLAYNAME")
    private String displayName;  // 显示名称，例如 "无", "有", "恢复中"

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKubun() {
        return kubun;
    }

    public void setKubun(String kubun) {
        this.kubun = kubun;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
