# bewd-mock-workshop

## Introductie

### 1. Clone deze workshop repository

Gebruik je favoriete git client, download de zip van github of clone direct vanuit intellij (File -> new project from version control).

### 2. Bestudeerd de code

Open een aantal verschillende classes en de bijbehorende unittests. Volg de code door de verschillende classes heen.

Probeer deze vragen te beantwoorden over de code:

1. Welke classes worden er allemaal geraakt door de test. Beantwoord dit voor elke test.
1. Wat voor wijzigingen in de code kunnen er voor zorgen dat deze test faalt?
1. Welke klassen worden er getest?

### 3. Run alle unit tests

Dit kan je in IntelliJ doen door op het test package te rechtsklikken op het project en te kiezen voor 'Run All tests'.

Beantwoord deze vragen, schrijf je antwoorden onder de vragen.

1. Welke tests falen?
1. Kijk alleen naar de testresultaten. Kan je hier al zien waar de bug zit?

## Waarom unit tests klein moeten zijn
Unit tests zijn het meest nuttig als één wijziging in de code ook maar één of enkele tests laat falen. Op die manier kan je er vrij snel achter komen waar de fout zit. Hoe kleiner de 'unit' die je test, hoe specifieker je de test kunt maken en hoe nuttiger deze unit test is om jou en je team te helpen bugs te vinden in de toekomst.

## Isolatie van je SUT
In de applicatie zit één enkele bug in één enkele class. Het doel van deze workshop is niet om de bug te fixen, maar om er voor te zorgen dat de unittests die de buggy class niet horen te testen ook niet meer falen. In de volgende stappen ga je verschillende manieren proberen om de testen te verbeteren door het SUT te verkleinen naar alleen de class die je wilt testen. Je gaat het SUT beter _isoleren_. Je doet dit door enkel de testcode aan te passen (de code in de test/java directory). In geen enkel geval is het nodig of toegestaan om de applicatiecode zelf (alle code onder /main/java) te wijzigen tenzij dit specifiek staat beschreven.

### 4. Gebruik Polymorfie om de SUT te isoleren part1: Interfaces

Bekijk de testen in de class 'VakTest'. De eerste test 'opdracht4getCijferMetEnkeleToetsGeeftCijferTerug' faalt. De SUT van deze test is de klasse 'Vak', maar hier zit niet de bug. Als je wilt dat alleen je 'System Under Test' (Vak in dit geval) getest wordt, en bugs in andere classes (Bijvoorbeeld de *Toets* class) er niet voor zorgen dat deze test faalt. Wat kan je dan aanpassen in je unit test? Hoe verbreken we de afhankelijkheid van *Toets*

1. Vervang de implementatie van de andere classes door gebruik te maken van Polymorfisme. Als de class waar je afhankelijk van bent een interface is dan kan je een eigen 'fake implementatie'' schrijven die vaste waardes teruggeeft. Maak de fake class in de test/java directory in het package 'nl.han.se.bewd.mockworkshop.toets'.
1. Creeer in je test package een nieuwe class welke de interface 'Summatief' implementeert.
1. Om te voldoen aan de interface moet de methode 'getToetsCijferVoorStudent' toegevoegd worden. Maak de methode en laat deze simpelweg altijd een 8 teruggeven (```return 8;```). Vergeet niet de ```@Override``` annotatie toe te voegen.
1. Pas de test aan, zodat deze niet langer een *Toets* instantieert, maar een *FakeSummatief*. De ```studentMaaktToets()``` methode hoeft ook niet meer aangeroepen te worden. Het Arrange stuk van de test komt er nu zo uit te zien:

    ```java
    // Arrange
    Student testStudent = new Student();
    Summatief toets1 = new FakeSummatief();
    Vak vak = new Vak(List.of(toets1));
    ```
1. Run de unittest (of run alle testen). Als het goed is slaagt de *getCijferMetEnkeleToetsGeeftCijferTerug* test nu. Hoera! Er falen nu minder testen van de Vak class, en dat is een goed iets. Je hebt je SUT beter geïsoleerd tegen bugs in andere classes.

### 5. Gebruik Polymorfie om de SUT te isoleren part2: Subclass

