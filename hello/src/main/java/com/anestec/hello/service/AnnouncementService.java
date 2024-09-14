package com.anestec.hello.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.anestec.hello.model.Announcement;
import com.anestec.hello.model.Category;
import com.anestec.hello.repository.AnnouncementRepository;
import com.anestec.hello.repository.CategoryRepository;
import com.anestec.hello.specification.AnnouncementSpecification;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Announcement> getAllOshirase(Pageable pageable) {
        Specification<Announcement> spec = (root, query, criteriaBuilder) -> {
            // 查询 delete_flg 不等于 "1" 的记录
            // return criteriaBuilder.notEqual(root.get("deleteFlg"), "1");
            return criteriaBuilder.or(
                    criteriaBuilder.notEqual(root.get("deleteFlg"), "1"),
                    criteriaBuilder.isNull(root.get("deleteFlg")),
                    criteriaBuilder.equal(root.get("deleteFlg"), "")
            );
        };
        return announcementRepository.findAll(spec, pageable);
    }

    public Page<Announcement> searchAnnouncement(String title, String category, String registrationDate, String startDate, String endDate, Pageable pageable) {
        Specification<Announcement> result
                = AnnouncementSpecification.filterByConditions(title, category, registrationDate, startDate, endDate);
        return announcementRepository.findAll(result, pageable);
    }

    public List<String> getAllCategory() {
        return announcementRepository.findAll().stream()
                .map(Announcement::getCategory) // 提取 Category 字段
                .distinct() // 去除重复的邮箱
                .collect(Collectors.toList());
    }

    public List<Category> getAllKubun() {
        return categoryRepository.findAll();
    }

    public void saveAnnouncement(String title, String category, String registrationDate, String startDate, String endDate,
            String infomessage, String deleteFlg, String createUser, String updateUser) {

        // 将数据保存到数据库
        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setCategory(category);
        announcement.setRegistrationDate(registrationDate);
        announcement.setStartDate(startDate);
        announcement.setEndDate(endDate);
        announcement.setInfomessage(infomessage);
        announcement.setDeleteFlg(deleteFlg);
        announcement.setCreateUser(createUser);
        announcement.setUpdateUser(updateUser);

        announcementRepository.save(announcement);  // 保存到数据库
    }

    public void updateAnnouncement(Long id, String title, String category, String registrationDate, String startDate, String endDate) {

        // 将数据保存到数据库
        Announcement announcement = findById(id);
        if (announcement != null) {

            announcement.setTitle(title);
            announcement.setCategory(category);
            announcement.setRegistrationDate(registrationDate);
            announcement.setStartDate(startDate);
            announcement.setEndDate(endDate);
            // announcement.setInfomessage(infomessage);
            // announcement.setDeleteFlg(deleteFlg);
            // announcement.setCreateUser(createUser);
            // announcement.setUpdateUser(updateUser);

            announcementRepository.save(announcement);  // 保存到数据库
        }
    }

    public void deleteAnnouncement(Long id) {

        // 将数据保存到数据库
        Announcement announcement = findById(id);
        if (announcement != null) {

            announcement.setDeleteFlg("1");

            announcementRepository.save(announcement);  // 保存到数据库
        }
    }

    public Announcement findById(Long id) {
        return announcementRepository.findById(id).orElse(null);
    }

    // public void updateAnnouncement(Announcement announcement) {
    //     announcementRepository.save(announcement);  // 保存更新后的记录
    // }
}
