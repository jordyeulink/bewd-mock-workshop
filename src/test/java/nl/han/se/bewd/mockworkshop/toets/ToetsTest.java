package nl.han.se.bewd.mockworkshop.toets;

import nl.han.se.bewd.mockworkshop.resultaat.ToetsResultaatRegistratieDB;
import nl.han.se.bewd.mockworkshop.student.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToetsTest {
    @Mock
    Student mockStudent = mock(Student.class);

    @Mock
    ToetsResultaatRegistratieDB mockDB = mock(ToetsResultaatRegistratieDB.class);

    @InjectMocks
    Toets sut = new Toets();

    @Test
    void opdracht10getToetsCijferVoorStudentGeeftToetsCijferVoorStudent() {
        // Arrange
        when(mockDB.vraagResultatenOp(mockStudent)).thenReturn(List.of("8"));

        // Act
        int result = sut.getToetsCijferVoorStudent(mockStudent);

        // Assert
        assertEquals(8, result);
    }

    @Test
    void opdracht10getToetsCijferVoorStudentGeeftToetsCijferVoorStudent2() {
        // Arrange
        when(mockDB.vraagResultatenOp(mockStudent)).thenReturn(List.of("2"));

        // Act
        int result = sut.getToetsCijferVoorStudent(mockStudent);

        // Assert
        assertEquals(2, result);
    }

    @Test
    void opdracht10getToetsCijferVoorStudentGeeftToetsCijferVoorStudent3() {
        // Arrange
        when(mockDB.vraagResultatenOp(mockStudent)).thenReturn(List.of("1","2","3"));

        // Act
        int result = sut.getToetsCijferVoorStudent(mockStudent);

        // Assert
        assertEquals(3, result);
    }

    @Test
    void opdracht10getToetsCijferVoorStudentGeeftToetsCijferVoorStudent4() {
        // Arrange
        when(mockDB.vraagResultatenOp(mockStudent)).thenReturn(List.of("10","2","3"));

        // Act
        int result = sut.getToetsCijferVoorStudent(mockStudent);

        // Assert
        assertEquals(10, result);
    }


}