package vn.iotstar.storage;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
	void init();

	void store(MultipartFile file, String filename);

	String getStorageFilename(MultipartFile file, String id);
}