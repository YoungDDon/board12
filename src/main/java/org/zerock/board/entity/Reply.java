package org.zerock.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno; //프라이머리키

    private String text; //멤버변수
    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY) //외래키
    private Board board;

}
