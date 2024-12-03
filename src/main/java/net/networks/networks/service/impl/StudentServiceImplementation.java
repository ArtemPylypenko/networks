package net.networks.networks.service.impl;

import lombok.AllArgsConstructor;
import net.networks.networks.dto.StudentDto;
import net.networks.networks.exceptions.ResourceNotFoundException;
import net.networks.networks.mapper.StudentMapper;
import net.networks.networks.model.Department;
import net.networks.networks.model.Student;
import net.networks.networks.repos.DepartmentRepository;
import net.networks.networks.repos.StudentRepository;
import net.networks.networks.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImplementation implements StudentService {

    private StudentRepository studentRepository;

    private DepartmentRepository departmentRepository;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = StudentMapper.mapToStudent(studentDto);

        Department department = departmentRepository.findById(studentDto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department was not found with id: "
                        + studentDto.getDepartmentId()));
        student.setDepartment(department);
        Student savedStudent = studentRepository.save(student);
        return StudentMapper.mapToStudentDto(savedStudent);
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new ResourceNotFoundException("Student was not found with given id: " + studentId));
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        return studentList.stream()
                .map(StudentMapper::mapToStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) {
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new ResourceNotFoundException("Student was not found with given id: " + studentId));

        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());

        Department department = departmentRepository.findById(studentDto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department was not found with id: "
                        + studentDto.getDepartmentId()));
        student.setDepartment(department);

        Student savedStudent = studentRepository.save(student);
        return StudentMapper.mapToStudentDto(savedStudent);
    }

    @Override
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new ResourceNotFoundException("Student was not found with given id: " + studentId));
        studentRepository.deleteById(studentId);
    }
}
