package by.nika_doroshkevich.command.impl;

import by.nika_doroshkevich.command.Command;
import by.nika_doroshkevich.service.FileBaseService;
import by.nika_doroshkevich.service.ServiceProvider;

public class AddFileCommand implements Command {

    FileBaseService fileBaseService = ServiceProvider.getInstance().getFileBaseService();

    @Override
    public String execute(String request) {
        String response;
        response = "file added!";
        //fileBaseService.addFile();
        return response;
    }
}
