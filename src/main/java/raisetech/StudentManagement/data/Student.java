package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int id;

  @NotBlank(message = "名前は必須です。")
  private String name;

  private String nickname;

  @NotBlank(message = "メールアドレスは必須です。")
  private String email;

  private String address ;
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;
}
