package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileService {
    public static <T> List <T> readFile(String filePath) {
        List<T> objects = new ArrayList<>();
        try (ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)))) {
            Object obj;
            while ((obj = file.readObject()) != null){
                objects.add((T)obj);
            }
        } catch (EOFException e){
            return objects;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objects;
    }

    public static <T> void writeFile(String filePath, List<T> objects) {
        try(ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)))){
            for (T object : objects) {
                file.writeObject(object);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
