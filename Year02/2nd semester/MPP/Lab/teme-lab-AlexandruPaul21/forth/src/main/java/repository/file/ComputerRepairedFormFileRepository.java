package repository.file;

import model.ComputerRepairRequest;
import model.ComputerRepairedForm;
import repository.ComputerRepairRequestRepository;
import repository.RepositoryException;
import repository.mock.ComputerRepairedFormInMemoryRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ComputerRepairedFormFileRepository extends ComputerRepairedFormInMemoryRepository {
    private String filename;
    private ComputerRepairRequestRepository requestRepository;
    private static int idGenerator=0;

    public ComputerRepairedFormFileRepository(String filename,ComputerRepairRequestRepository requestRepository) {
        this.filename = filename;
        this.requestRepository=requestRepository;
        readFromFile();
    }

    private void readFromFile(){
        try(BufferedReader br=new BufferedReader(new FileReader(filename))){
            String line=br.readLine();
            try{
                idGenerator=Integer.parseInt(line);
            }catch (NumberFormatException ex){
                System.err.println("Invalid Value for idGenerator, starting from 0");
            }
            while((line=br.readLine())!=null){
                String[] elems=line.split(";");
                if (elems.length!=6){
                    System.err.println("Invalid line ..."+line);
                    continue;
                }
                try {
                    int id = Integer.parseInt(elems[0]);
                    int requestId=Integer.parseInt(elems[1]);
                    double price=Double.parseDouble(elems[3]);
                    ComputerRepairRequest crr=requestRepository.findById(requestId);
                    ComputerRepairedForm crf=new ComputerRepairedForm(id,crr,elems[2],price,elems[4],elems[5]);
                    super.add(crf);

                }catch(NumberFormatException ex){
                    System.err.println("Invalid data "+ex);
                }catch (RepositoryException ex){
                    System.err.println("Repository Error "+ex);
                }
            }
        }catch (IOException ex){
            throw new RepositoryException("Error reading "+ex);
        }

    }

    @Override
    public ComputerRepairedForm add(ComputerRepairedForm el) {
        el.setID(getNextId());
        super.add(el);
        writeToFile();
        return el;
    }

    @Override
    public void delete(ComputerRepairedForm el) {
        super.delete(el);
        writeToFile();
    }

    @Override
    public void update(ComputerRepairedForm el, Integer integer) {
        super.update(el, integer);
        writeToFile();
    }

    private void writeToFile(){
        try(PrintWriter pw=new PrintWriter(filename)){
            pw.println(idGenerator);
            for(ComputerRepairedForm crf:findAll()){
                pw.println(crf.getID()+";"+crf.getRequest().getID()+";"+crf.getServices()+";"+crf.getPrice()+";"+crf.getDate()+";"+crf.getEmployee());
            }
        }catch(IOException ex){
            throw new RepositoryException("Error writing "+ex);
        }

    }

    private static int getNextId(){
        return idGenerator++;
    }
}
