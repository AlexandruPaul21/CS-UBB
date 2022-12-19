package repository.file;

import domain.Friendship;
import domain.validators.Validator;
import repository.memory.MemoryFriendshipRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class FileFriendshipRepository extends MemoryFriendshipRepository {
    private String fileName;

    public FileFriendshipRepository(Validator<Friendship> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    @Override
    public Friendship save(Friendship entity) {
        Friendship friendship = super.save(entity);
        saveData();
        return friendship;
    }

    @Override
    public Friendship delete(Long aLong) {
        Friendship friendship = super.delete(aLong);
        saveData();
        return friendship;
    }

    @Override
    public Long delete(Long id1, Long id2) {
        Long id = super.delete(id1, id2);
        saveData();
        return id;
    }

    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] words = line.split(";");
                Friendship friendship = new Friendship(Long.parseLong(words[1]), Long.parseLong(words[2]));
                friendship.setFriendsFrom(LocalDate.parse(words[3]));
                friendship.setId(Long.parseLong(words[0]));
                super.save(friendship);
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
            for (Friendship friendship : findAll()) {
                out.write(friendship.writeable());
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

}
