package nl.han.se.bewd.mockworkshop.toets;

import nl.han.se.bewd.mockworkshop.student.Student;

public class FakeSummatief implements  Summatief{
    @Override
    public int getToetsCijferVoorStudent(Student s) {
        return 8;
    }

    @Override
    public void verwijderStudentResultaten(Student s) {

    }
}
