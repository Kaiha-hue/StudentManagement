package raisetech.StudentManagement.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */

@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }
  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧(全件)
   */

  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentsCourses> studentsCoursesList = repository.searchStudentsCoursesList();
    return converter.convertStudentDetails(studentList, studentsCoursesList);
  }

  /**
   * 受講生検索です。
   * IDに紐づく受講生情報を取得したあと。その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生情報
   */

  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourses(student.getId());
    return new StudentDetail(student, studentsCourses);
  }

  public StudentDetail registerStudent(StudentDetail studentDetail) {
    // 学生情報を登録
    repository.registerStudent(studentDetail.getStudent());
    // 受講コース情報を登録
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(studentDetail.getStudent().getId());
      studentsCourses.setCourseStartAt(LocalDate.now());
      studentsCourses.setCourseEndAt(LocalDate.now().plusYears(1));
      repository.registerStudentsCourses(studentsCourses);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail student) {
    // 受講生の基本情報を更新
    repository.updateStudent(student);

    // 受講生コース情報も更新する
    for (StudentsCourses studentsCourses : student.getStudentsCourses()) {
      repository.updateStudentsCourses(studentsCourses);
    }
  }
}