//package project.shop1.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Data
//public class Category {
//
//    @Id @GeneratedValue
//    @Column(name = "category_id")
//    private Long id;
//
//    /* 카테고리 등급 */
//    private int tier;
//
//    /* 카테고리 이름 */
//    private String name;
//
//    /* 카테고리 번호 */
//    private String code;
//
//    /* 상위 카테고리 */
//    @ManyToOne
//    @JoinColumn(name = "parent_id")
//    private Category parent;
//
//    @OneToMany(mappedBy = "parent")
//    private List<Category> child = new ArrayList<>();
//
//    /* 하위 분류 추가 메서드 */
//    public void addSubcategory(Category subcategory){
//        if (child == null){
//            child = new ArrayList<>();
//        }
//        child.add(subcategory);
//        subcategory.setParent(this);
//    }
//}
