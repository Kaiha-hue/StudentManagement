package raisetech.student.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }
}
