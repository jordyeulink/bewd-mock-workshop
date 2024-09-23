package nl.han.se.bewd.mockworkshop.toets;

import nl.han.se.bewd.mockworkshop.resultaat.ToetsResultaatRegistratieDB;
import nl.han.se.bewd.mockworkshop.student.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ToetsTest {

    @Test
    void opdracht10getToetsCijferVoorStudentGeeftToetsCijferVoorStudent() {
        // Arrange
        Student mockStudent = mock(Student.class);
        ToetsResultaatRegistratieDB mockDB = mock(ToetsResultaatRegistratieDB.class);
        Toets sut = new Toets();
        sut.setDb(mockDB);
        when(mockDB.vraagResultatenOp(mockStudent)).thenReturn(List.of("8"));

        // Act
        int result = sut.getToetsCijferVoorStudent(mockStudent);

        // Assert
        assertEquals(8, result);
    }

    @Test
    void opdracht10getToetsCijferVoorStudentGeeftToetsCijferVoorStudent2() {
        // Arrange
        Student mockStudent = mock(Student.class);
        ToetsResultaatRegistratieDB mockDB = mock(ToetsResultaatRegistratieDB.class);
        Toets sut = new Toets();
        sut.setDb(mockDB);
        when(mockDB.vraagResultatenOp(mockStudent)).thenReturn(List.of("2"));

        // Act
        int result = sut.getToetsCijferVoorStudent(mockStudent);

        // Assert
        assertEquals(2, result);
    }

    @Test
    void opdracht10getToetsCijferVoorStudentGeeftToetsCijferVoorStudent3() {
        // Arrange
        Student mockStudent = mock(Student.class);
        ToetsResultaatRegistratieDB mockDB = mock(ToetsResultaatRegistratieDB.class);
        Toets sut = new Toets();
        sut.setDb(mockDB);
        when(mockDB.vraagResultatenOp(mockStudent)).thenReturn(List.of("1","2","3"));

        // Act
        int result = sut.getToetsCijferVoorStudent(mockStudent);

        // Assert
        assertEquals(3, result);
    }

    @Test
    void opdracht10getToetsCijferVoorStudentGeeftToetsCijferVoorStudent4() {
        // Arrange
        Student mockStudent = mock(Student.class);
        ToetsResultaatRegistratieDB mockDB = mock(ToetsResultaatRegistratieDB.class);
        Toets sut = new Toets();
        sut.setDb(mockDB);
        when(mockDB.vraagResultatenOp(mockStudent)).thenReturn(List.of("10","2","3"));

        // Act
        int result = sut.getToetsCijferVoorStudent(mockStudent);

        // Assert
        assertEquals(10, result);
    }


}