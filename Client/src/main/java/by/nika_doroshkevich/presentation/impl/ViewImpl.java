package by.nika_doroshkevich.presentation.impl;

import by.nika_doroshkevich.model.File;
import by.nika_doroshkevich.presentation.View;

public class ViewImpl implements View {

    @Override
    public void print(File file) {
        System.out.println(file);
    }

    @Override
    public void print(String text) {
        System.out.println(text);
    }
}
