package com.sparta.round3.service;

import com.sparta.round3.dto.*;
import com.sparta.round3.entity.Board;
import com.sparta.round3.repostiory.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardSaveResponseDto saveboard(BoardSaveRequestDto boardSaveRequestDto) {
        Board newBoard = new Board(
                boardSaveRequestDto.getTitle(),
                boardSaveRequestDto.getContents()
        );

        Board savedBoard = boardRepository.save(newBoard);

        return new BoardSaveResponseDto(
                savedBoard.getId(),
                savedBoard.getTitle(),
                savedBoard.getContents()
        );
    }

    public List<BoardSimpleResponseDto> getBoards() {
        List<Board> boardList = boardRepository.findAll();

        List<BoardSimpleResponseDto> dtoList = new ArrayList<>();

        for (Board board : boardList) {
            BoardSimpleResponseDto dto = new BoardSimpleResponseDto(board.getTitle());
            dtoList.add(dto);
        }

        return dtoList;
    }

    public BoardDetailResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("ㅈㅅ 님 그 보드 업슴!!"));

        return new BoardDetailResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContents()
        );
    }

    @Transactional
    public BoardUpdateTitleResponseDto updateBoardTitle(Long boardId, BoardUpdateTitleRequestDto boardUpdateTitleRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new NullPointerException("ㅈㅅ 님 그보드 없음 !!"));

        board.updateTitle(boardUpdateTitleRequestDto.getTitle());

        return new BoardUpdateTitleResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContents()
        );
    }

    @Transactional
    public BoardUpdateContentsResponseDto updateBoardContents(Long boardId, BoardUpdateContentsRequestDto boardUpdateContentsRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("ㅈㅅ님 그보드 없음!!"));

        board.updateContents(board.getContents());

        return new BoardUpdateContentsResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContents()
        );
    }

    @Transactional
    public void deleteBoard(Long boardId) { boardRepository.deleteById(boardId);
    }
}
