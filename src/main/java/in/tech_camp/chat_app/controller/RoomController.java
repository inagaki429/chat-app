package in.tech_camp.chat_app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import in.tech_camp.chat_app.custom_user.CustomUserDetail;
import in.tech_camp.chat_app.entity.RoomEntity;
import in.tech_camp.chat_app.entity.RoomUserEntity;
import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import in.tech_camp.chat_app.repository.RoomRepository;
import in.tech_camp.chat_app.repository.RoomUserRepository;
import in.tech_camp.chat_app.validation.ValidationOrder;

import in.tech_camp.chat_app.form.RoomForm;

@Controller
@AllArgsConstructor

public class RoomController {
  private final UserRepository userRepository;

  private final RoomRepository roomRepository;

  private final RoomUserRepository roomUserRepository;

  @GetMapping("/")
  public String index(@AuthenticationPrincipal CustomUserDetail currentUser, Model model) {
    UserEntity user = userRepository.findById(currentUser.getId());
    model.addAttribute("user", user);
    List<RoomUserEntity> roomUserEntities = roomUserRepository.findByUserId(currentUser.getId());
    List<RoomEntity> roomList = roomUserEntities.stream()
        .map(RoomUserEntity::getRoom)
        .collect(Collectors.toList());
    model.addAttribute("rooms", roomList);
    return "rooms/index";
  }

  @GetMapping("/rooms/new")
  public String showRoomNew(@AuthenticationPrincipal CustomUserDetail currentUser, Model model){
    List<UserEntity> users = userRepository.findAllExcept(currentUser.getId());
    model.addAttribute("users", users);
    model.addAttribute("roomForm", new RoomForm());
    return "rooms/new";
  }


  @PostMapping("/rooms")
  //送信
  public String createRoom(@ModelAttribute("RoomForm") @Validated(ValidationOrder.class) RoomForm roomForm,
      BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetail currentUser, Model model) {
    if (bindingResult.hasErrors()) {
      List<String> errorMessages = bindingResult.getAllErrors().stream()
          .map(DefaultMessageSourceResolvable::getDefaultMessage)
          .collect(Collectors.toList());
      List<UserEntity> users = userRepository.findAllExcept(currentUser.getId());
      model.addAttribute("users", users);
      model.addAttribute("roomForm", roomForm);
      model.addAttribute("errorMessages", errorMessages);
      return "rooms/new";
      // フォーム入力ミスがあったら、再入力画面にいく
    }

    RoomEntity roomEntity = new RoomEntity();
    roomEntity.setName(roomForm.getName());
    try {
      roomRepository.insert(roomEntity);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      List<UserEntity> users = userRepository.findAllExcept(currentUser.getId());
      model.addAttribute("users", users);
      model.addAttribute("roomForm", new RoomForm());
      return "rooms/new";
      // ルーム作成に失敗したら、エラー表示なしで入力画面に戻す処理
    }

    List<Integer> memberIds = roomForm.getMemberIds();
    //画面で選ばれたユーザーid一覧を取得
    for (Integer userId : memberIds) {
      UserEntity userEntity = userRepository.findById(userId);
      //idからユーザー情報をDBで取得
      RoomUserEntity roomUserEntity = new RoomUserEntity();
      //インスタンス生成
      roomUserEntity.setRoom(roomEntity);
      roomUserEntity.setUser(userEntity);
      //ルームとユーザーのセット
      
        // ルームとユーザーの紐づけを１件ずつDBに保存している

      }
    

    return "redirect:/";
  }
}
