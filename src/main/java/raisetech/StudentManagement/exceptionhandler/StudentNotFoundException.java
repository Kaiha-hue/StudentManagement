package raisetech.StudentManagement.exceptionhandler;

/**
 * 受講生が見つからない場合の例外クラス
 */
public class StudentNotFoundException extends RuntimeException {
  public StudentNotFoundException(String message) {
    super(message);
  }
}
