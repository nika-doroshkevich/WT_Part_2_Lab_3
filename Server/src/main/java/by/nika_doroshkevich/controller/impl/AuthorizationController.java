package by.nika_doroshkevich.controller.impl;

import by.nika_doroshkevich.controller.Controller;

public class AuthorizationController implements Controller {

    @Override
    public String action(String request) {
        String[] params = request.split("\\s+");

        String username = params[0];
        String password = params[1];

        //Authorization

        return "authorized!";
    }
}
