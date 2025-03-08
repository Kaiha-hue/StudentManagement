package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
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
  @Select("SELECT * FROM students")
  List<Student> search();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生情報
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(@Param("id") int id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報(全件)
   */
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCoursesList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param id 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{id}")
  List<StudentsCourses> searchStudentsCourses(@Param("id") int id);

  @Insert("INSERT INTO students (name, nickname, email, address, age, gender, remark, is_deleted) " +
      "VALUES (#{name}, #{nickname}, #{email}, #{address}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id, course_name, course_start_at, course_end_at) " +
      "VALUES (#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentsCourses(StudentsCourses studentsCourses);

  @Update("UPDATE students SET name = #{student.name}, nickname = #{student.nickname}, email = #{student.email}, " +
      "address = #{student.address}, age = #{student.age}, gender = #{student.gender}, remark = #{student.remark}, " +
      "is_deleted = #{student.isDeleted} WHERE id = #{student.id}")
  void updateStudent(StudentDetail student);

  @Update("UPDATE students_courses SET course_name = #{courseName}, " +
      "course_start_at = COALESCE(#{courseStartAt}, course_start_at), " +
      "course_end_at = COALESCE(#{courseEndAt}, course_end_at) " +
      "WHERE id = #{id}")
  void updateStudentsCourses(StudentsCourses studentsCourses);
}

