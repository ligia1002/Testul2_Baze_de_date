package problema2;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
class MasinaMapper implements RowMapper<Masina>{
    @Override
    public Masina mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Masina masina = new Masina(
            rs.getInt("nr_matriculare"),
            rs.getString("marca"),
            rs.getString("modelul"),
            rs.getString("culoarea")
                                );
         return masina;

    }
}

