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

/**
 * 受講生情報を扱うリポジトリ。
 *
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 全件検索します。
   *
   * @return 全件検索した乗降性情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> findAllStudentsCourses(); // 全件検索

  @Select("SELECT * FROM students_courses WHERE studentId = #{studentId}")
  List<StudentsCourses> findStudentsCoursesByStudentId(String studentId); // studentIdに基づく検索

  @Insert("INSERT INTO students (name, nickname, email, address, age, gender, remark, is_deleted) " +
      "VALUES (#{name}, #{nickname}, #{email}, #{address}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);


  @Insert("INSERT INTO students_courses (studentId, course_name, courseStartAt, courseEndAt) " +
      "VALUES (#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentsCourses(StudentsCourses studentsCourses);

  @Update("UPDATE students SET name = #{name}, nickname = #{nickname}, email = #{email}, " +
      "address = #{address}, age = #{age}, gender = #{gender}, remark = #{remark}, " +
      "is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Update("UPDATE students_courses SET course_name = #{courseName}, courseStartAt = #{courseStartAt}, courseEndAt = #{courseEndAt} WHERE id = #{id}")
  void updateStudentsCourses(StudentsCourses studentsCourses);

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(int id);

  @Update("UPDATE students SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentCourseName(@Param("id") int id, @Param("courseName") String courseName);
}
