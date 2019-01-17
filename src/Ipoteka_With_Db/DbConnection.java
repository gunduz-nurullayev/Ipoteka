package Ipoteka_With_Db;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DbConnection {

    public void dataBaseInsert(List<Users> users){
        Properties p = new Properties();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            p.load(new FileReader("config.properties"));
            String url = p.getProperty("url");
            String driver = p.getProperty("driver");
            String username = p.getProperty("username");
            String password = p.getProperty("password");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            if (!connection.isClosed()) {
                connection.setAutoCommit(false);
                System.out.println("Successful!");
            }
            String sql = "insert into ipoteka(odenis_tarixi,faiz,esas_mebleg,ayliq_odenis,qaliq_borc) " +
                    " values(?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < users.size(); i++) {
                Date date=Date.valueOf(users.get(i).getDate());
                ps.setDate(1, date);
                ps.setBigDecimal(2, users.get(i).getFaiz());
                ps.setBigDecimal(3, users.get(i).getEsasMebleg());
                ps.setBigDecimal(4, users.get(i).getAyliqOdenis());
                ps.setBigDecimal(5, users.get(i).getQaliqBorc());
                ps.executeUpdate();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
