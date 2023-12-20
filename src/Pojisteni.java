public enum Pojisteni {
    Zadne ("žádné pojištění"),
    Zivotni ("životní pojištění"),
    Odpovednosti ("pojištění odpovědnosti"),
    Domacnosti ("pojištění domácnosti"),
    Nemovitosti ("pojištění nemovitosti"),
    Cestovni ("cestovní pojištění");

    private String text;

    Pojisteni(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
