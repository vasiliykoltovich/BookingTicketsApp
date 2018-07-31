package beans.services;

import beans.models.soap.Event;
import beans.models.soap.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadService {

    public List<Event> uploadEventFile(MultipartFile multipartFile) throws IOException {

        File file = convertMultiPartToFile(multipartFile);

        List<Event> mandatoryMissedList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        //        Event data = objectMapper.readValue(file, new TypeReference<Event>() {});
        List<Event> data = objectMapper.readValue(file, new TypeReference<List<Event>>() {
        });
        mandatoryMissedList.addAll(data);
        return mandatoryMissedList;
    }

    public List<User> uploadUserFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        List<User> mandatoryMissedList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        //        User data = objectMapper.readValue(file, new TypeReference<User>() {
        List<User> data = objectMapper.readValue(file, new TypeReference<List<User>>() {
        });
        mandatoryMissedList.addAll(data);
        return mandatoryMissedList;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
