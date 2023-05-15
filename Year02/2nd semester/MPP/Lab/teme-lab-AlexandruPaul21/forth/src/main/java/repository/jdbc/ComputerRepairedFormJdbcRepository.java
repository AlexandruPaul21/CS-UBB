package repository.jdbc;

import model.ComputerRepairedForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.ComputerRepairedFormRepository;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class ComputerRepairedFormJdbcRepository implements ComputerRepairedFormRepository {
    private JdbcUtils jdbcUtils;

    private final static Logger logger= LogManager.getLogger(ComputerRepairRequestJdbcRepository.class);


    public ComputerRepairedFormJdbcRepository(Properties props) {
        jdbcUtils=new JdbcUtils(props);
    }

    @Override
    public List<ComputerRepairedForm> filterByEmployee(String employee) {
        return null;
    }

    @Override
    public List<ComputerRepairedForm> filterByEmployeeAndDate(String employee, String date) {
        return null;
    }

    @Override
    public ComputerRepairedForm add(ComputerRepairedForm elem) {
        logger.traceEntry("saving repaired form {} ",elem);
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into RepairedForms (services, price, date, employee,requestID) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){

            preStmt.setString(1,elem.getServices());
            preStmt.setDouble(2,elem.getPrice());
            preStmt.setString(3,elem.getDate());
            preStmt.setString(4,elem.getEmployee());
            preStmt.setInt(5,elem.getRequest().getID());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
            if (result>0){
                //obtinem ID-ul generat de baza de date
                ResultSet rs = preStmt.getGeneratedKeys();
                if (rs.next()) {
                    int id=rs.getInt(1);
                    elem.setID(id);
                    logger.trace("Generated id {} ",id);
                }

            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        return logger.traceExit(elem);

    }

    @Override
    public void delete(ComputerRepairedForm elem) {

    }

    @Override
    public void update(ComputerRepairedForm elem, Integer id) {

    }

    @Override
    public ComputerRepairedForm findById(Integer id) {
        return null;
    }

    @Override
    public Iterable<ComputerRepairedForm> findAll() {
        return null;
    }

    @Override
    public Collection<ComputerRepairedForm> getAll() {
        return null;
    }
}
