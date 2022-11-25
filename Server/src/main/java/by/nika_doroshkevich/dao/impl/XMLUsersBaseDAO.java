package by.nika_doroshkevich.dao.impl;

import by.nika_doroshkevich.dao.DAOProvider;
import by.nika_doroshkevich.dao.UsersBaseDAO;
import by.nika_doroshkevich.model.User;
import by.nika_doroshkevich.model.UserRole;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLUsersBaseDAO implements UsersBaseDAO {

    private static final String USERS_BASE_PATH = "users.xml";
    private static final SecretKeySpec KEY = new SecretKeySpec("Hdy2rl1ds64MePhn".getBytes(), "AES");
    private final HashMap<String, User> users;

    public XMLUsersBaseDAO() {
        this.users = readUsersFromXml();
    }

    @Override
    public HashMap<String, User> parseXmlToTheListOfUsers(String xmlDocument) {
        HashMap<String, User> users;
        ArrayList<String> xmlElements;
        User user;
        Pattern elementPattern;
        Pattern userNamePattern;
        Pattern passwordPattern;
        Pattern userRolePattern;
        Matcher matcher;

        users = new HashMap<>();
        xmlElements = new ArrayList<>();
        elementPattern = Pattern.compile("<user>(.*?)</user>");
        userNamePattern = Pattern.compile("<username>(.*)</username>");
        passwordPattern = Pattern.compile("<password>(.*)</password>");
        userRolePattern = Pattern.compile("<user-role>(.*)</user-role>");

        matcher = elementPattern.matcher(xmlDocument
                .replaceAll("\n", "")
                .replaceAll("\t", ""));
        while (matcher.find()) {
            xmlElements.add(matcher.group());
        }

        for (String element : xmlElements) {
            user = new User();
            matcher = userNamePattern.matcher(element);
            while (matcher.find()) {
                user.setUsername(matcher.group(1));
            }

            matcher = passwordPattern.matcher(element);
            while (matcher.find()) {
                user.setPassword(decryptUserPassword(matcher.group(1)));
            }

            matcher = userRolePattern.matcher(element);
            while (matcher.find()) {
                if (matcher.group(1).equals("Administrator")) {
                    user.setRole(UserRole.ADMINISTRATOR);
                } else {
                    user.setRole(UserRole.USER);
                }
            }
            users.put(user.getUsername(), user);
        }
        return users;
    }

    @Override
    public HashMap<String, User> readUsersFromXml() {
        HashMap<String, User> users;
        List<String> lines;
        StringBuilder xmlDocument;

        users = new HashMap<>();

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(USERS_BASE_PATH);
            java.io.File file = new java.io.File(resource.toURI());
//            lines = Files.readAllLines(Paths.get(USERS_BASE_PATH), StandardCharsets.UTF_8);
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            xmlDocument = new StringBuilder();
            for (String line : lines) {
                xmlDocument.append(line);
            }
            users = parseXmlToTheListOfUsers(xmlDocument.toString());
            return users;
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void writeUsersToXml() {
        try(FileWriter writer = new FileWriter(USERS_BASE_PATH, false)) {
            writer.write(getXmlDocument(this.users));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public String getXmlElementCustom(User user) {
        StringBuilder element;

        element = new StringBuilder();
        element.append("\t<user>\n");
        element.append("\t\t<username>").append(user.getUsername()).append("</username>\n");
        element.append("\t\t<password>").append(Arrays.toString(encryptUserPassword(user.getPassword()))).append("</password>\n");
        element.append("\t\t<user-role>").append(user.getRole().toString()).append("</user-role>\n");
        element.append("\t</user>\n");

        return element.toString();
    }

    @Override
    public String getXmlDocument(HashMap<String, User> users) {
        StringBuilder document;
        UsersBaseDAO usersBaseDAO;

        document = new StringBuilder();
        usersBaseDAO = DAOProvider.getInstance().getUsersBaseDAO();

        document.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
        document.append("<user>\n");

        for (User user : usersBaseDAO.getUsers().values()) {
            document.append(getXmlElementCustom(user));
        }

        document.append("</user>");

        return document.toString();
    }

    public byte[] encryptUserPassword(String password) {
        byte[] passwordsBytes;

        passwordsBytes = new byte[0];

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, KEY);
            passwordsBytes = cipher.doFinal(password.getBytes());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalStateException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return passwordsBytes;
    }

    public String decryptUserPassword(String bytesArrayInString) {
        StringBuilder password;

        password = new StringBuilder();

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, KEY);
            byte[] chars = cipher.doFinal(getBytesArrayFromString(bytesArrayInString));

            for (byte b : chars) {
                password.append((char) b);
            }

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException e) {
            e.printStackTrace();
        }
        return password.toString();
    }

    public byte[] getBytesArrayFromString(String password) {
        String[] passwordParsedToStringsArray;
        byte[] passwordParsedToBytesArray;

        passwordParsedToStringsArray = password.substring(1, password.length() - 1).split(", ");
        passwordParsedToBytesArray = new byte[passwordParsedToStringsArray.length];

        for (int i = 0; i < passwordParsedToStringsArray.length; i++) {
            passwordParsedToBytesArray[i] = Byte.parseByte(passwordParsedToStringsArray[i]);
        }

        return passwordParsedToBytesArray;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }
}