package in.tech_camp.chat_app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.tech_camp.chat_app.custom_user.CustomUserDetail;
import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
//コンストラクタを自動生成(newするときの初期化)
public class UserAuthenticationService implements UserDetailsService {
//ログイン認証をインターフェースに実装
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      UserEntity userEntity = userRepository.findByEmail(email);
      //emailでユーザーを探している
      if (userEntity == null) {
          throw new UsernameNotFoundException("User not found with email: " + email);
          //見つからないときはログイン失敗になる
      }

      return new CustomUserDetail(userEntity);
      //見つかったら、Detailに変換して返す
  }
}