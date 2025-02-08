package raisetech.StudentManagement.controller;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    // コース選択肢を追加
    List<String> availableCourses = Arrays.asList("Java", "English", "Python", "Physics", "Chemistry");
    model.addAttribute("availableCourses", availableCourses);

    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@Valid @ModelAttribute StudentDetail studentDetail,
      BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      // エラー時にコース選択肢を再設定
      List<String> availableCourses = Arrays.asList("Java", "English", "Python", "Physics", "Chemistry");
      model.addAttribute("availableCourses", availableCourses);
      return "registerStudent";
    }
    // 受講コース名を学生の courseName にセット
    if (!studentDetail.getStudentsCourses().isEmpty()) {
      String firstCourseName = studentDetail.getStudentsCourses().get(0).getCourseName();
      studentDetail.getStudent().setCourseName(firstCourseName);
    }

    service.registerStudent(studentDetail);
    return "redirect:/studentList";
  }

  @PostMapping("/updateStudent")
  public String updateStudent(@Valid @ModelAttribute StudentDetail studentDetail, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("studentDetail", studentDetail);
      return "updateStudent";
    }
    service.updateStudent(studentDetail.getStudent());
    return "redirect:/studentList";
  }

  @GetMapping("/updateStudent/{id}")
  public String updateStudentForm(@PathVariable("id") int id, Model model) {
    Student student = service.searchStudentById(id);
    List<StudentsCourses> studentsCourses = service.searchStudentsCoursesByStudentId(String.valueOf(id));

    // StudentDetail オブジェクトを生成
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);

    // 使用可能なコースリストを作成
    List<String> availableCourses = Arrays.asList("Java", "English", "Python", "Physics", "Chemistry");
    model.addAttribute("availableCourses", availableCourses);

    // 必要なデータをモデルに追加
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  @PostMapping("/updateStudent/{id}")
  public String updateStudent(
      @PathVariable("id") int id,
      @Valid @ModelAttribute StudentDetail studentDetail,
      BindingResult result,
      Model model
  ) {
    if (result.hasErrors()) {
      // エラー時にコース選択肢を再設定
      List<String> availableCourses = Arrays.asList("Java", "English", "Python", "Physics", "Chemistry");
      model.addAttribute("availableCourses", availableCourses);
      model.addAttribute("studentDetail", studentDetail);
      return "updateStudent";
    }
    // 学生情報の更新
    studentDetail.getStudent().setId(id);
    service.updateStudent(studentDetail.getStudent());

    // 受講コース情報の更新
    for (StudentsCourses course : studentDetail.getStudentsCourses()) {
      course.setStudentId(id);
      service.updateStudentsCourses(course);
    }

    // students テーブルのコース名も更新
    if (!studentDetail.getStudentsCourses().isEmpty()) {
      String firstCourseName = studentDetail.getStudentsCourses().get(0).getCourseName();
      studentDetail.getStudent().setCourseName(firstCourseName);
      service.updateStudentCourseName(id, firstCourseName);
    }
    return "redirect:/studentList";
  }

  @GetMapping("/restoreStudentList")
  public String getRestoreStudentList(Model model) {
    List<Student> canceledStudents = service.searchCanceledStudentList();
    model.addAttribute("canceledStudentList", canceledStudents);
    return "restoreStudentList";
  }

  @PostMapping("/restoreStudent/{id}")
  public String restoreStudent(@PathVariable("id") int id) {
    service.restoreStudent(id);
    return "redirect:/restoreStudentList";
  }
}
