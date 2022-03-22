module lifegame {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens ru.sibsutis.lifegame;
    opens ru.sibsutis.lifegame.forms;
    opens ru.sibsutis.lifegame.window;

    exports ru.sibsutis.lifegame;
    exports ru.sibsutis.lifegame.forms;
    exports ru.sibsutis.lifegame.window;
}