import java.math.BigDecimal;
import java.time.LocalDate;

public class Users {
    LocalDate date;
    BigDecimal faiz;
    BigDecimal esasMebleg;
    BigDecimal ayliqOdenis;
    BigDecimal qaliqBorc;

     Users() {
        this.date = null;
        this.faiz = BigDecimal.ZERO;
        this.esasMebleg = BigDecimal.ZERO;
        this.ayliqOdenis = BigDecimal.ZERO;
        this.qaliqBorc = BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "Users{" +
                "Tarix=" + date +
                ", Faiz=" + faiz +
                ", Əsas məbləğ=" + esasMebleg +
                ", Aylıq ödəniş=" + ayliqOdenis +
                ", Qalıq borc=" + qaliqBorc +
                '}';
    }
}
