package nl.han.se.bewd.mockworkshop.toets;

import nl.han.se.bewd.mockworkshop.student.Student;
import nl.han.se.bewd.mockworkshop.resultaat.ToetsResultaatRegistratieDB;

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
        if (null == s) throw new FoutiefStudentException();
        db.verwijderResultaatVanStudent(s);
    }
}
