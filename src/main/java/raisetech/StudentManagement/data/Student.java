package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {

  private int id;

  @NotBlank
  private String name;

  @NotBlank
  private String nickname;

  @NotBlank
  private String email;

  @NotBlank
  private String address ;

  private int age;

  @NotBlank
  private String gender;

  private String remark;
  private boolean isDeleted;
}
