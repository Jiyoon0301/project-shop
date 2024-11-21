package project.shop1.domain.productSearch.common;

import lombok.Data;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Criteria {

    /* 현재 페이지 번호 */
    private int pageNumber;

    /* 페이지 표시 개수 */
    private int amount;

    /* 검색 타입 */
    private String type;

    /* 검색 키워드 */
    private String keyword;

    /* 검색 타입 데이터 배열 변환 */
    public String[] getTypeArr(){
        return type == null? new String[] {}:type.split("");
    }

    /* 작가 리스트 */
    private String[] authorArr;

    /* 카테고리 코드 */
    private String category;

}
