package by.nika_doroshkevich.service;

import by.nika_doroshkevich.model.File;

public interface FileBaseService {

    void addFile(File file);

    void deleteFile(int fileId);

    String getAllFiles();

    String searchFilesByKeywords(String request);
}
