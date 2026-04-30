package in.tech_camp.chat_app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
//ユーザー登録のビジネスロジックを担当（サービス層）
@AllArgsConstructor
//すべてのfinalフィールドを引数に持つコンストラクタを自動生成
public class UserService {
  private final UserRepository userRepository;
  //DB操作担当
  private final PasswordEncoder passwordEncoder;
  //パスワードを暗号化する部品
  public void createUserWithEncryptedPassword(UserEntity userEntity) {
    //パスワードを暗号化してからユーザーをDBに保存
    String encodedPassword = encodePassword(userEntity.getPassword());
    //パスワード取得、暗号化
    userEntity.setPassword(encodedPassword);
    //Entityに上書き暗号化済みにする
    userRepository.insert(userEntity);
    //MyBatisでINSERT実行
  }

  private String encodePassword(String password) {
    return passwordEncoder.encode(password);
    //ラップ
  }
}