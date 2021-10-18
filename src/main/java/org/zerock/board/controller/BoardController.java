package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.BoardService;

import java.util.jar.Attributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    final private BoardService boardService;

    @GetMapping({"","/","/list"})
    public String boardList(PageRequestDTO pageRequestDTO, Model model){
      log.info("/board/list...");
      log.info(">>>"+pageRequestDTO);
      model.addAttribute("result", boardService.getList(pageRequestDTO));
      return "/board/list";
    }

    @GetMapping("/register")
    public void register() {log.info("register...");}

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){
        log.info("registerPost...");
        Long bno = boardService.register(dto);
        log.info("register bno:" + bno);
        redirectAttributes.addFlashAttribute("msg",bno);
        redirectAttributes.addFlashAttribute("noti","등록");
        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                     Long bno, Model model) {
        log.info("bno:"+bno);
        BoardDTO boardDTO = boardService.get(bno);
        log.info(boardDTO);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes attributes){
        log.info("bno: "+bno);
        boardService.removeWithReplies(bno);
        attributes.addFlashAttribute("msg", bno);
        attributes.addFlashAttribute("noti", "삭제");
        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto,RedirectAttributes redirectAttributes,
                         @ModelAttribute("requestDTO")PageRequestDTO requestDTO){
        boardService.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("bno", dto.getBno());
        return "redirect:/board/read";
    }


}
