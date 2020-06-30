package szabi.calendar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import szabi.model.Day;
import szabi.model.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class Calendar extends VBox {

    private ArrayList<CalendarCell> allCalendarDays = new ArrayList<>(35);
    private LocalDate currentDate;
    private Text calendarTitle;
    private Label[] daysOfWeek = new Label[] {
      new Label("Héftő"), new Label("Kedd"), new Label("Szerda"), new Label("Csütörtök"),
      new Label("Péntek"), new Label("Szombat"), new Label("Vasárnap")
    };
    private Employee employee;

    public Calendar(LocalDate date, Employee employee) {
        currentDate = date;
        this.employee = employee;

        // a hét napjainak sora
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(840);
        Integer col = 0;
        for (Label dayName : daysOfWeek) {
            StackPane sp = new StackPane();
            sp.setPrefSize(120, 10);
            sp.getChildren().add(dayName);
            dayLabels.add(sp, col++, 0);
        }

        // a naptár panelje
        GridPane calendar = new GridPane();
        calendar.setPrefSize(840, 480);
        calendar.setGridLinesVisible(true);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                CalendarCell cell = new CalendarCell();
                calendar.add(cell, j, i);
                allCalendarDays.add(cell);
            }
        }

        calendarTitle = new Text("");
        HBox titleBar = new HBox(calendarTitle);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        titleBar.setSpacing(20);
        titleBar.setPadding(new Insets(10));

        populateCalendar(date);

        this.getChildren().addAll(titleBar, dayLabels, calendar);

        for (CalendarCell cell : allCalendarDays) {
            for (Day day : employee.getDays()) {
                int cellYear = cell.getDate().getYear();
                int cellMonth = cell.getDate().getMonthValue();
                int cellDay = cell.getDate().getDayOfMonth();
                int dayYear = day.getYear();
                int dayMonth = day.getMonth();
                int dayDay = day.getDay();
                if (cellYear != dayYear || cellMonth != dayMonth || cellDay != dayDay) {
                    continue;
                } else {
                    cell.setLbStatusText(day.getStatus());
                    cell.getLbBegin().setText(day.getBeginHour());
                    cell.getLbDone().setText(day.getEndHour());
                }
            }
        }
    }

    // a naptár listájának feltöltése a dátumokkal
    private void populateCalendar(LocalDate date) {

        while (!date.getDayOfWeek().toString().equals("MONDAY")) {
            date = date.minusDays(1);
        }

        for (CalendarCell cell : allCalendarDays) {
            int txt = date.getDayOfMonth();
            cell.setLbDateText(txt);
            if (date.getDayOfWeek().toString().equals("SUNDAY")) cell.setWeekEndCells();
            if (date.getDayOfWeek().toString().equals("SATURDAY")) cell.setWeekEndCells();
            cell.setDate(date);
            date = date.plusDays(1);
        }

        DateTimeFormatter hungarian = DateTimeFormatter.ofPattern("yyyy MMMM", new Locale("hu", "HU"));
        calendarTitle.setText(date.format(hungarian));
        calendarTitle.setStyle("-fx-font-size: 20px;");
    }

    public ArrayList<CalendarCell> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<CalendarCell> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }
}
