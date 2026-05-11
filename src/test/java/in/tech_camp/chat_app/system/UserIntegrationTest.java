package in.tech_camp.chat_app.system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import in.tech_camp.chat_app.ChatAppApplication;
import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.factories.UserFormFactory;
import in.tech_camp.chat_app.form.UserForm;
import in.tech_camp.chat_app.service.UserService;

@ActiveProfiles("test")
@SpringBootTest(classes = ChatAppApplication.class)
@AutoConfigureMockMvc
public class UserIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

   @Autowired
  private UserService userService;

  @Test
  public void ログインしていない状態でトップページにアクセスした場合とグインページに移動する() throws Exception {
    // トップページに移動し、再度ログインページにリダイレクトされることを確認する
    mockMvc.perform(get("/"))
    //HTTPリクエスト送信はperformメソッド
            .andExpect(status().isFound())
            //302で正常
            .andExpect(redirectedUrl("http://localhost/users/login"));
            //リダイレクト
  }
  @Test
  public void ログインに成功しトップページに遷移する() throws Exception {
    // 予め、ユーザーをDBに保存する
    UserForm userForm = UserFormFactory.createUser();
    //UserFormクラスの変数をしようするため
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(userForm.getEmail());
    userEntity.setName(userForm.getName());
    userEntity.setPassword(userForm.getPassword());
    userService.createUserWithEncryptedPassword(userEntity);
    //テスト用のユーザー情報を作って、UserServiceで保存している

    //メモ、ユーザーデータを作成し、ユーザー登録を行う

    // ログインページに遷移する
     mockMvc.perform(get("/users/login"))
            .andExpect(status().isOk())
            //ステータスは200
            .andExpect(view().name("users/login"));
    
        // 保存したユーザーでログインする
     MvcResult loginResult = mockMvc.perform(post("/login")
                                      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                      .param("email", userForm.getEmail())
                                      .param("password", userForm.getPassword())
                                      .with(csrf()))
                              .andExpect(status().isFound())
                              .andExpect(redirectedUrl("/"))
                              .andReturn();
  
    // トップページに再度アクセスし、ログインできていることを確認する
    mockMvc.perform(get("/").session((MockHttpSession)loginResult.getRequest().getSession()))
            .andExpect(status().isOk())
            .andExpect(view().name("rooms/index"));
    
  }

  @Test
  public void ログインに失敗し再びログインページに戻ってくる() throws Exception {
    // 予め、ユーザーをDBに保存する
    // サインインページに遷移する
    mockMvc.perform(get("/users/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("users/login"));

   
    // 誤ったユーザーでログインするとエラーパスにリダイレクトされる
     mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("email", "hoge@hoge.com")
                    .param("password", "hogefuga")
                    .with(csrf()))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/login?error"));  
    
    // エラーパスにリダイレクトされたとき、サインインのビューが表示される
     mockMvc.perform(get("/login?error").param("error", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("users/login"));
  }
}