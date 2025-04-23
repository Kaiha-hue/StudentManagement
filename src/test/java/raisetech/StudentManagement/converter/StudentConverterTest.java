package raisetech.StudentManagement.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

public class StudentConverterTest {

  private StudentConverter studentConverter;

  @BeforeEach
  void setUp() {
    studentConverter = new StudentConverter();
  }

  @Test
  void convertStudentDetails_受講生とコースが正しくマッピングされる() {
    // Arrange
    Student student1 = new Student();
    student1.setId("1");
    student1.setName("山田 太郎");

    Student student2 = new Student();
    student2.setId("2");
    student2.setName("佐藤 花");

    StudentCourse course1 = new StudentCourse();
    course1.setStudentId("1");
    course1.setCourseName("Java");
    course1.setCourseStartAt(LocalDate.of(2025, 4, 1));
    course1.setCourseEndAt(LocalDate.of(2026, 3, 31));

    StudentCourse course2 = new StudentCourse();
    course2.setStudentId("2");
    course2.setCourseName("English");
    course2.setCourseStartAt(LocalDate.of(2025, 5, 1));
    course2.setCourseEndAt(LocalDate.of(2026, 4, 30));

    List<Student> studentList = Arrays.asList(student1, student2);
    List<StudentCourse> courseList = Arrays.asList(course1, course2);

    // Act
    List<StudentDetail> result = studentConverter.convertStudentDetails(studentList, courseList);

    // Assert
    assertThat(result).hasSize(2);

    // 学生1の検証
    StudentDetail detail1 = result.get(0);
    assertThat(detail1.getStudent().getId()).isEqualTo("1");
    assertThat(detail1.getStudentCourseList()).hasSize(1);
    assertThat(detail1.getStudentCourseList().get(0).getCourseName()).isEqualTo("Java");

    // 学生2の検証
    StudentDetail detail2 = result.get(1);
    assertThat(detail2.getStudent().getId()).isEqualTo("2");
    assertThat(detail2.getStudentCourseList()).hasSize(1);
    assertThat(detail2.getStudentCourseList().get(0).getCourseName()).isEqualTo("English");
  }
}
