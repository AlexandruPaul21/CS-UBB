package repository.mock;

import model.ComputerRepairedForm;
import repository.ComputerRepairedFormRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ComputerRepairedFormInMemoryRepository extends AbstractRepository<Integer, ComputerRepairedForm> implements ComputerRepairedFormRepository {

    @Override
    public List<ComputerRepairedForm> filterByEmployee(String employee) {
        return getAll().stream().filter(x->x.getEmployee().toLowerCase().equals(employee.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<ComputerRepairedForm> filterByEmployeeAndDate(String employee, String date) {
        return getAll().stream().filter(x->x.getEmployee().toLowerCase().equals(employee.toLowerCase()) && x.getDate().equals(date)).collect(Collectors.toList());
    }
}
