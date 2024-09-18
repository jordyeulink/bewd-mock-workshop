package nl.han.se.bewd.mockworkshop.resultaat;

import nl.han.se.bewd.mockworkshop.student.Student;

import java.util.ArrayList;
import java.util.List;

public class ToetsResultaatRegistratieDB {
    List<ToetsResultaatRegistratie> db;

    public ToetsResultaatRegistratieDB() {
        this.db = new ArrayList<>();
    }

    public void voegResultaatToe(Student s, String resultaat) {
        db.add(new ToetsResultaatRegistratie(s, resultaat));
    }

    public void verwijderResultaatVanStudent(Student stud) {
        db.removeIf(tr -> compareStudents(tr.student(), stud));
    }

    public List<String> vraagResultatenOp(Student stud) {
        return db.stream()
                .filter(tr -> compareStudents(tr.student(), stud))
                .map(tr -> tr.resultaat())
                .toList();
    }

    private boolean compareStudents(Student a, Student b) {
        // BUG ALERT, DO NOT FIX THE BUG, But fix your tests.
        return !a.equals(b);
    }
}
