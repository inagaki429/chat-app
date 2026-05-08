package in.tech_camp.chat_app.form;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MessageForm {
  private String content;
  // フィールドの中身はコンテンツのみ
  private MultipartFile image;

  // 画像
  public void validateMessage(BindingResult result) {
    // 画像化メッセージどちらも空の場合に、バリデーションエラーになる設定
    if ((content == null || content.isEmpty()) && (image == null || image.isEmpty())) {
      result.rejectValue("Content", "error.Message", "Please enter either content or image");
    }
  }

}