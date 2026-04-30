package in.tech_camp.chat_app.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import in.tech_camp.chat_app.entity.UserEntity;

@Mapper //MyBatisでjavaとDBのデータの変換
public interface UserRepository { 
  @Insert("INSERT INTO users (name, email, password) VALUES (#{name}, #{email}, #{password})")
  //SQLをそのまま記述
  @Options(useGeneratedKeys = true, keyProperty = "id")
  //DBのidをエンティティに入れる
  void insert(UserEntity user);
  //上のコードはUserEntityをDBにINSERTする処理
  @Select("SELECT * FROM users WHERE email = #{email}")
  UserEntity findByEmail(String email);
  //データベースから同じemailのユーザーをSELECTしている

  @Select("SELECT * FROM users WHERE id = #{id}")
  UserEntity findById(Integer id);

//データベースからidをキーに全カラムを呼び出し
  @Update("UPDATE users SET name = #{name}, email = #{email} WHERE id = #{id}")
  void update(UserEntity user);

}
//リポジトリは、データベースとアプリケーションの通信をしている。
