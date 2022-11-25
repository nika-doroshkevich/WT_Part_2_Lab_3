package by.nika_doroshkevich.service;

import by.nika_doroshkevich.service.impl.FileBaseServiceImpl;
import by.nika_doroshkevich.service.impl.UserBaseServiceImpl;

public class ServiceProvider {

    private final FileBaseService fileBaseService = new FileBaseServiceImpl();
    private final UserBaseService userBaseService = new UserBaseServiceImpl();
    private static ServiceProvider instance;

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        if (instance == null) {
            instance = new ServiceProvider();
        }
        return instance;
    }

    public FileBaseService getFileBaseService() {
        return fileBaseService;
    }

    public UserBaseService getUserBaseService() {
        return userBaseService;
    }
}
