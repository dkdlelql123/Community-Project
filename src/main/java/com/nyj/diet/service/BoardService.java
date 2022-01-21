package com.nyj.diet.service;

import com.nyj.diet.dao.BoardRepository;
import com.nyj.diet.domain.Board;
import com.nyj.diet.dto.Board.BoardSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

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
}
