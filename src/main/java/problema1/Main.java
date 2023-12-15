package problema1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;
import java.util.*;
import java.lang.*;

import java.util.Map;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
public class Main {
  /*  public static void afisare_dupa_nume_magazin(Statement statement)  {
       //5 String name="Dana";
        String sql="select * from Magazin WHERE nume= 'Dana'";
      //  System.out.println("\n---"+mesaj+"---");
        try(ResultSet rs=statement.executeQuery(sql))
        {
            while (rs.next())
                System.out.println("id="+ rs.getInt(1) + ", nume="+ rs.getString(2) + ", adresa=" + rs.getString(3));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }*/
    private static void afisare_dupa_nume_magazin(Connection connection) throws SQLException{
        try
        {
            String sql="select * from persoane where nume=?";
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1, "Dana");
           // ps.setInt(2, 18);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                System.out.println("id="+rs.getInt(1)+", nume= "+ rs.getString(2) + ", adresa="+rs.getString(3));
            connection.close();
            ps.close();
            rs.close();

            }
        catch (SQLException e)
        { e.printStackTrace();
        }
    }


    private static Set<Produs> extrageProduseMagazin(Connection connection, int idMagazin) throws SQLException {
        Set<Produs> produseMagazin = new HashSet<>();

        String selectQuery = "SELECT * FROM Produs WHERE id_magazin = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, idMagazin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idProdus = resultSet.getInt("idProdus");
                    String nume = resultSet.getString("nume");
                    int vechime = resultSet.getInt("vechime");

                    produseMagazin.add(new Produs(idProdus, nume, vechime, idMagazin));
                }
            }
        }

        return produseMagazin;
    }

    private static void afiseazaDate(Connection connection) throws SQLException {
        Map<Integer, Magazin> magazinMap = new HashMap<>();

        Set<Produs> produse_magazin=new HashSet<Produs>();

        // Extrage datele din tabela Magazin
        String selectMagazinQuery = "SELECT * FROM Magazin";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectMagazinQuery)) {

            while (resultSet.next()) {
                int idMagazin = resultSet.getInt("id_magazin");
                String nume = resultSet.getString("nume");
                String adresa = resultSet.getString("adresa");
                produse_magazin=extrageProduseMagazin(connection, idMagazin);
                magazinMap.put(idMagazin, new Magazin( idMagazin, nume, adresa, produse_magazin));
            }
        }
        // Afiseaza colectia Map
        magazinMap.forEach((id, magazin) -> System.out.println(magazin));
       // magazinMap.stream().forEach(System.out::println);

        magazinMap.values()
                .stream()
                .sorted((a,b)->a.nume().compareToIgnoreCase(b.nume())).forEach(System.out::println);


    }
    public static void adaugare_produs(Connection connection, String nume, String vechime, int id_magazin) {
        String sql = "insert into produs (nume, vechime ,id_magazin) values (?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            //  ps.setInt(1, id);
            ps.setString(1, nume);
            ps.setString(2, vechime);
            ps.setInt(3, id_magazin);
            int nr_randuri = ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de adaugare=" + nr_randuri);
        } catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }

    }
    public static void adaugare_magazin(Connection connection, int id, String nume, String adresa) {
        String sql = "insert into magazin (nume, adresa) values (?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
          //  ps.setInt(1, id);
            ps.setString(1, nume);
            ps.setString(2, adresa);
            int nr_randuri = ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de adaugare=" + nr_randuri);
        } catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }

    }
    public static void afisare_tabela_magazine(Statement statement, String mesaj)
    {
        String sql="select * from magazin";
        System.out.println("\n---"+mesaj+"---");
        try(ResultSet rs=statement.executeQuery(sql)) {
            while (rs.next()) System.out.println("id= "+ rs.getInt(1) + ", nume= "+ rs.getString(2) + " adresa=" + rs.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void stergere(Connection connection)
    {
        String sql="delete from ProduS ORDER BY vechime ASC";
        try(Statement ps=connection.createStatement())
        {

            int nr_randuri=ps.executeUpdate(sql);
            System.out.println("\nNumar randuri afectate de modificare="+nr_randuri);
        } catch(SQLException e)
        { System.out.println(sql); e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {

        String url = "jdbc:mysql://localhost:3306/schema_labsql";
        try {
            Connection connection = DriverManager.getConnection(url, "root", "Angi2468");
            Statement statement = connection.createStatement();

            while (true) {
                System.out.println("Meniu:");
                System.out.println("1. Adauga magazin");
                System.out.println("2. Adauga produs");
                System.out.println("3. Afiseaza datele din magazin");
                System.out.println("4. Afiseaza datele");
                System.out.println("5. Dupa nume");
                System.out.println("6. Dupa stergere");
                System.out.println("0. Iesire");

                System.out.print("Alege o optiune: ");
                Scanner scanner=new Scanner(System.in);
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        BufferedReader flux_in = new BufferedReader(new InputStreamReader (System.in));
                        String nume= flux_in.readLine();
                        String adresa= flux_in.readLine();
                        adaugare_magazin(connection, 1, nume, adresa);

                        break;
                    case 2:
                        BufferedReader flux_in2 = new BufferedReader(new InputStreamReader (System.in));
                        System.out.print("Introdu numele produsului: ");
                        String name= flux_in2.readLine();
                        System.out.print("Introdu vechimea produsului: ");
                        String vechime= flux_in2.readLine();
                        System.out.print("Introdu id-ul magazinului: ");
                        int idMagazin = scanner.nextInt();
                        adaugare_produs(connection,name,vechime,idMagazin);
                        break;
                    case 3:
                        afisare_tabela_magazine(statement, "Dupa adaugare");
                        break;
                    case 4:
                        afiseazaDate(connection);
                        break;
                    case 5:
                        afisare_dupa_nume_magazin(connection);
                        break;
                    case 6:
                        stergere(connection);
                    case 0:
                        System.out.println("Programul se inchide...");
                        System.exit(0);
                    default:
                        System.out.println("Optiune invalida. Reincearca.");
                        break;
                }
            }

        }
        catch (SQLException e) { e.printStackTrace(); }
    }

}
