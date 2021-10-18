package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.entity.Reply;
import org.zerock.board.repository.search.SearchBoardRepository;

import java.util.List;
//인터페이스는 다중상속 허용
public interface BoardRepository extends JpaRepository <Board, Long>, SearchBoardRepository {
    @Query("select b, w from Board b left join b.writer w where b.bno=:bno ") //여기 bno가
    Object getBoardWithWriter(@Param("bno") Long bno); //여기서 받음.. param이 뮤ㅝㄴ데/.?

    @Query("select b, r from Board b left join Reply r on r.board=b where b.bno= :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno); //보드기준으로 연관관계X on을 씀

    @Query(value = "Select b,w, count(r) " +
            "from Board b " +
            "left join b.writer w "+
            "left join Reply r on r.board = b " +
            "group by b ", countQuery = "select count(b) from Board b ")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query("select b, w, count(r) from Board b "
            + "left join b.writer w left outer join Reply r on r.board = b "
            + "where b.bno=:bno ")
    Object getBoardByBno(@Param("bno") Long bno);
    }


