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
                .map(Announcement::getCategory) // 提取 email 字段
                .distinct() // 去除重复的邮箱
                .collect(Collectors.toList());
    }

    public List<Category> getAllKubun() {
        return categoryRepository.findAll();
    }

}
