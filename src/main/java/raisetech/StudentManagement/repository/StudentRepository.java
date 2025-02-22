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

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students WHERE is_deleted = false")
  List<Student> search();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(int id);

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> findAllStudentsCourses(); // 全件検索

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> findStudentsCoursesByStudentId(String studentId);

  @Insert("INSERT INTO students (name, nickname, email, address, age, gender, remark, is_deleted, course_name) " +
      "VALUES (#{name}, #{nickname}, #{email}, #{address}, #{age}, #{gender}, #{remark}, false, #{courseName})")
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

  @Update("UPDATE students_courses SET course_name = #{courseName}, course_start_at = #{courseStartAt}, course_end_at = #{courseEndAt} WHERE id = #{id}")
  void updateStudentsCourses(StudentsCourses studentsCourses);

  @Update("UPDATE students SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentCourseName(@Param("id") int id, @Param("courseName") String courseName);

  @Select("SELECT * FROM students WHERE is_deleted = true")
  List<Student> findCanceledStudents();

  @Update("UPDATE students SET is_deleted = false WHERE id = #{id}")
  void restoreStudent(int id);
}
