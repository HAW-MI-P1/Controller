package de.haw.app;

import de.haw.controller.Controller;
import de.haw.controller.ControllerImpl;
import de.haw.db.DB;
import de.haw.db.MockUpDBImpl;
import de.haw.filter.Filter;
import de.haw.filter.FilterImpl;
import de.haw.gui.GUI;
import de.haw.gui.GUIImpl;
import de.haw.parser.Parser;
import de.haw.parser.ParserImpl;
import de.haw.wrapper.Wrapper;
import de.haw.wrapper.WrapperImpl;

/**
 * Created by Fenja on 02.12.2014.
 */
public class App {

    public static void main (String args[]){
        Wrapper wrapper = new WrapperImpl();
        Filter filter = new FilterImpl(wrapper);
        DB dbcontrol = new MockUpDBImpl();
        Parser parser = new ParserImpl();
        Controller controller = new ControllerImpl(parser, filter, dbcontrol);
        GUI gui = new GUIImpl();
        gui.setController(controller);
        gui.run();
    }
}
