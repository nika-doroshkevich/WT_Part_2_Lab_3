package by.nika_doroshkevich.presentation;

import by.nika_doroshkevich.presentation.impl.UserInterfaceImpl;
import by.nika_doroshkevich.presentation.impl.ViewImpl;

public class PresentationProvider {

    private final View VIEW = new ViewImpl();
    private final UserInterface USER_INTERFACE = new UserInterfaceImpl();
    private static PresentationProvider instance;

    private PresentationProvider() {
    }

    public static PresentationProvider getInstance() {
        if (instance == null) {
            instance = new PresentationProvider();
        }
        return instance;
    }

    public View getVIEW() {
        return VIEW;
    }

    public UserInterface getUSER_INTERFACE() {
        return USER_INTERFACE;
    }
}
