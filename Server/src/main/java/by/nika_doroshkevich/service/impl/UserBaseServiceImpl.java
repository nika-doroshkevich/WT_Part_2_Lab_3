package by.nika_doroshkevich.service.impl;

import by.nika_doroshkevich.dao.DAOProvider;
import by.nika_doroshkevich.dao.UsersBaseDAO;
import by.nika_doroshkevich.model.User;
import by.nika_doroshkevich.service.UserBaseService;

public class UserBaseServiceImpl implements UserBaseService {

    public UserBaseServiceImpl() {
    }

    @Override
    public void addUser(User user) {
        UsersBaseDAO usersBaseDAO;

        usersBaseDAO = DAOProvider.getInstance().getUsersBaseDAO();
        usersBaseDAO.getUsers().put(user.getUsername(), user);
        usersBaseDAO.writeUsersToXml();
    }

}
