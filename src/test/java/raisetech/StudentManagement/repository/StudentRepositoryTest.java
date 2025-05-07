package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setName("畑島快伊");
    student.setNickname("ハタシー");
    student.setEmail("hatashima@kai.example.com");
    student.setAddress("福岡県");
    student.setAge(25);
    student.setGender("男性");
    student.setDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生IDによる検索が行えること() {
    Student student = sut.searchStudent(1);
    assertThat(student).isNotNull();
    assertThat(student.getName()).isEqualTo("山田太郎");
  }

  @Test
  void 全受講生コース情報の検索が行えること() {
    List<StudentCourse> courses = sut.searchStudentCourseList();
    assertThat(courses).hasSize(10);
  }

  @Test
  void 受講生IDに紐づくコース情報の検索が行えること() {
    List<StudentCourse> courses = sut.searchStudentsCourse(3);
    assertThat(courses).hasSize(3);
    assertThat(courses.get(0).getCourseName()).isEqualTo("Web制作コース");
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    StudentCourse course = new StudentCourse();
    course.setStudentId(2);
    course.setCourseName("Pythonコース");
    course.setCourseStartAt(LocalDate.of(2024, 1, 1));
    course.setCourseEndAt(LocalDate.of(2024, 6, 1));

    sut.registerStudentsCourse(course);

    List<StudentCourse> courses = sut.searchStudentsCourse(2);
    assertThat(courses).anyMatch(c -> c.getCourseName().equals("Pythonコース"));
  }

  @Test
  void 受講生情報の更新が行えること() {
    Student student = sut.searchStudent(1);
    student.setName("山田新太郎");

    StudentDetail detail = new StudentDetail(student, List.of());
    sut.updateStudent(detail);

    Student updated = sut.searchStudent(1);
    assertThat(updated.getName()).isEqualTo("山田新太郎");
  }

  @Test
  void 受講生コース情報の更新が行えること() {
    List<StudentCourse> courses = sut.searchStudentsCourse(1);
    StudentCourse course = courses.get(0);
    course.setCourseName("更新済みコース");

    sut.updateStudentsCourse(course);

    List<StudentCourse> updatedCourses = sut.searchStudentsCourse(1);
    assertThat(updatedCourses).anyMatch(c -> c.getCourseName().equals("更新済みコース"));
  }
}
