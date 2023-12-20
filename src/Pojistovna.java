import java.util.ArrayList;
import java.util.Scanner;

public class Pojistovna {
    //obsahuje metody pro komunikaci
    Scanner sc = new Scanner(System.in, "UTF-8");
    Databaze databaze = new Databaze();

    // metoda, která naplní databázi pokusnými pojištěnci
    public void naplnDatabaziPojistencu() {
        Pojistenec dana = new Pojistenec("DANA", "GABRIŠOVÁ", "2003-01-03", "744 702 665");
        Pojistenec danka = new Pojistenec("DANA", "GABRIŠOVÁ", "2023-01-03", "744 702 665");
        Pojistenec vesna = new Pojistenec("VESNA", "MÁJOVÁ", "1991-08-21", "744 777 777");
        Pojistenec stepan = new Pojistenec("ŠTĚPÁN", "KAHLE", "1960-12-12", "724 558 820");
        Pojistenec jiri = new Pojistenec("JIŘÍ", "VYHNÁLEK", "1971-04-12", "724 012 520");
        Pojistenec david = new Pojistenec("DAVID", "BLÁHA", "1970-05-11", "744 111 500");
        databaze.pridejPojistence(dana, danka, stepan, jiri, david, vesna);
        dana.setPojisteni(Pojisteni.Zivotni, Pojisteni.Domacnosti);
        stepan.setPojisteni(Pojisteni.Nemovitosti, Pojisteni.Domacnosti, Pojisteni.Zivotni);
        jiri.setPojisteni(Pojisteni.Nemovitosti, Pojisteni.Odpovednosti);
        david.setPojisteni(Pojisteni.Cestovni);
        vesna.setPojisteni(Pojisteni.Zivotni, Pojisteni.Odpovednosti, Pojisteni.Cestovni);
    }

