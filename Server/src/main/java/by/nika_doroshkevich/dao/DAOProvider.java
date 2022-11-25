package by.nika_doroshkevich.dao;

import by.nika_doroshkevich.dao.impl.XMLFilesBaseDAO;
import by.nika_doroshkevich.dao.impl.XMLUsersBaseDAO;

public class DAOProvider {

    private final FilesBaseDAO filesBaseDAO = new XMLFilesBaseDAO();
    private final UsersBaseDAO usersBaseDAO = new XMLUsersBaseDAO();
    private static DAOProvider instance;

    private DAOProvider() {}

    public static DAOProvider getInstance() {
        if (instance == null) {
            instance = new DAOProvider();
        }
        return instance;
    }

    public FilesBaseDAO getFilesBaseDAO() {
        return filesBaseDAO;
    }

    public UsersBaseDAO getUsersBaseDAO() {
        return usersBaseDAO;
    }
}
