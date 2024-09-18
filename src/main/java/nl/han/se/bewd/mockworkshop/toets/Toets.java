package nl.han.se.bewd.mockworkshop.toets;

import nl.han.se.bewd.mockworkshop.resultaat.ToetsResultaatRegistratieDB;
import nl.han.se.bewd.mockworkshop.student.Student;

public class Toets implements Summatief {

    ToetsResultaatRegistratieDB db;

    public Toets() {
        db = new ToetsResultaatRegistratieDB();
    }

    public void studentMaaktToets(Student stud, int cijfer) {
        db.voegResultaatToe(stud, Integer.toString(cijfer));
    }

    @Override
    public int getToetsCijferVoorStudent(Student s) {
        return db.vraagResultatenOp(s)
                .stream()
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
    }

    @Override
    public void verwijderStudentResultaten(Student s) {

        db.verwijderResultaatVanStudent(s);
    }
}
