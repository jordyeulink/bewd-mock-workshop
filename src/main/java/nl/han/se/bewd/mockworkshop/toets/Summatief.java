package nl.han.se.bewd.mockworkshop.toets;

import nl.han.se.bewd.mockworkshop.student.Student;

public interface Summatief {
    public int getToetsCijferVoorStudent(Student s);
    public void verwijderStudentResultaten(Student s);
}
