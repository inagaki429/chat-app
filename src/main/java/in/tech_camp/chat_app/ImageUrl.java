package in.tech_camp.chat_app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
//インスタンスを自動管理
@NoArgsConstructor
public class ImageUrl {
  @Value("${image.url}")
  //アプリケーションプロパティの値をマッピング
  private String url;

  public String getImageUrl(){
    return url;
    //画像ファイルの取得ができる
  }
}