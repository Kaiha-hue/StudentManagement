package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  private int id;

  @NotBlank(message = "受講生IDは必須です。")
  @Pattern(regexp = "^\\d+$", message = "IDは数字のみで入力してください。")
  private String studentId;

  @NotBlank
  private String courseName;
  private LocalDate courseStartAt;
  private LocalDate courseEndAt;
}
