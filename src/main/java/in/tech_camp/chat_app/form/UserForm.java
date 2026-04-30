package in.tech_camp.chat_app.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;

import in.tech_camp.chat_app.validation.ValidationPriority1;
import in.tech_camp.chat_app.validation.ValidationPriority2;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {
  @NotBlank(message = "Name can't be blank", groups = ValidationPriority1.class)
  private String name;

  @NotBlank(message = "Email can't be blank", groups = ValidationPriority1.class)
  @Email(message = "Email should be valid", groups = ValidationPriority2.class)
  private String email;

  @NotBlank(message = "Password can't be blank", groups = ValidationPriority1.class)
  @Length(min = 6, max = 128, message = "Password should be between 6 and 128 characters", groups = ValidationPriority2.class)
  private String password;
  private String passwordConfirmation; // これはパスワードの再入力

  public void validatePasswordConfirmation(BindingResult result) {
    if (!password.equals(passwordConfirmation)) {
      // パスワードが一致していない場合
      result.rejectValue("passwordConfirmation", "error.user", "Password confirmation doesn't match Password");
      // エラー登録 rejectValueでエラーメッセージを手動で追加
    }
  }
}
// これはformでユーザーが入力した情報を一時的に保存する場所
