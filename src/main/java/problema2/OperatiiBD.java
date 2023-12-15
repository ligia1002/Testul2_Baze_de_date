package problema2;

 import java.sql.SQLException;
 import java.lang.*;
 import java.util.List;
 import javax.sql.DataSource;
 import org.springframework.jdbc.core.JdbcTemplate;
 class OperatiiBD {
     private DataSource dataSource;
     private JdbcTemplate jdbcTemplateObject;
     public void setDataSource(DataSource dataSource)
     { this.dataSource = dataSource;
         this.jdbcTemplateObject = new JdbcTemplate(dataSource);
     }
     public void insert(int nr_matricol, String marca, String model, String culoarea)
     {
         String SQL = "insert into masini (nr_matriculare, marca, modelul, culoarea ) values (?, ?, ?, ?)";
         jdbcTemplateObject.update( SQL, nr_matricol, marca, model, culoarea );
     }
     public List<Masina> getListaMasini()
     {
         String SQL = "select * from masini";
         List <Masina> mas= jdbcTemplateObject.query(SQL,new MasinaMapper());
         return mas; }
     public void delete(int nr_matriculare) {
         String SQL = "delete from masini where nr_matriculare = ?";
         jdbcTemplateObject.update(SQL, nr_matriculare);
     }
     public Masina getMasina(int nr_matriculare)
     {
            try{
             String SQL = "select * from masini where nr_matriculare = ?";
             Masina masina = jdbcTemplateObject.queryForObject(SQL, new Object[]{nr_matriculare}, new MasinaMapper());
             return masina;
            } catch (Exception e) {
                System.out.println("nu exista acest numar matricol.");
                return null;
            }

     }
     public int numar(String marca)
     {
         String SQL = "select count(*) from masini where marca = ?";
         int nr = jdbcTemplateObject.queryForObject(SQL, Integer.class, marca);
         return nr;
     }

 }

