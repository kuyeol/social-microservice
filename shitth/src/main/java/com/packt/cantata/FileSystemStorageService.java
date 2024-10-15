package com.packt.cantata;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileSystemStorageService implements StorageService {


    //@Autowired
    //public FileSystemStorageService(StorageProperties properties) {
    //
    //    if(properties.getLocation().trim().length() == 0){
    //        throw new StorageException("File upload location can not be Empty.");
    //    }
    //
    //    this.rootLocation = Paths.get(properties.getLocation());
    //}

    @Override
    public void init() {

    }

    @Override
    public void store(MultipartFile file) {

    }

    @Override
    public Stream<Path> loadAll() {
        return Stream.empty();
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
