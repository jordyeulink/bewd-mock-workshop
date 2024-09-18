package nl.han.se.bewd.mockworkshop.resultaat;

import nl.han.se.bewd.mockworkshop.student.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToetsResultaatRegistratieDBTest {

    @Test
    void voegResultaatToeResultaatIsOokWeerOpTeVragen() {
        //Arrange
        Student student1 = new Student();
        ToetsResultaatRegistratieDB sut = new ToetsResultaatRegistratieDB();
        String resultaat = String.valueOf(10);

        // Act
        sut.voegResultaatToe(student1, resultaat);
        List<String> resultaten = sut.vraagResultatenOp(student1);

        // Assert
        assertEquals(resultaten.size(), 1);
    }

    @Test
    void verwijderResultaatVanStudentVerwijdertResultaatVanJuisteStudent() {
        //Arrange
        Student student1 = new Student();
        Student student2 = new Student();
        ToetsResultaatRegistratieDB sut = new ToetsResultaatRegistratieDB();
        String resultaat1 = String.valueOf(10);
        String resultaat2 = String.valueOf(5);

        // Act
        sut.voegResultaatToe(student1, resultaat1);
        sut.voegResultaatToe(student2, resultaat2);
        sut.verwijderResultaatVanStudent(student2);
        List<String> resultaten1 = sut.vraagResultatenOp(student1);

        // Assert
        assertEquals(resultaten1.get(0), String.valueOf(10));
    }
}