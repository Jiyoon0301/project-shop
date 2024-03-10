package project.shop1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Author {

    @Id @GeneratedValue
    private Long id;

    private String authorName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="nation_id")
    private Nation nation;

    private String authorIntro;

}
