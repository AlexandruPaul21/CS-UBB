package repository.file;

import model.ComputerRepairRequest;
import model.RequestStatus;
import repository.RepositoryException;
import repository.mock.ComputerRepairRequestInMemoryRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ComputerRepairRequestFileRepository extends ComputerRepairRequestInMemoryRepository {
    private String filename;
    private static int idGenerator=0;
    public ComputerRepairRequestFileRepository(String filename) {
        this.filename = filename;
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
                if (elems.length!=8){
                    System.err.println("Invalid line ..."+line);
                    continue;
                }
                try{
                    int id=Integer.parseInt(elems[0]);
                    RequestStatus status=RequestStatus.valueOf(RequestStatus.class,elems[7]);
                    ComputerRepairRequest crr=new ComputerRepairRequest(id,elems[1],elems[2],elems[3],elems[4],elems[5],elems[6]);
                    crr.setStatus(status);
                    super.add(crr);
                }catch(NumberFormatException ex){
                    System.err.println("Error converting "+elems[0]);
                }catch (IllegalArgumentException ex){
                    System.err.println("Error converting "+elems[7]);
                }
            }

        }catch(IOException ex){
            throw new RepositoryException("Error reading "+ex);
        }

    }

    private void writeToFile(){
        try(PrintWriter pw=new PrintWriter(filename)){
            pw.println(idGenerator);
            for(ComputerRepairRequest crr:findAll()){
                pw.println(crr.getID()+";"+crr.getOwnerName()+";"+crr.getOwnerAddress()+";"+crr.getPhoneNumber()+";"+crr.getModel()+";"+crr.getDate()+";"+crr.getProblemDescription()+";"+crr.getStatus());
            }
        }catch(IOException ex){
            throw new RepositoryException("Error writing "+ex);
        }

    }


    @Override
    public ComputerRepairRequest add(ComputerRepairRequest el) {
        el.setID(getNextId());
        super.add(el);
        writeToFile();
        return el;
    }

    @Override
    public void delete(ComputerRepairRequest el) {
        super.delete(el);
        writeToFile();
    }

    @Override
    public void update(ComputerRepairRequest el, Integer integer) {
        super.update(el, integer);
        writeToFile();
    }

    private static int getNextId(){
        return idGenerator++;
    }
}
