package com.nyj.diet.service;

import com.nyj.diet.dao.BoardRepository;
import com.nyj.diet.domain.Article;
import com.nyj.diet.domain.Board;
import com.nyj.diet.dto.Board.BoardDTO;
import com.nyj.diet.dto.Board.BoardModifyForm;
import com.nyj.diet.dto.Board.BoardSaveForm;
import com.nyj.diet.dto.article.ArticleListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // transactional - db에 저장해야 하기때문에
    // 밑에 findall 은 db에 저장하지 않기에 read only로 아무것도 적지 않음
    @Transactional
    public void save(BoardSaveForm boardSaveForm) {
        Board board = Board.createBoard(
                boardSaveForm.getName(),
                boardSaveForm.getDetail()
        );

        boardRepository.save(board);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Optional<Board> findById(Long id){
        // id로 board를 찾기 위해서 만듬
        return boardRepository.findById(id);
    }

    public Board getBoard(Long id){
        Optional<Board> boardOptional = boardRepository.findById(id);
        boardOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );
        return boardOptional.get();
    }

    public BoardDTO getBoardDetail(Long id){
        // 존재하는 board 인가 확인하기 위해
        Optional<Board> boardOptional = findById(id);

        boardOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );

        Board findBoard = boardOptional.get();

        List<ArticleListDTO> articleList = new ArrayList<>();
        List<Article> articles = findBoard.getArticles();

        for (Article article : articles){
            ArticleListDTO articleListDTO = new ArticleListDTO(article);
            articleList.add(articleListDTO);
        }

        return new BoardDTO(findBoard, articleList);
    }

    @Transactional
    public Long modify(Long id, BoardModifyForm boardModifyForm) throws NoSuchElementException{

        Optional<Board> boardOptional = boardRepository.findById(id);

        boardOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );

        Board board = boardOptional.get();
        board.modifyBoard(
                boardModifyForm.getName(),
                boardModifyForm.getDetail()
        );

        return board.getId();

    }

    // 삭제 - db의 정보를 변경해주는 거기에 trancational 붙인다.
    @Transactional
    public void delete(Long id){
        Optional<Board> boardOptional = findById(id);

        boardOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시물은 존재하지 않습니다.")
        );

        Board findBoard = boardOptional.get();
        boardRepository.delete(findBoard);

    }

}
