// StudentManager.aidl
package com.wasu.aidlserver;

// Declare any non-default types here with import statements
import com.wasu.aidlserver.Student;

interface StudentManager {
    List<Student> getStudents();
    Student addStudentIn(in Student student);
    Student addStudentOut(out Student student);
    Student addStudentInOut(inout Student student);
}