Voor een ander geval is er misschien geen interface, in dat geval kunnen we een fake implementatie maken door van de originele class te overerven met het extends keyword en alle methoden te *overriden*.

1. Maak een eigen fake implementatie van *Toets* door een eigen subclass te maken (```extends Toets```). zet deze ook in het test package naast *fakeSummatief*.
1. Het zou handig zijn als we in onze unit test kunnen aangeven wat de fake terug moet geven, dus voegen we aan de fake Toets een extra methode toe waarmee we de fake return value voor ```getToetsCijferVoorStudent()``` kunnen setten. Zie onderstaande implementatie van de *fakeToets* class.

  ```java
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
}
  ```

1. Pas de test *opdracht5getCijferMetMeerdereToetsenGeeftGemiddeldeTerugVanTweeToetsen* aan zodat deze jouw fake Toets gebruikt. Zorg dat je een instantie maakt die een 8 teruggeeft, en maak ook een instantie die een 6 teruggeeft. Gebruik deze twee fakes om de instantie van Vak te maken.
1. Run de test. Als het goed is slaagt deze nu ook. Je hebt de SUT nog beter geïsoleerd tegen bugs in andere classes! 

Helaas (gelukkig?) wordt er niet altijd een interface gebruikt bij afhankelijkheden naar andere classes, en ook wil je niet altijd een subclass maken. Het is best wel wat werk om fake subclasses bij te houden van alle classes die je wilt uitsluiten van je SUT.

Dat moet anders kunnen!?

## Mocking met Mockito

