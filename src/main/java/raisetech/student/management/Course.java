package raisetech.student.management;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {

  @JsonProperty("course_id")
  private String courseId;

  @JsonProperty("student_id")
  private String studentId;

  @JsonProperty("course_name")
  private String courseName;

  @JsonProperty("start_date")
  private String startDate;

  @JsonProperty("expected_end_date")
  private String expectedEndDate;
}