    // metoda pro vypsání nabídky možných akcí
    public void vstupniObrazovka() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Evidence pojištěných");
        System.out.println("--------------------------");
        System.out.println();
        System.out.println("Vyberte si akci:");
        System.out.println("1 - Přidat nového pojištěného");
        System.out.println("2 - Zobrazit všechny pojištěné");
        System.out.println("3 - Vyhledat pojištěného");
        System.out.println("4 - Změna pojištění");
        System.out.println("5 - Konec");
        System.out.println();
    }

    // hlavní metoda, která vypíše nabídku možností a poté reaguje na volby uživatele
    public void vyberAkci() {
        String volba = "";
        while (!volba.equals("5")) {
            vstupniObrazovka();
            volba = sc.nextLine();
            switch (volba) {
                case "1":
                    pridejNovehoPojisteneho();
                    break;
                case "2":
                    vypisVsechnyPojistence();
                    break;
                case "3":
                    najdiPojisteneho();
                    break;
                case "4":
                    ArrayList<Pojistenec> pojistenci = najdiPojisteneho();
                    zmenPojisteni(pojistenci);
                    break;
                case "5":
                    // ukončí cyklus while a tím ukončí program
                    break;
                default:
                    System.out.println("Neplatná volba.");
            }
            if (!volba.equals("5"))   {
                System.out.println();
                System.out.println("Stiskněte enter pro pokračování.");
                String x = sc.nextLine();
            }

        }
    }

    // metoda, která načte jméno požadovaného pojištěnce. Musí mít alespoň dva znaky.
    // odstraní případné prázdné znaky před a za jménem a odstraní rozlišování velkých/malých písmen
    public String nactiJmeno() {
        String jmeno = "";
        while (jmeno.length() < 2) {
            System.out.println("Zadejte jméno pojištěného: ");
            jmeno = sc.nextLine().trim().toUpperCase();
        }
        return jmeno;
    }

    // metoda, která načte příjmení. Musí mít alespoň 2 znaky.
    public String nactiPrijmeni() {
        String prijmeni = "";
        while (prijmeni.length() < 2) {
            System.out.println("Zadejte příjmení pojištěného: ");
            prijmeni = sc.nextLine().trim().toUpperCase();
        }
        return prijmeni;
    }

    // metoda pro zadání nového pojištěnce
    public void pridejNovehoPojisteneho() {
        String jmeno = nactiJmeno();
        String prijmeni = nactiPrijmeni();
        System.out.println("Zadejte telefonní číslo: ");
        // telefonní číslo je nepovinné, nekontrolujeme funkčnost
        String telefon = sc.nextLine();
        Boolean pridano = false;
        String datum;
        // kontrola správného formátu zadaného data narození probíhá při ukládání do databáze, kdy
        // se při vytvoření instance pojištěnce ukládá datum narození ve formátu LocalDate
        while (!pridano) {
            System.out.println("Zadejte datum narození ve tvaru yyyy-mm-dd: ");
            datum = sc.nextLine();
            System.out.println();
            try {
                databaze.pridejPojistence(jmeno, prijmeni, datum, telefon);
                pridano = true;
            } catch (Exception e) {
                System.out.println("Skutečně je potřeba, abyste zadali datum narození ve správném tvaru.");
            }
        }
        System.out.println("Nový pojištěnec byl uložen.");
    }

    // metoda, která vyhledá pojištěnce podle zadaného jména
    public ArrayList<Pojistenec> najdiPojisteneho() {
        // zadání jména a příjmení uživatelem
        String jmeno = nactiJmeno();
        String prijmeni = nactiPrijmeni();
        System.out.println();
        // vyhledání pojištěnců databází
        ArrayList<Pojistenec> pojistenci = databaze.najdiPojistence(jmeno, prijmeni);
        // vypsání všech nalezených pojištěnců s daným jménem a příjmením
        if (pojistenci.size() > 0) {
            System.out.println("Bylo nalezeno: ");
            System.out.println("--------------");
            for (int i = 1; i <= pojistenci.size(); i++) {
                System.out.print(i + " ");
                System.out.println(pojistenci.get(i - 1));
            }
        } else {
            System.out.println("Nebyl nalezen žádný pojištěnec se zadaným jménem.");
        }
        return pojistenci;
    }

    // metoda, která vypíše všechny pojištěnce
    public void vypisVsechnyPojistence() {
        System.out.println("Seznam všech pojištěných: ");
        System.out.println("-------------------------");
        for (Pojistenec pojistenec : databaze.getPojistenci()) {
            System.out.println(pojistenec);
        }
    }

    // metoda pro změny pojištění - přidání nebo zrušení
    public void zmenPojisteni(ArrayList<Pojistenec> pojistenci) {
        // vybírání z více pojištěnců stejného jména
        Pojistenec vybranyPojistenec = pojistenci.get(0);
        int volba = -1;
        if (pojistenci.size() > 1) {
            while ((volba < 1) || (volba > pojistenci.size())) {
                System.out.println("U kterého pojištěnce budete měnit pojištění?");
                try {
                    volba = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("Vaše volba je neplatná.");
                }
            }
            for (int i = 0; i < pojistenci.size(); i++) {
                if (volba == i + 1) {
                    vybranyPojistenec = pojistenci.get(i);
                }
            }
        }
        // volba, jestli budeme pojištění zakládat nebo rušit
        volba = 100;
        while ((volba != 1) && (volba != 2)) {
            System.out.println("Přejete si přidat pojištění? Stiskněte 1");
            System.out.println("Přejete si zrušit pojištění? Stiskněte 2");
            try {
                volba = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Vaše volba je neplatná.");
            }
        }
        // založení nového pojištění
        if (volba == 1) {
            int druhPojisteni = 100;
            while ((druhPojisteni < 1) || (druhPojisteni > 5)) {
                System.out.println("Zvolte druh zakládaného pojištění:");
                System.out.println("1 - životní");
                System.out.println("2 - odpovědnosti");
                System.out.println("3 - domácnosti");
                System.out.println("4 - nemovitosti");
                System.out.println("5 - cestovní");
                try {
                    druhPojisteni = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("Vaše volba je neplatná.");
                }
            }
            switch (druhPojisteni) {
                case 1:
                    zalozeniPojisteni(vybranyPojistenec, Pojisteni.Zivotni);
                    break;
                case 2:
                    zalozeniPojisteni(vybranyPojistenec, Pojisteni.Odpovednosti);
                    break;
                case 3:
                    zalozeniPojisteni(vybranyPojistenec, Pojisteni.Domacnosti);
                    break;
                case 4:
                    zalozeniPojisteni(vybranyPojistenec, Pojisteni.Nemovitosti);
                    break;
                case 5:
                    zalozeniPojisteni(vybranyPojistenec, Pojisteni.Cestovni);
                    break;
            }

            // zrušení pojištění
        } else {
            ruseniPojisteni(vybranyPojistenec, Pojisteni.Zivotni);
            ruseniPojisteni(vybranyPojistenec, Pojisteni.Odpovednosti);
            ruseniPojisteni(vybranyPojistenec, Pojisteni.Domacnosti);
            ruseniPojisteni(vybranyPojistenec, Pojisteni.Nemovitosti);
            ruseniPojisteni(vybranyPojistenec, Pojisteni.Cestovni);
            if (vybranyPojistenec.getPojisteni().contains(Pojisteni.Zadne))  {
                System.out.println("Vybraný pojištěný nemá založeno žádné pojištění, které by bylo možné zrušit.");
            }
        }
    }

    //metoda pro založení pojištění, pokud jej dotyčný ještě nemá založeno
    public void zalozeniPojisteni(Pojistenec pojistenec, Pojisteni pojisteni) {
        if (pojistenec.getPojisteni().contains(pojisteni)) {
            System.out.println("Toto pojisteni už má tento pojištěný založeno.");
        } else {
            pojistenec.setPojisteni(pojisteni);
            System.out.println("Bylo založeno " + pojisteni + ".");
        }
    }

    //metoda pro zrušení pojištění. Ke zrušení dojde pouze při potvrzení slovem ano.
    public void ruseniPojisteni(Pojistenec pojistenec, Pojisteni pojisteni) {
        if (pojistenec.getPojisteni().contains(pojisteni)) {
            System.out.println("Přejete si zrušit " + pojisteni + "? ano/ne ");
            String potvrzeni = sc.nextLine().trim().toLowerCase();
            if (potvrzeni.equals("ano")) {
                pojistenec.zrusPojisteni(pojisteni);
                System.out.println("Pojištění bylo zrušeno.");
            }
        }
    }
}