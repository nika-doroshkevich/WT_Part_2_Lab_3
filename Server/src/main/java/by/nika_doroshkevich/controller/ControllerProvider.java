package by.nika_doroshkevich.controller;

import by.nika_doroshkevich.controller.impl.AuthorizationController;
import by.nika_doroshkevich.controller.impl.ServiceController;

public class ControllerProvider {
    private static ControllerProvider instance;
    private final Controller authorizationController = new AuthorizationController();
    private final Controller serviceController = new ServiceController();

    private ControllerProvider() {}

    public static ControllerProvider getInstance() {
        if (instance == null) {
            instance = new ControllerProvider();
        }
        return instance;
    }

    public Controller getAuthorizationController() {
        return authorizationController;
    }

    public Controller getServiceController() {
        return serviceController;
    }
}
