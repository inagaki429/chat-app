package in.tech_camp.chat_app.custom_user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.tech_camp.chat_app.entity.UserEntity;
import lombok.Data;

@Data
public class CustomUserDetail implements UserDetails {
  //entityをUserDetailsに変換している
  private final UserEntity user;

  public CustomUserDetail(UserEntity user) {
      this.user = user;
  //UserEntityをこのクラスにセットしている
  //thisはこのオブジェクトという意味
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      return Collections.emptyList();
  }
  //権限を1つも持ってないユーザーとして扱う

  @Override
  public String getPassword() {
      return user.getPassword();
  }

  @Override
  public String getUsername() {
      return user.getEmail();
  //ここで、Nameではなく、Emailのフィールド値を返すようにしている！    
  //Usernameなのは、emailがログインidだから
  }

  public Integer getId() {
      return user.getId();
  }

  public String getName() {
      return user.getName();
  }

  @Override
  public boolean isAccountNonExpired() {
      return true;
  }

  @Override
  public boolean isAccountNonLocked() {
      return true;
  }
  //このアカウントはロックされてません
  //booleanはYes/Noを表すスイッチ

  @Override
  public boolean isCredentialsNonExpired() {
      return true;
  }
  //このパスワードは期限切れではありません


  @Override
  public boolean isEnabled() {
      return true;
  }
  //このユーザーは有効です
}