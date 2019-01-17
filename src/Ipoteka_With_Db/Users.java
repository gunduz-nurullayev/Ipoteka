package Ipoteka_With_Db;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Users {
    private LocalDate date;
    private BigDecimal faiz;
    private BigDecimal esasMebleg;
    private BigDecimal ayliqOdenis;
    private BigDecimal qaliqBorc;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getFaiz() {
        return faiz;
    }

    public void setFaiz(BigDecimal faiz) {
        this.faiz = faiz;
    }

    public BigDecimal getEsasMebleg() {
        return esasMebleg;
    }

    public void setEsasMebleg(BigDecimal esasMebleg) {
        this.esasMebleg = esasMebleg;
    }

    public BigDecimal getAyliqOdenis() {
        return ayliqOdenis;
    }

    public void setAyliqOdenis(BigDecimal ayliqOdenis) {
        this.ayliqOdenis = ayliqOdenis;
    }

    public BigDecimal getQaliqBorc() {
        return qaliqBorc;
    }

    public void setQaliqBorc(BigDecimal qaliqBorc) {
        this.qaliqBorc = qaliqBorc;
    }

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
