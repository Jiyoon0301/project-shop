package project.shop1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor //모든 필드 사용 생성자
@NoArgsConstructor
@Builder
public class Author {

    @Id @GeneratedValue
    private Long id;

    /* 작가 이름 */
    private String authorName;

    /* 작가 소속 국가 */
    private String nation;

    /* 작가 소개 */
    private String authorIntro;

    /* 등록 날짜 */
    private LocalDateTime regDate; // 작가 등록될 때 자동으로 날짜 생성되도록?

    /* 수정 날짜 */
//    private Date updateDate;



}
