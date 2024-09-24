package nl.han.se.bewd.mockworkshop.vak;

import nl.han.se.bewd.mockworkshop.student.FoutiefStudentException;
import nl.han.se.bewd.mockworkshop.student.Student;
import nl.han.se.bewd.mockworkshop.toets.FakeSummatief;
import nl.han.se.bewd.mockworkshop.toets.FakeToets;
import nl.han.se.bewd.mockworkshop.toets.Summatief;
import nl.han.se.bewd.mockworkshop.toets.Toets;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VakTest {

    @Test
    void opdracht4getCijferMetEnkeleToetsGeeftCijferTerug() {
        // Arrange
        Student testStudent = new Student();
        Summatief toets1 = new FakeSummatief();
        //toets1.studentMaaktToets(testStudent, 8);
        Vak vak = new Vak(List.of(toets1));

        // Act
        int result = vak.getVakCijferForStudent(testStudent);

        // Assert
        assertEquals(8, result);
    }

    @Test
    void opdracht5getCijferMetMeerdereToetsenGeeftGemiddeldeTerugVanTweeToetsen() {
        // Arrange
        Student testStudent = new Student();

        FakeToets toets1 = new FakeToets();
         toets1.studentMaaktToets(testStudent, 8);
        FakeToets toets2 = new FakeToets();
         toets2.studentMaaktToets(testStudent, 6);

        Vak vak = new Vak(List.of(toets1, toets2));

        // Act
        int result = vak.getVakCijferForStudent(testStudent);

        // Assert
        assertEquals(7, result);
    }

    @Test
    void opdracht6getCijferGeeftEenNulAanStudentenDieDeToetsNietHebbenGemaakt() {
        // Arrange
        Student studentNietGemaakt = new Student();
        Student studentWelGemaakt = new Student();
        // Origineel: Toets toets1 = new Toets();
        Toets toets1 = mock(Toets.class);
        //toets1.studentMaaktToets(studentWelGemaakt, 8);
        Vak vak = new Vak(List.of(toets1));

        // Act
        int result = vak.getVakCijferForStudent(studentNietGemaakt);

        // Assert
        assertEquals(0, result);
    }

    @Test
    void opdracht7getCijferGeeftHetCorrecteCijferBijMeerdereStudenten() {
        // Arrange
        Student student1 = new Student();
        Student student2 = new Student();

        Toets toets1 = mock(Toets.class);
        //toets1.studentMaaktToets(student1, 3);
        //toets1.studentMaaktToets(student2, 10);
        Vak vak = new Vak(List.of(toets1));

        when(toets1.getToetsCijferVoorStudent(student1)).thenReturn(3);
        when(toets1.getToetsCijferVoorStudent(student2)).thenReturn(10);

        // Act
        int result1 = vak.getVakCijferForStudent(student1);
        int result2 = vak.getVakCijferForStudent(student2);

        // Assert
        assertAll(
                "Elke student moet het correcte cijfer voor die student krijgen",
                () -> assertEquals(3, result1),
                () -> assertEquals(10, result2)
        );
    }

    @Test
    public void opdracht8verwijderStudentUitAllToetsenVerwijdertStudentUitToets() {
        // Arrange
        Student student1 = new Student();
        Toets toets1 = mock(Toets.class);
        toets1.studentMaaktToets(student1, 10);
        Vak vak = new Vak(List.of(toets1));

        // Act
        vak.verwijderStudentUitAlleToetsen(student1);

        // Assert
        /*
        int toetsCijferVoorStudent = toets1.getToetsCijferVoorStudent(student1);
        assertEquals(0, toetsCijferVoorStudent);

         */
        verify(toets1).verwijderStudentResultaten(student1);
    }

    @Test
    public void opdracht9verwijderStudentGooitRTEBijNullStudent() {
        // Arrange
        Toets toets1 = mock(Toets.class);
        Vak vak = new Vak(List.of(toets1));

        //when(toets1.verwijderStudentResultaten(any())).thenThrow(FoutiefStudentException.class);
        doThrow(FoutiefStudentException.class).when(toets1).verwijderStudentResultaten(isNull());

        // Act and Assert
        assertThrows(
                RuntimeException.class,
                () -> vak.verwijderStudentUitAlleToetsen(null)
        );
    }
}