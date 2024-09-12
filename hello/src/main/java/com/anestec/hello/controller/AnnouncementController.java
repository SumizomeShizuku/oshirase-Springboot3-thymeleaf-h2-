package com.anestec.hello.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anestec.hello.model.Announcement;
import com.anestec.hello.model.Category;
import com.anestec.hello.service.AnnouncementService;

@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    // @GetMapping("/oshirase")
    // public String getUsers(Model model) {
    //     List<Announcement> oshiraselist = announcementService.getAllOshirase();
    //     model.addAttribute("oshirase", oshiraselist);
    //     return "oshiraselist"; // Thymeleaf 模板名称
    // }
    @GetMapping("/oshirase")
    public String getUsers(Model model,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "registrationDate", required = false) String registrationDate,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "page", defaultValue = "0") int page, // 页码参数，默认第 0 页
            @RequestParam(value = "size", defaultValue = "10") int size) { // 每页条数，默认 10 条
        Pageable pageable = PageRequest.of(page, size);
        Page<Announcement> oshiraselist;

        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String regDate = null;
        if (registrationDate != null && !registrationDate.isEmpty()) {
            LocalDate parsedDate = LocalDate.parse(registrationDate, inputDateFormatter);
            regDate = parsedDate.format(outputDateFormatter);
        }
        String sDate = null;
        if (startDate != null && !startDate.isEmpty()) {
            LocalDate parsedDate = LocalDate.parse(startDate, inputDateFormatter);
            sDate = parsedDate.format(outputDateFormatter);
        }
        String eDate = null;
        if (endDate != null && !endDate.isEmpty()) {
            LocalDate parsedDate = LocalDate.parse(endDate, inputDateFormatter);
            eDate = parsedDate.format(outputDateFormatter);
        }
        // LocalDateTime regDate = (registrationDate != null && !registrationDate.isEmpty()) ? LocalDate.parse(registrationDate, formatter).atTime(LocalTime.now()) : null;
        // LocalDateTime sDate = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate, formatter).atTime(LocalTime.now()) : null;
        // LocalDateTime eDate = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate, formatter).atTime(LocalTime.now()) : null;
        // String regDate = (registrationDate != null && !registrationDate.isEmpty()) ? registrationDate : null;
        // String sDate = (startDate != null && !startDate.isEmpty()) ? startDate : null;
        // String eDate = (endDate != null && !endDate.isEmpty()) ? endDate : null;

        if ((title == null || title.isBlank()) && (category == null || category.isBlank())
                && regDate == null && sDate == null && eDate == null) {
            oshiraselist = announcementService.getAllOshirase(pageable);
        } else {
            oshiraselist = announcementService.searchAnnouncement(title, category, regDate, sDate, eDate, pageable);
        }

        // List<String> allCategory = announcementService.getAllCategory();
        List<Category> kubunList = announcementService.getAllKubun();

        // 将 yyyyMMdd 格式的日期转换为 yyyy-MM-dd 格式
        String regDay = formatBackendDate(regDate);
        String startDay = formatBackendDate(sDate);
        String endDay = formatBackendDate(eDate);

        // String regDayforTable = formatFrontendDate(regDate);
        // String startDayforTable = formatFrontendDate(sDate);
        // String endDayforTable = formatFrontendDate(eDate);
        // model.addAttribute("oshirase", oshiraselist);
        model.addAttribute("oshirase", oshiraselist.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", oshiraselist.getTotalPages());
        model.addAttribute("totalItems", oshiraselist.getTotalElements());

        model.addAttribute("title", title); // 用 'username' 来传递 'title' 的值
        model.addAttribute("category", category);    // 回显已选择的 category
        // model.addAttribute("registrationDate", registrationDate);
        // model.addAttribute("startDate", startDate);
        // model.addAttribute("endDate", endDate);

        model.addAttribute("registrationDate", regDay);
        model.addAttribute("startDate", startDay);
        model.addAttribute("endDate", endDay);
        System.out.println("Start Date: " + sDate + "  " + startDate);
        System.out.println("End Date: " + eDate + "  " + endDate);

        model.addAttribute("regDayforTable", regDate);
        model.addAttribute("startDayforTable", sDate);
        model.addAttribute("endDayforTable", eDate);

        model.addAttribute("allCategory", kubunList); // 下拉菜单的数据
        return "oshiraselist"; // Thymeleaf 模板名称
    }

    // @GetMapping("/")
    // public String startpage(Model model) {
    //     List<Announcement> oshiraselist = announcementService.getAllOshirase();
    //     model.addAttribute("oshirase", oshiraselist);
    //     return "oshiraselist"; // Thymeleaf 模板名称
    // }
    String formatBackendDate(String date) {
        if (date != null && !date.isEmpty()) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        }
        return null;
    }

    // String formatFrontendDate(String date) {
    //     if (date != null && !date.isEmpty()) {
    //         return date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6, 8);
    //     }
    //     return null;
    // }
}
