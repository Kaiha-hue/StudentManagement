package raisetech.StudentManagement.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 指定したIDの受講生情報の検索が実行できて空で返ってくること() throws Exception {
    int id = 999;
    mockMvc.perform(get("/student").param("id", String.valueOf(id)))
        .andExpect(status().isOk());
    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生登録が成功すること() throws Exception {
    Student student = new Student();
    student.setId(10);
    student.setName("登録 太郎");
    student.setNickname("トロ");
    student.setEmail("touroku@example.com");
    student.setAddress("京都");
    student.setGender("男性");

    StudentCourse course = new StudentCourse();
    course.setCourseName("Java");

    StudentDetail requestDetail = new StudentDetail(student, List.of(course));

    // モックの返り値設定
    when(service.registerStudent(any(StudentDetail.class))).thenReturn(requestDetail);

    String json = """
    {
      "student": {
        "id": "10",
        "name": "登録 太郎",
        "nickname": "トロ",
        "email": "touroku@example.com",
        "address": "京都",
        "gender": "男性"
      },
      "studentCourseList": [
        {
          "courseName": "Java"
        }
      ]
    }
    """;

    mockMvc.perform(post("/registerStudent")
            .contentType("application/json")
            .content(json))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any(StudentDetail.class));
  }

  @Test
  void 受講生情報が更新されること() throws Exception {
    String json = """
    {
      "student": {
        "id": "1",
        "name": "更新 太郎",
        "nickname": "アップ",
        "email": "update@example.com",
        "address": "東京",
        "gender": "男性"
      },
      "studentCourseList": [
        {
          "id": 1,
          "studentId": "1",
          "courseName": "Python"
        }
      ]
    }
    """;

    mockMvc.perform(put("/updateStudent")
            .contentType("application/json")
            .content(json))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any(StudentDetail.class));
  }

  @Test
  void IDが0のときに例外が発生してエラーメッセージが返ること() throws Exception {
    mockMvc.perform(get("/triggerException").param("id", "0"))
        .andExpect(status().is4xxClientError());
  }

  //Studentの入力チェック//
  @Test
  void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId(1);
    student.setName("山田 太郎");
    student.setNickname("タロ");
    student.setEmail("yamada.taro@example.com");
    student.setAddress("東京");
    student.setGender("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  //StudentsCourseの入力チェック//
  @Test
  void 受講生詳細の受講生コース情報で適切な値を入力した時に入力チェックに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(5);
    studentCourse.setCourseName("山田 太郎");

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations.size()).isEqualTo(0);
  }
}