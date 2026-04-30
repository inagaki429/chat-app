package in.tech_camp.chat_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.form.LoginForm;
import in.tech_camp.chat_app.form.UserEditForm;
import in.tech_camp.chat_app.form.UserForm;
import in.tech_camp.chat_app.repository.UserRepository;
import in.tech_camp.chat_app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class UserControler {

  private final UserRepository userRepository;

  private final UserService userService;
  //UserServiceを使う宣言

  @GetMapping("/users/sign_up") // ブラウザでこのurlにアクセスしている
  public String showSignUp(Model model) {
    // 上のコードはモデルの箱にデータを入れますよという意味
    model.addAttribute("userForm", new UserForm());
    // 上のコードは、UserFormのインスタンスを作って、"userForm"という名前で、
    // addAttributeでHTML(ビュー)に渡している
    return "users/signUp";
  }

  @PostMapping("/user")
  public String createUser(@ModelAttribute("userForm") UserForm userForm, Model model) {
    UserEntity userEntity = new UserEntity();
    userEntity.setName(userForm.getName());
    userEntity.setEmail(userForm.getEmail());
    userEntity.setPassword(userForm.getPassword());
    // 上のコードは、formで入力されたデータをエンティティに変換している
    // HTTP POSTリクエストを受け取る

    try {
       userService.createUserWithEncryptedPassword(userEntity);
       //ここからサービスクラスの処理を繰り返す
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      model.addAttribute("userForm", userForm);
      //JavaのデータをHTMLに渡すために入れている
      //エラーが起きても、前に入力した値を表示させる。

      return "users/signUp";
    }
    // 例外処理、try-catch、エラーが起きたらここに来る
    // エラー時は再入力させる

    return "redirect:/";
    // 成功したらトップページにリダイレクトする
  }

  @GetMapping("/users/login")
  public String LoginForm(Model model) {
    model.addAttribute("loginForm", new LoginForm());
    //LoginFormのインスタンスを生成してモデルに入れている
    return "users/login";
    //上の"users/login"はビュー
  }

  @GetMapping("/login")
  public String login(@RequestParam(value = "error", required = false) String error, @ModelAttribute("loginForm") LoginForm loginForm, Model model) {  
  //RequestParamでURLのパラメータerrorを受け取る  
  //ログイン失敗時にはURLが?errorになる
    if (error != null) {
      //errorが存在しているときだけ処理する
      model.addAttribute("loginError", "メールアドレスかパスワードが間違っています。");
    }
    return "users/login";
  }

  @GetMapping("/users/{userId}/edit")
  public String editUserForm(@PathVariable("userId") Integer userId, Model model) {
    UserEntity user = userRepository.findById(userId);

    UserEditForm userForm = new UserEditForm();
    userForm.setId(user.getId());
    userForm.setName(user.getName());
    userForm.setEmail(user.getEmail());

    model.addAttribute("user", userForm);
    return "users/edit";
  }

  @PostMapping("/users/{userId}")
  public String updateUser(@PathVariable("userId") Integer userId, @ModelAttribute("user") UserEditForm userEditForm, Model model) {
    UserEntity user = userRepository.findById(userId);
    user.setName(userEditForm.getName());
    user.setEmail(userEditForm.getEmail());

    try {
      userRepository.update(user);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      model.addAttribute("user", userEditForm);
      return "users/edit";
    }
    return "redirect:/";
  }
  

}