package vn.iotstar.storage;

import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileSystemStorageService implements IStorageService {

  private final Path root;

  public FileSystemStorageService(@Value("${storage.location}") String location){
    this.root = Paths.get(location);
  }

  @PostConstruct
  public void ensureInit(){ init(); }

  @Override
  public void init(){
    try{
      if(!Files.exists(root)) Files.createDirectories(root);
    }catch(IOException e){ throw new StorageException("Init storage fail", e); }
  }

  @Override
  public void store(MultipartFile file, String filename){
    try{
      if(file.isEmpty()) throw new StorageException("Empty file");
      Path dest = root.resolve(filename).normalize().toAbsolutePath();
      if(!dest.getParent().equals(root.toAbsolutePath()))
        throw new StorageException("Invalid destination");
      Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
    }catch(IOException e){ throw new StorageException("Store failed", e); }
  }

  @Override
  public String getStorageFilename(MultipartFile f, String id){
    String ext = FilenameUtils.getExtension(f.getOriginalFilename());
    return "f" + id + (ext==null||ext.isEmpty() ? "" : "."+ext.toLowerCase());
    }
}
