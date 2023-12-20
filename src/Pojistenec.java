import java.time.LocalDate;
import java.util.EnumSet;

public class Pojistenec {
    // atributy, které pojištěnec uchovává
    private String jmeno;
    private String prijmeni;
    private LocalDate datumNarozeni;
    private String telefon;
    private EnumSet pojisteni=EnumSet.of(Pojisteni.Zivotni,Pojisteni.Domacnosti,Pojisteni.Odpovednosti,Pojisteni.Nemovitosti,Pojisteni.Cestovni);

    // konstruktor
    public Pojistenec(String jmeno, String prijmeni, String datumNarozeni, String telefon) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.datumNarozeni = LocalDate.parse(datumNarozeni);
        this.telefon = telefon;
        this.pojisteni = EnumSet.of(Pojisteni.Zadne);
    }

    // metoda, která vypočítá věk pojištěnce
    public int spocitejVek()  {
        LocalDate dnesek = LocalDate.now();
        LocalDate posunuteDatumNarozeni = datumNarozeni;
        int vek = 0;

        while (dnesek.isAfter(posunuteDatumNarozeni.minusDays(1)))   {
            posunuteDatumNarozeni=posunuteDatumNarozeni.plusYears(1);
            vek++;
        }
        return vek-1;
    }

    // gettery a settery
    public String getJmeno() {
        return jmeno;
    }
    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }
    public String getPrijmeni() {
        return prijmeni;
    }
    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }
    public LocalDate getDatumNarozeni() {
        return datumNarozeni;
    }
    public void setDatumNarozeni(LocalDate datumNarozeni) {
        this.datumNarozeni = datumNarozeni;
    }
    public String getTelefon() {
        return telefon;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public EnumSet getPojisteni() {
        return pojisteni;
    }

    // metoda pro přidání jednoho nebo více nových pojištění pojištěnci
    public void setPojisteni(Pojisteni...novaPojisteni) {
        for (Pojisteni pojistenii : novaPojisteni)   {
            this.pojisteni.add(pojistenii);
        }
        if (pojisteni.contains(Pojisteni.Zadne)) {
            pojisteni.remove(Pojisteni.Zadne);
        }
    }

    // metoda pro zrušení vybraného pojištění
    public void zrusPojisteni(Pojisteni rusenePojisteni)  {
        this.pojisteni.remove(rusenePojisteni);
        if (pojisteni.size()<1) {
            pojisteni.add(Pojisteni.Zadne);
        }
    }

    // metoda toString definuje formát výpisu pojištěnce
    @Override
    public String toString(){
        return String.format("%s %s   %d let  %s  %s ", getJmeno(), getPrijmeni(), spocitejVek(), getTelefon(), getPojisteni());
    }
}
