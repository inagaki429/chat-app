package in.tech_camp.chat_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import in.tech_camp.chat_app.form.RoomForm;

@Controller

public class RoomController {

  @GetMapping("/rooms/new")
  public String showRoomNew(Model model) {
    model.addAttribute("roomForm", new RoomForm());
    return "rooms/new";
  }
  //チャットルーム作成画面のインスタンスを生成、ビューを返す
}
