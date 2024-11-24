package raisetech.student.management;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  @JsonProperty("student_id")
  private String studentId;

  private String name;

  private String nickname;

  @JsonProperty("email_address")
  private String emailAddress;

  private String address;

  private int age;

  private String gender;
}
