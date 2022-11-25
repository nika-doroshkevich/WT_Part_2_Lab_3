package by.nika_doroshkevich.service.impl;

import by.nika_doroshkevich.dao.DAOProvider;
import by.nika_doroshkevich.dao.FilesBaseDAO;
import by.nika_doroshkevich.model.File;
import by.nika_doroshkevich.service.FileBaseService;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileBaseServiceImpl implements FileBaseService {

    public FileBaseServiceImpl() {
    }

    @Override
    public void addFile(File file) {
        FilesBaseDAO filesBaseDAO;

        filesBaseDAO = DAOProvider.getInstance().getFilesBaseDAO();

        filesBaseDAO.getFiles().add(file);
        filesBaseDAO.writeFilesToXmlFile();
    }

    @Override
    public void deleteFile(int fileId) {
        FilesBaseDAO filesBaseDAO;

        filesBaseDAO = DAOProvider.getInstance().getFilesBaseDAO();

        filesBaseDAO.getFiles().remove(fileId - 1);
        for (int i = 0; i < filesBaseDAO.getFiles().size(); i++) {
            filesBaseDAO.getFiles().get(i).setId(i + 1);
        }
        filesBaseDAO.writeFilesToXmlFile();
    }

    @Override
    public String getAllFiles() {
        FilesBaseDAO filesBaseDAO = DAOProvider.getInstance().getFilesBaseDAO();
        return filesBaseDAO.getXmlDocument(filesBaseDAO.getFiles())
                .replaceAll("\n", "")
                .replaceAll("\t", "");
    }

    @Override
    public String searchFilesByKeywords(String request) {
        String[] params = request.split("\\s+");
        ArrayList<File> result;
        FilesBaseDAO filesBaseDAO;
        Pattern pattern;
        Matcher matcher;

        filesBaseDAO = DAOProvider.getInstance().getFilesBaseDAO();
        result = new ArrayList<>();
        pattern = Pattern.compile(params[2].toLowerCase(Locale.ROOT));

        for (File file : filesBaseDAO.getFiles()) {
            StringBuilder fileFieldsForSearch;

            fileFieldsForSearch = new StringBuilder();
            fileFieldsForSearch.append(file.getStudent().getFirstName()).append(file.getStudent().getSecondName());
            matcher = pattern.matcher(fileFieldsForSearch.toString().toLowerCase(Locale.ROOT));

            if (matcher.find()) {
                result.add(file);
            }
        }

        if (result.size() != 0) {
            return filesBaseDAO.getXmlDocument(result)
                    .replaceAll("\n", "")
                    .replaceAll("\t", "");
        }

        return "\"" + params[2] + "\" not found!";
    }
}

