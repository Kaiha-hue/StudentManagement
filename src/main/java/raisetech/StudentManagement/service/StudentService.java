package raisetech.StudentManagement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exceptionhandler.StudentNotFoundException;
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
   * 受講生詳細の一覧検索です。全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧(全件)
   */

  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生詳細検索です。
   * IDに紐づく受講生情報を取得したあと。その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */

  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    if (student == null) {  // student が null の場合は例外をスロー
      throw new StudentNotFoundException("このIDの受講生情報は存在しません: " + id);
    }
    List<StudentCourse> studentCourse = repository.searchStudentsCourse(student.getId());
    return new StudentDetail(student, studentCourse);
  }

  /**
   * 受講生詳細の登録を行います。
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値やコース開始日、コース終了日を設定します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.registerStudent(student);
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentsCourse(studentCourse, student);
      repository.registerStudentsCourse(studentCourse);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentCourse 受講生コース情報
   * @param student 受講生
   */
  private void initStudentsCourse(StudentCourse studentCourse, Student student) {
    LocalDate now = LocalDate.now();

    studentCourse.setStudentId(student.getId());
    studentCourse.setCourseStartAt(now);
    studentCourse.setCourseEndAt(now.plusYears(1));
  }

  /**
   * 受講生詳細の更新を行います。
   * 受講生と受講生コース情報をそれぞれ更新します。
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail);
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateStudentsCourse(studentCourse));
  }

  public void deleteStudent(int id) {
    Student existingStudent = repository.searchStudent(id);
    if (existingStudent == null) {
      throw new StudentNotFoundException("受講生が見つかりません: ID = " + id);
    }
    repository.deleteStudent(id);
  }

  public List<StudentDetail> searchStudentByNameOrAge(String name, Integer age) {
    List<Student> students = repository.searchByNameOrAge(name, age);
    List<StudentDetail> result = new ArrayList<>();
    for (Student student : students) {
      List<StudentCourse> courses = repository.searchStudentsCourse(student.getId());
      result.add(new StudentDetail(student, courses));
    }
    return result;
  }

  public List<CourseStatus> getAllCourseStatuses() {
    return repository.getAllCourseStatuses();
  }
}