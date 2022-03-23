module lifegame {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens ru.sibsutis.lifegame;
    opens ru.sibsutis.lifegame.forms;
    opens ru.sibsutis.lifegame.windows;

    exports ru.sibsutis.lifegame;
    exports ru.sibsutis.lifegame.forms;
    exports ru.sibsutis.lifegame.windows;
}