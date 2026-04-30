package in.tech_camp.chat_app.form;

import lombok.Data;

@Data
public class UserForm {
  private String name;
  private String email;
  private String password;
  private String passwordConfirmation; // これはパスワードの再入力
}
//これはformでユーザーが入力した情報を一時的に保存する場所

