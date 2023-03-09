package team.cupid.realworld.domain.board.tag;

import team.cupid.realworld.domain.board.Board;

import javax.persistence.*;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(name = "board_id")
    private Board board;
}
