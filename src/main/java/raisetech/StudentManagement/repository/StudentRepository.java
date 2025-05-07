package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

/**
 * 受講生テーブルと受講生コース情報テーブルを紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧(全件)
   */
  List<Student> search();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生情報
   */

  Student searchStudent(int id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報(全件)
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param id 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentsCourse(@Param("id") int id);

  /**
   * 受講生を新規登録します。
   *
   * @param student 受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。 IDに関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース情報
   */
  void registerStudentsCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(StudentDetail student);

  /**
   * 受講生コース情報を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentsCourse(StudentCourse studentCourse);

  /**
   * 受講生を削除します。
   *
   * @param id 受講生ID
   */
  void deleteStudent(@Param("id") int id);

  /**
   * 名前もしくは年齢で受講生を検索します（完全一致）。
   *
   * @param name 名前（null許容）
   * @param age 年齢（null許容）
   * @return 該当する受講生一覧
   */
  List<Student> searchByNameOrAge(@Param("name") String name, @Param("age") Integer age);

  List<CourseStatus> getAllCourseStatuses();
}

