package by.nika_doroshkevich.command.impl;

import by.nika_doroshkevich.command.Command;
import by.nika_doroshkevich.service.FileBaseService;
import by.nika_doroshkevich.service.ServiceProvider;

public class DeleteFileCommand implements Command {

    FileBaseService fileBaseService = ServiceProvider.getInstance().getFileBaseService();

    @Override
    public String execute(String request) {
        String response;
        response = "file deleted!";

        try {
            String[] params = request.split("\\s+");
            fileBaseService.deleteFile(Integer.parseInt(params[2]));
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }

        return response;
    }
}