Het maken van fake ([AKA mock](https://en.wikipedia.org/wiki/Mock_object)) versies van classes kunnen we uitbesteden aan de Mockito library. Deze library doet eigenlijk hetzelfde als wat je in de vorige opdrachten zelf hebt gedaan, maar dan simpeler, sneller en uitgebreider. Hiervoor hebben we uiteraard de Mockito library nodig.

### 6. mock() gebruiken om mock/fake objecten te maken

1. Open de maven pom.xml en zoek de mockito library op in de lijst van dependencies. Als je in je eigen projecten ook mockito wilt gebruiken moet je deze zelf toevoegen, in deze workshop is dat al voor je gedaan. You're welcome.
1. Bekijk de test *opdracht6getCijferGeeftEenNulAanStudentenDieDeToetsNietHebbenGemaakt*.
1. We kunnen hier dezelfde aanpak gebruiken als bij de eerdere tests, maar dit keer vragen we Mockito om de fake implementatie te maken. Vervang de regel waar het toets1 object wordt aangemaakt met een aanroep naar de ```mock()``` methode van Mockito. Je moet hier een ```.class``` aan meegeven van de class waar je een mock versie van wilt hebben. Het resultaat is dan als volgt:
    ````java
    // Origineel: Toets toets1 = new Toets();
    Toets toets1 = mock(Toets.class);
    ````
1. Om dit te laten compileren moet de import van de mock methode nog toegevoegd worden. Dit kan je oplossen in IntelliJ door je cursor op de aanroep van mock te zetten en dan met Alt-Enter te kiezen voor 'import static method etc...'. Als het goed is wordt dan bovenin je test bestand de regel ```import static org.mockito.Mockito.mock;``` toegevoegd.
1. Run de test. De test zou moeten slagen.
1. De aanroep naar studentMaaktToets() mag nu helemaal weg uit de test, deze aanroep op het mock object heeft geen effect. Run de test om dit te verifieren. De test blijft slagen. Maar hoe dan?

Als je de implementatie van *getVakCijferForStudent()* in *Vak* bekijkt, zie je dat deze de methode *getToetsCijferVoorStudent()* gebruikt om toetsResultaten op te halen. Ondanks dat deze methode wordt aangeroepen op onze mock versie, gaat dit toch goed. Dit komt doordat mockito voor elke methode een standaard mock implementatie maakt. Deze standaardimplementatie geeft afhankelijk van het return type een null, een lege collectie of een 0 terug. In dit geval is het return type van *getToetsCijferVoorStudent()* een int, en dus zal de mock een '0' teruggeven. Dat is toevallig ook wat onze test verwacht, en daarom gaat het in dit specifieke test geval meteen al goed.

### 7. mock gedrag toevoegen met when(mock).then().

Het is een beetje mazzel dat de vorige test direct slaagde nadat we mock() gebruikt hebben. In dat geval geeft de mock een default van 0 terug, en dat is ook precies wat er in de test verwacht is met assert(). Maar wat nu als we meer verwachten van de mock...

Bekijk de volgende *opdracht7getCijferGeeftHetCorrecteCijferBijMeerdereStudenten* test. Hier wordt de toets door meerdere studenten gemaakt en elke student moet het juiste cijfer terugkrijgen uit *Vak*. In dit geval is het default gedrag van mockito (return 0) niet meer voldoende. Je hebt controle nodig over wat de mock teruggeeft als *getToetsCijferVoorStudent()* aangeroepen wordt. Mockito heeft hiervoor een reeks aan methodes om dit gedrag te definiëren.
1. mock het toets object net als bij de vorige opdracht.
1. Run de falende test. Je ziet in de output dat er twee assertions falen.
1. Voeg de volgende regel toe in het Arrange blok van de test, na het aanmaken van het mock toets1 object:
    ```java
        when(toets1.getToetsCijferVoorStudent(any())).thenReturn(3);
    ```
1. Deze regel betekend kortgezegd: Als toets1.getToetsCijferVoorStudent wordt aangeroepen met maakt niet uit wat voor parameter, geef dan 0 terug.
1. Er zitten twee nieuwe methodes in deze aanroep (when() en any()) die je als imports moet toevoegen, vergelijkbaar als wat je eerder voor mock() hebt gedaan. De *any()* methode noemen we ook wel een *Matcher*. Daar ga je er nog meer van zien in deze workshop.
1. Run de tests, helaas faalt deze nog steeds, maar als het goed is alleen op de tweede assertion voor student2.
1. Verander de when regel zo dat deze niet meer werkt met alle objecten, maar alleen met aanroepen voor student1:
    ```java
        when(toets1.getToetsCijferVoorStudent(student1)).thenReturn(3);
    ```
1. Voeg een hele nieuwe when() toe voor student2 zodat de mock voor student2 het juiste cijfer teruggeeft.
1. Run de tests. Als het goed is slaagt je test!

Uitstekend, met deze methode kan je in veel gevallen de classes die je niet wilt testen buiten scope houden. Prettig! 

Het  op deze manier specificeren wat een mock moet doen heet ook wel *stubben*, *stubbing* of *stubs*. Deze kreten kan je tegenkomen in de documentatie, tutorials of artikelen over het onderwerp mocking.

Wat kunnen we nog meer doen met Mockito?

### 8. Gedrag verifiëren met verify() AKA testen van void methodes

Niet alle methoden hebben een handige return value om het gedrag te valideren. Sommige methodes geven niks (void) terug. Hoe kan je dan na gaan dat de methode zijn werk heeft gedaan in een unit test? 

1. Zie de test *opdracht8verwijderStudentUitAllToetsenVerwijdertStudentUitToets*. De assertion gebruikt nu het Toets opbject direct voor de assertion? De huidige oplossing is niet fantastisch. Het enige wat er nagegaan moet worden is of de juiste methodes op de toets wel of niet aangeroepen zijn, verder rijkt ons SUT niet, dus hoe Toets precies zijn werk doet is niet relevant voor deze test. Mockito heeft hier de verify() methode voor, hiermee kan je nagaan of methodes op een mock zijn aangeroepen, en met welke parameters.
1. Mock de toets.
1. Vervang de huidige assertion met deze Mockito verify():
   ````java
    verify(toets1).verwijderStudentResultaten(student1);
    ````
1. Run de test. Hoe kom je er nu achter of deze test ook echt faalt als de code van *Vak* niet klopt?
1. Verander de implementatie van *verwijderStudentUitAlleToetsen* in de class *Vak* tijdelijk. Je kunt bijvoorbeeld de hele regel in comments zetten door er twee slashes (//) voor te zetten.
1. Run de test opnieuw en bestudeer de output. Als het goed is lijkt deze hier op:
    ```
    Wanted but not invoked:
    toets.verwijderStudentResultaten(
      nl.han.se.bewd.mockworkshop.student.Student
    );
    ```
1. Deze verify methoden zijn een groot onderdeel van Mockito. [Je kunt er in de docs van Mockito meer over lezen](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#1).

### 9. Exceptions gooien met mocks

- Bekijk de test *opdracht9verwijderStudentGooitRTEBijNullStudent*. Deze test slaagt al wel, maar is nu sterk afhankelijk van *Toets*. Deze moet worden ontkoppeld met mocking.
- Er wordt in de assertThrows een null meegegeven aan *verwijderStudentUitAlleToetsen()*. Deze null wordt doorgegeven in de aanroep van *verwijderStudentResultaten* op *Toets*. *Toets.verwijderStudentResultaten()* geeft daarop een *FoutiefStudentException* en deze wordt gevangen in *Vak* en er wordt dan een *RuntimeException* gegooid. Oftewel, de mock van Toets moet een *FoutiefStudentException* gaan gooien.
- Maak de mock van Toets aan in de Arrange sectie.
- In dit geval willen we eigenlijk de volgende constructie gebruiken om de mock een Exception te laten gooien:
  ````java
  when(toets1.verwijderStudentResultaten(any())).thenThrow(FoutiefStudentException.class);
  ````
- Dit werkt niet. Een methode stubben die niks teruggeeft (geen return value, aka void) is wat lastiger en vereist een iets andere mockito syntax. Bovenstaande syntax had ook gewerkt als de methode toevallig een methode was met return value.
  ````java
  doThrow(FoutiefStudentException.class).when(toets1).verwijderStudentResultaten(isNull());
  ````
- Als je wilt dat je mock alleen een Exception gooit als er ook een null wordt meegegeven aan de gestubte methode dan kan je de *isNull()* matcher gebruiken in plaats van de *any()* matcher. Test of dat waar is.

## 10. Mocks injecteren met @Mock en @InjectMocks

1. Bekijk de testen in het java bestand *ToetsTest.java*. Deze testen slagen al, we gaan ze alleen iets meer leesbaar maken.
2. Deze testen gebruiken al mocking, maar als je de Assert onderdelen van de verschillende testen vergelijkt valt je misschien op dat er elke keer exact dezelfde handelingen verricht worden.
3. Dat kan beter. In plaats van zelf de mocks te maken met *mock()*, kan je ook een eenvoudige vorm van Dependency Injection gebruiken van Mockito.
4. Zet op de class ToetsTest de annotatie ```@ExtendWith(MockitoExtension.class)```. Hier mee activeer je mock injectie door Mockito.
5. Verhuis de mock variabelen voor student en de db uit elke test naar een class variabele en zat daarboven de ```@Mock``` annotatie.
6. Zorg er voor dat alle verwijzingen kloppen en run de tests. Als het goed is blijven de testen slagen.
7. Het aanmaken van het SUT en het injecteren van de mockDB gebeurt nog wel in elke test apart :( . Verplaats het sut naar een class variabele, en annoteer deze met ```@InjectMocks```. Verwijder de regel in de individuele testen waar het sut wordt aangemaakt en de .setDB() wordt aangeroepen.
8. De testen zien er nu als het goed is al een stuk opgeruimder uit:
  ```java
  @Test
  void opdracht10getToetsCijferVoorStudentGeeftToetsCijferVoorStudent2() {
      // Arrange
      when(mockDB.vraagResultatenOp(mockStudent)).thenReturn(List.of("8"));
      // Act
      int result = sut.getToetsCijferVoorStudent(mockStudent);
      // Assert
      assertEquals(8, result);
  }
  ```
  - Clean! <3
9. Als het goed blijven de testen werken. Mockito maakt nu een nieuwe instantie van het SUT (*Toets*) en injecteerd de mock *ToetsResultaatRegistratieDB* via de setter van *Toets*. Magisch en handig!

## 11. Meer Mockito

1. Probeer zelf extra test cases te schrijven met de volgende mockito features.
   1. [Verifying exact number of invocations](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#4)
   1. [Verification in order](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#6)
   1. [Making sure interaction(s) never happened on mock](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#7)
   1. [Finding redundant invocations](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#8)
   1. [Stubbing consecutive calls](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#10)
