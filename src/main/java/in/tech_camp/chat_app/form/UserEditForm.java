package in.tech_camp.chat_app.form;

import lombok.Data;

@Data
public class UserEditForm {
  private Integer id;
  private String name;
  private String email;
  //この３つの入力情報をユーザーから受け取る

}