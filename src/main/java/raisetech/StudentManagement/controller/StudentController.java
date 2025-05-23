package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exceptionhandler.StudentNotFoundException;
import raisetech.StudentManagement.exceptionhandler.TestException;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧(全件)
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細の検索です。
   *
   * @param id　受講生ID
   * @return　受講生
   */
  @Operation(summary = "受講生検索", description = "指定されたIDの受講生情報を取得します。")
  @GetMapping("/student")
  public StudentDetail getStudent(@RequestParam(required = false) @NotBlank(message = "IDは必須です") int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("IDは1以上の数値を指定してください。");
    }
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行います。
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生更新", description = "受講生情報を更新します。キャンセルフラグの更新も含みます。")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  /**
   * 受講生が見つからない例外を発生させるテスト用メソッド
   *
   * @param id 受講生ID
   * @return メッセージ
   */
  @Operation(summary = "例外発生テスト", description = "指定されたIDが0の場合に受講生が見つからない例外を発生させます。")
  @GetMapping("/triggerException")
  public String triggerException(@RequestParam String id) {
    if ("0".equals(id)) {
      throw new StudentNotFoundException("受講生が見つかりません: ID = " + id);
    }
    return "受講生が見つかりました: ID = " + id;
  }

  @Operation(summary = "受講生削除", description = "指定されたIDの受講生をDBから物理削除します。")
  @PostMapping("/deleteStudent")
  public ResponseEntity<String> deleteStudent(@RequestParam int id) {
    service.deleteStudent(id);
    return ResponseEntity.ok("削除処理が成功しました。");
  }

  @Operation(summary = "名前または年齢による受講生検索", description = "名前または年齢（完全一致）で受講生情報を検索します。")
  @GetMapping("/searchStudentByNameOrAge")
  public List<StudentDetail> searchStudentByNameOrAge(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer age) {

    if ((name == null || name.isBlank()) && age == null) {
      throw new IllegalArgumentException("名前または年齢のいずれかは指定してください。");
    }

    return service.searchStudentByNameOrAge(name, age);
  }

  @Operation(summary = "コースごとのステータス一覧", description = "すべてのコースに対する申し込みステータスを取得します。")
  @GetMapping("/courseStatuses")
  public List<CourseStatus> getAllCourseStatuses() {
    return service.getAllCourseStatuses();
  }
}
