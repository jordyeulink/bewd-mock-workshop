package nl.han.se.bewd.mockworkshop.toets;

import nl.han.se.bewd.mockworkshop.student.Student;

public class FakeToets extends Toets {

    int fakeReturnValue = 0;

    public void setReturnValueForGetToetsCijferVoorStudent(int fakeReturnvalue) {
        this.fakeReturnValue = fakeReturnvalue;
    }

    @Override
    public int getToetsCijferVoorStudent(Student s) {
        return fakeReturnValue;
    }

    @Override
    public void studentMaaktToets(Student stud, int cijfer) {
        db.voegResultaatToe(stud, Integer.toString(cijfer));
    }
}
