package in.tech_camp.chat_app.entity;

import lombok.Data;

@Data
public class RoomUserEntity {
  private Long id;
  //Longは整数を扱う型の一つで、大きい数まで入れられる変数
  private UserEntity user;
  //Entitiyによって関連データまで扱う
  private RoomEntity room;
}
