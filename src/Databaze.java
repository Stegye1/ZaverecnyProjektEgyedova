import java.util.ArrayList;

public class Databaze {
    //tvoří a upravuje kolekci pojištěnců pomocí ArrayListu
    private ArrayList<Pojistenec> pojistenci = new ArrayList<>();

    public ArrayList<Pojistenec> getPojistenci() {
        return pojistenci;
    }

    public void setPojistenci(ArrayList<Pojistenec> pojistenci) {
        this.pojistenci = pojistenci;
    }

    // dvě přetížení metody pro zadávání nových pojištěnců s různými parametry
    public void pridejPojistence(String jmeno, String prijmeni, String datumNarozeni, String telefon) {
        pojistenci.add(new Pojistenec(jmeno, prijmeni, datumNarozeni, telefon));
    }

    public void pridejPojistence(Pojistenec...pojistencii)   {
        for (Pojistenec pojistenec : pojistencii)    {
            pojistenci.add(pojistenec);
        }
    }

    // metoda pro vyhledání pojištěnce podle jména a příjmení
    public ArrayList<Pojistenec> najdiPojistence(String jmeno, String prijmeni)    {
        ArrayList<Pojistenec> nalezeno = new ArrayList<>();
        for (Pojistenec pojistenec : pojistenci)    {
            if ((pojistenec.getJmeno().equals(jmeno))&&(pojistenec.getPrijmeni().equals(prijmeni))) {
                nalezeno.add(pojistenec);
            }
        }
        return nalezeno;
    }
}
