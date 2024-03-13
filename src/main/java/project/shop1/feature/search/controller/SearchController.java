//package project.shop1.feature.search.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import project.shop1.common.validation.ValidationSequence;
//import project.shop1.feature.search.repository.SearchRepository;
//import project.shop1.feature.search.service.SearchService;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class SearchController {
//
//    private final SearchService searchService;
//    private final SearchRepository searchRepository;
//
//    @PostMapping("/searchRanking")
//    public List<String> searchRanking(){
//        return searchRepository.searchRanking();
//    }
//
//}
