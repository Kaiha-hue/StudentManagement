package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {
  private String courseId;
  private String studentId;
  private String courseName;
  private String startDate;
  private String expectedEndDate;
}
