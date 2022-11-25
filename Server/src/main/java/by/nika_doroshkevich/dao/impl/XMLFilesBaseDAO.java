package by.nika_doroshkevich.dao.impl;

import by.nika_doroshkevich.dao.FilesBaseDAO;
import by.nika_doroshkevich.model.File;
import by.nika_doroshkevich.model.Student;
import by.nika_doroshkevich.model.Subject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class XMLFilesBaseDAO implements FilesBaseDAO {

    private final String FILES_BASE_PATH = "files.xml";
    private final ArrayList<File> files;

    public XMLFilesBaseDAO() {
        this.files = readFilesFromXml();
    }

    @Override
    public ArrayList<File> readFilesFromXml() {
        ArrayList<File> files = new ArrayList<>();
        List<String> lines;
        StringBuilder xmlDocument;

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(FILES_BASE_PATH);
            java.io.File file = new java.io.File(resource.toURI());
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            xmlDocument = new StringBuilder();
            for (String line : lines) {
                xmlDocument.append(line);
            }
            files = parseXmlToTheListOfFiles(xmlDocument.toString());

            return files;
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return files;
    }

    @Override
    public ArrayList<File> parseXmlToTheListOfFiles(String xmlDocument) {
        ArrayList<File> files = new ArrayList<>();
        ArrayList<String> xmlElements = new ArrayList<>();

        Pattern elementsPattern = Pattern.compile("<file>(.*?)</file>");
        Pattern idPattern = Pattern.compile("<id>(.*)</id>");
        Pattern firstNamePattern = Pattern.compile("<first-name>(.*)</first-name>");
        Pattern secondNamePattern = Pattern.compile("<second-name>(.*)</second-name>");
        Pattern mathPattern = Pattern.compile("<math>(.*)</math>");
        Pattern englishPattern = Pattern.compile("<english>(.*)</english>");
        Pattern geographyPattern = Pattern.compile("<geography>(.*)</geography>");
        Pattern physicsPattern = Pattern.compile("<physics>(.*)</physics>");
        Pattern literaturePattern = Pattern.compile("<literature>(.*)</literature>");

        Matcher matcher = elementsPattern.matcher(xmlDocument.replaceAll("\t", "").replaceAll("\n", ""));
        while (matcher.find()) {
            xmlElements.add(matcher.group());
        }

        for (String element : xmlElements) {
            File file = new File();
            Student student = new Student();
            matcher = idPattern.matcher(element);
            while (matcher.find()) {
                file.setId(Integer.parseInt(matcher.group(1)));
            }

            matcher = firstNamePattern.matcher(element);
            while (matcher.find()) {
                student.setFirstName(matcher.group(1));
            }

            matcher = secondNamePattern.matcher(element);
            while (matcher.find()) {
                student.setSecondName(matcher.group(1));
            }

            file.setStudent(student);

            matcher = mathPattern.matcher(element);
            while (matcher.find()) {
                file.getProgress().put(Subject.MATH, Integer.parseInt(matcher.group(1)));
            }

            matcher = englishPattern.matcher(element);
            while (matcher.find()) {
                file.getProgress().put(Subject.ENGLISH, Integer.parseInt(matcher.group(1)));
            }

            matcher = geographyPattern.matcher(element);
            while (matcher.find()) {
                file.getProgress().put(Subject.GEOGRAPHY, Integer.parseInt(matcher.group(1)));
            }

            matcher = physicsPattern.matcher(element);
            while (matcher.find()) {
                file.getProgress().put(Subject.PHYSICS, Integer.parseInt(matcher.group(1)));
            }

            matcher = literaturePattern.matcher(element);
            while (matcher.find()) {
                file.getProgress().put(Subject.LITERATURE, Integer.parseInt(matcher.group(1)));
            }

            files.add(file);
        }

        return files;
    }

    @Override
    public StringBuilder getXmlElement(File file) {
        StringBuilder element = new StringBuilder();
        element.append("\t<file>\n");
        element.append("\t\t<id>").append(file.getId()).append("</id>\n");
        element.append("\t\t<student>\n");
        element.append("\t\t\t<first-name>").append(file.getStudent().getFirstName()).append("</first-name>\n");
        element.append("\t\t\t<second-name>").append(file.getStudent().getSecondName()).append("</second-name>\n");
        element.append("\t\t</student>\n");
        if (!file.getProgress().isEmpty()) {
            element.append("\t\t<progress>\n");
            if (file.getProgress().containsKey(Subject.MATH)) {
                element.append("\t\t\t<math>").append(file.getProgress().get(Subject.MATH)).append("</math>\n");
            }
            if (file.getProgress().containsKey(Subject.ENGLISH)) {
                element.append("\t\t\t<english>").append(file.getProgress().get(Subject.ENGLISH)).append("</english>\n");
            }
            if (file.getProgress().containsKey(Subject.GEOGRAPHY)) {
                element.append("\t\t\t<geography>").append(file.getProgress().get(Subject.GEOGRAPHY)).append("</geography>\n");
            }
            if (file.getProgress().containsKey(Subject.PHYSICS)) {
                element.append("\t\t\t<physics>").append(file.getProgress().get(Subject.PHYSICS)).append("</physics>\n");
            }
            if (file.getProgress().containsKey(Subject.LITERATURE)) {
                element.append("\t\t\t<literature>").append(file.getProgress().get(Subject.LITERATURE)).append("</literature>\n");
            }
            element.append("\t\t</progress>\n");
        }
        element.append("\t</file>\n");

        return element;
    }

    @Override
    public String getXmlDocument(ArrayList<File> files) {
        StringBuilder document;

        document = new StringBuilder();

        document.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
        document.append("<files>\n");
        for (File file : files) {
            document.append(getXmlElement(file));
        }
        document.append("</files>");
        return document.toString();
    }

    @Override
    public void writeFilesToXmlFile() {
        try (FileWriter writer = new FileWriter(FILES_BASE_PATH, false)) {
            writer.write(getXmlDocument(this.files));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<File> getFiles() {
        return files;
    }
}
