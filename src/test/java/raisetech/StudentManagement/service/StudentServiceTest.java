package raisetech.StudentManagement.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exceptionhandler.StudentNotFoundException;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void updateStudent_受講生情報とコース情報が更新されること() {
    Student student = new Student();
    student.setId(1);

    StudentCourse course = new StudentCourse();
    course.setId(100);
    course.setStudentId(1);

    List<StudentCourse> courseList = new ArrayList<>();
    courseList.add(course);

    StudentDetail detail = new StudentDetail(student, courseList);

    sut.updateStudent(detail);

    verify(repository).updateStudent(detail);
    verify(repository).updateStudentsCourse(course);
  }
}