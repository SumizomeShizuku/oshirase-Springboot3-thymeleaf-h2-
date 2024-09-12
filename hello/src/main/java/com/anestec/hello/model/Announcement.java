package com.anestec.hello.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "ASMS_INFORMATION")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INFORMATION_ID")
    private Long id;

    @Column(name = "INFORMATION_TITLE")
    private String title;

    @Column(name = "INFORMATION_KBN")
    private String category;

    @Column(name = "KEISAI_YMD")
    private String registrationDate;

    @Column(name = "ENABLE_START_YMD")
    private String startDate;

    @Column(name = "ENABLE_END_YMD")
    private String endDate;

    @Column(name = "DELETE_FLG")
    private String deleteFlg;

    @ManyToOne
    @JoinColumn(name = "INFORMATION_KBN", referencedColumnName = "kubun", insertable = false, updatable = false)
    private Category categoryEntity;

    @Transient
    private String regDayforTable;

    @Transient
    private String startDayforTable;

    @Transient
    private String endDayforTable;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndtDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(String deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    // 通过 kubun 实体获取 displayName
    public String getDisplayName() {
        return categoryEntity != null ? categoryEntity.getDisplayName() : null;
    }

    public String getRegDayforTable() {
        // 你可以在这里格式化日期，例如返回 yyyy/MM/dd 格式的日期
        if (this.registrationDate != null) {
            return this.registrationDate.substring(0, 4) + "/" + this.registrationDate.substring(4, 6) + "/" + this.registrationDate.substring(6, 8);
        }
        return null;
    }

    public void setRegDayforTable(String regDayforTable) {
        this.regDayforTable = regDayforTable;
    }

    // Getter for startDayforTable
    public String getStartDayforTable() {
        if (this.startDate != null) {
            return this.startDate.substring(0, 4) + "/" + this.startDate.substring(4, 6) + "/" + this.startDate.substring(6, 8);
        }
        return null;
    }

    public void setStartDayforTable(String startDayforTable) {
        this.startDayforTable = startDayforTable;
    }

    // Getter for endDayforTable
    public String getEndDayforTable() {
        if (this.endDate != null) {
            return this.endDate.substring(0, 4) + "/" + this.endDate.substring(4, 6) + "/" + this.endDate.substring(6, 8);
        }
        return null;
    }

    public void setEndDayforTable(String endDayforTable) {
        this.endDayforTable = endDayforTable;
    }

}
