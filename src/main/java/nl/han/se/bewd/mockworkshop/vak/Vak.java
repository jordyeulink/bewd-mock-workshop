package nl.han.se.bewd.mockworkshop.vak;

import nl.han.se.bewd.mockworkshop.student.Student;
import nl.han.se.bewd.mockworkshop.student.FoutiefStudentException;
import nl.han.se.bewd.mockworkshop.toets.Summatief;

import java.util.List;

public class Vak {
    private List<Summatief> summatieveToetsen;

    public Vak(List<Summatief> summatieveToetsen) {
        this.summatieveToetsen = summatieveToetsen;
    }

    public int getVakCijferForStudent(Student student) {
        // gemiddelde van alle summatieve toetsen.
        return (summatieveToetsen
                .stream()
                .map(toets -> toets.getToetsCijferVoorStudent(student))
                .reduce(0, Integer::sum) / summatieveToetsen.size());
    }

    public void verwijderStudentUitAlleToetsen(Student stud) {
        summatieveToetsen.forEach(toets -> {
            try {
                toets.verwijderStudentResultaten(stud);
            } catch (FoutiefStudentException fse) {
                System.out.println("Er ging iets mis bij het verwijderen van studentresultaten.");
                throw new RuntimeException("Er ging iets mis bij het verwijderen van studentresultaten.");
            }
        });
    }
}
