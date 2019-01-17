package Ipoteka_With_Db;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Ipoteka {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BigDecimal aylarinSayi = null;
        BigDecimal umumiGelir = null;
        BigDecimal odenileBilenMebleg = null;
        try {
            System.out.println("Doğum tarixinizi qeyd edin: gün.ay.il");
            String dateString = sc.next();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(dateString, dateFormat);
            long yasamAyi = (((12 - date.getMonthValue() + LocalDate.now().getMonthValue()) + (LocalDate.now().getYear() - date.getYear() - 1) * 12));
            if (yasamAyi >= 365 * 25) {
                System.out.println("Bağışlayın sizə ipoteka verə bilmərik, yaşınız pensiya yasini keçmişdir!");
            } else {
                System.out.println("Ipoteka məbləğini daxil edin: ");
                BigDecimal umumiSumma = sc.nextBigDecimal();
                System.out.println("Ayliq gəlirinizi daxil edin: ");
                BigDecimal ilkGelir = sc.nextBigDecimal();
                System.out.println("Həyat yoldaşınız varmı?: beli/xeyir");
                String hy = sc.next();
                if (hy.toUpperCase().equals("BELI")) {
                    System.out.println("Həyat yoldaşınızın gəlirini daxil edin: ");
                    BigDecimal ikinciGelir = sc.nextBigDecimal();
                    umumiGelir = ilkGelir.add(ikinciGelir);
                } else if (hy.toUpperCase().equals("XEYIR")) {
                    umumiGelir = ilkGelir;
                }
                else{
                    System.out.println("Daxil etme yanlisdir!");
                }
                System.out.println("Siz və sizin himayənizdə olan ailə üzvlərinin sayını daxil edin: ");
                BigDecimal aileSayi = sc.nextBigDecimal();
                odenileBilenMebleg = umumiGelir.subtract((aileSayi.multiply(BigDecimal.valueOf(123))));
                System.out.println("Ipotekaya ödəyə biləcəyiniz maksimal məbləğ = " + odenileBilenMebleg + "\n");
                Ipoteka ipoteka = new Ipoteka();
                if ((12 * 65 - yasamAyi) >= 12 * 25) {
                    aylarinSayi = BigDecimal.valueOf(25).multiply(BigDecimal.valueOf(12));
                    ipoteka.hesabat(aylarinSayi, odenileBilenMebleg, umumiSumma);
                } else {
                    aylarinSayi = BigDecimal.valueOf(12 * 65).subtract(BigDecimal.valueOf(yasamAyi));
                    ipoteka.hesabat(aylarinSayi, odenileBilenMebleg, umumiSumma);

                }
            }
        }catch (InputMismatchException e){
            System.out.println("Daxil etmə yalnışdır!");
            System.out.println(e.getMessage());
        }catch (DateTimeParseException e){
            System.out.println("Daxil etmə yalnışdır!");
            System.out.println(e.getMessage());
        }
    }


    public void hesabat(BigDecimal aylarinSayi, BigDecimal odenileBilenMebleg, BigDecimal umumiSumma) {
        List<Users> usersList = new ArrayList<>();
        LocalDate odenisTarixi = null;
        odenisTarixi = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 05);

        BigDecimal faizDerecesi = BigDecimal.valueOf(7);
        BigDecimal ayliqFaizDerecesi = faizDerecesi.divide(BigDecimal.valueOf(1200), MathContext.DECIMAL128);

        BigDecimal faiz = null;
        BigDecimal esasMebleg = null;
        BigDecimal ayliqOdenis = null;
        BigDecimal qaliqBorc = null;
        ayliqOdenis = umumiSumma.multiply(ayliqFaizDerecesi).divide(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(1).divide((BigDecimal.valueOf(1).add(ayliqFaizDerecesi)).pow(aylarinSayi.intValue()), MathContext.DECIMAL128.DECIMAL128)), MathContext.DECIMAL128);
        if (odenileBilenMebleg.compareTo(ayliqOdenis) >= 0) {
            for (int i = 1; i <= aylarinSayi.intValue(); i++) {
                odenisTarixi = odenisTarixi.plusMonths(1);

                faiz = umumiSumma.multiply(ayliqFaizDerecesi);
                esasMebleg = ayliqOdenis.subtract(faiz);
                umumiSumma = umumiSumma.subtract(esasMebleg);
                qaliqBorc = umumiSumma;

                Users users = new Users();
                users.setFaiz(faiz.setScale(2, BigDecimal.ROUND_HALF_EVEN));
                users.setAyliqOdenis(ayliqOdenis.setScale(2, BigDecimal.ROUND_HALF_EVEN));
                users.setDate(odenisTarixi);
                users.setEsasMebleg(esasMebleg.setScale(2, BigDecimal.ROUND_HALF_EVEN));
                users.setQaliqBorc(qaliqBorc.setScale(2, BigDecimal.ROUND_HALF_EVEN));
                usersList.add(users);
            }
            DbConnection dbConnection=new DbConnection();
            dbConnection.dataBaseInsert(usersList);
            System.out.println("Ödəniş cədvəli verilənlər bazasına uğurla əlavə olundu!");
            System.out.println("Elave olunan datalar siyahisi!");
            for (Users user : usersList) {
                System.out.println(user);
            }
        } else {
            System.out.println("Sizə gəlirinizin az olması ile bağli ipoteka verə bilmirik!");
        }

    }
}

