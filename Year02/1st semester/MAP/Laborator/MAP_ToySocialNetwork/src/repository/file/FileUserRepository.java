package repository.file;

import domain.User;
import domain.validators.Validator;
import repository.memory.MemoryUserRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUserRepository extends MemoryUserRepository {
    private String fileName;

    public FileUserRepository(Validator<User> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    @Override
    public User save(User entity) {
        User user = super.save(entity);
        saveData();
        return user;
    }

    @Override
    public User delete(Long aLong) {
        User user = super.delete(aLong);
        saveData();
        return user;
    }

    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] words = line.split(";");
                User user = new User(words[1], words[2], words[3], words[4]);
                user.setId(Long.parseLong(words[0]));
                super.save(user);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveData() {
        BufferedWriter out = null;
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            out = new BufferedWriter(fileWriter);
            for (User user : findAll()) {
                out.write(user.writeable());
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

    @Override
    public User update(User entity) {
        User user = super.update(entity);
        saveData();
        return user;
    }
}
