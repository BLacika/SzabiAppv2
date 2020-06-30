package szabi.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import szabi.Main;
import szabi.calendar.Calendar;
import szabi.calendar.CalendarCell;
import szabi.model.Day;
import szabi.model.Employee;

import java.io.IOException;
import java.time.LocalDate;

public class WorkingTimeOverview {

    private Main main;
    private Calendar calendar;
    @FXML
    private ChoiceBox<Integer> chYear;
    @FXML
    private TableColumn<Employee, String> empCol;
    @FXML
    private VBox calendarBox;
    @FXML
    private Text txSick;
    @FXML
    private HBox choiceBox;
    @FXML
    private ChoiceBox<Integer> chMonth;
    @FXML
    private Text txHolidays;
    @FXML
    private Button btSave;
    @FXML
    private TableView<Employee> empTable;
    @FXML
    private Text txAllWorkedDays;

    @FXML
    private void initialize() {
        empCol.setCellValueFactory(data -> data.getValue().nameProperty());

        showEmployeeWorkTime(null, null);

        chYear.getItems().addAll(2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025);
        chYear.setValue(LocalDate.now().getYear());
        chMonth.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        chMonth.setValue(LocalDate.now().getMonthValue());

        empTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showEmployeeWorkTime(newValue, LocalDate.of(chYear.getValue(), chMonth.getValue(), 1));
        });

        chYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showEmployeeWorkTime(empTable.getSelectionModel().getSelectedItem(),
                    LocalDate.of(newValue, chMonth.getValue(), 1));
        });
        chMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showEmployeeWorkTime(empTable.getSelectionModel().getSelectedItem(),
                    LocalDate.of(chYear.getValue(), newValue, 1));
        });

        btSave.setOnMouseClicked(e -> {
            Employee employee = empTable.getSelectionModel().getSelectedItem();
            ObservableList<Day> days = employee.getDays();
            days.clear();
            for (CalendarCell cell : calendar.getAllCalendarDays()) {
                days.add(new Day(cell.getDate().getYear(), cell.getDate().getMonthValue(),
                        cell.getDate().getDayOfMonth(), cell.getLbBegin().getText(), cell.getLbDone().getText(),
                        cell.getLbStatus().getText()));
            }
        });
    }

    @FXML
    private void showEmployeeWorkTime(Employee employee, LocalDate localDate) {
        if (employee != null) {
            calendarBox.getChildren().clear();
            calendar = new Calendar(LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()), employee);
            calendarBox.getChildren().addAll(choiceBox, calendar);

            double allWorkedHours = 0;
            int allWorkedDays = 0;
            int allSickDays = 0;
            int allHolidays = 0;

            for (CalendarCell cell : calendar.getAllCalendarDays()) {

                cell.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("SetDayDialog.fxml"));
                        try {
                            AnchorPane ap = (AnchorPane) loader.load();
                            SetDayDialog controller = loader.getController();
                            controller.setDialogStage(stage);
                            controller.setCell(cell);
                            Scene scene = new Scene(ap);
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                });

                if (cell.getLbStatus().getText().equals("Munkanap") && cell.getDate().getYear() == chYear.getValue() &&
                        cell.getDate().getMonthValue() == chMonth.getValue()) {
                    allWorkedDays += 1;
                }

                if (cell.getLbStatus().getText().equals("Fizetett szabadság") && cell.getDate().getYear() == chYear.getValue() &&
                        cell.getDate().getMonthValue() == chMonth.getValue()) {
                    allHolidays += 1;
                }

                if (cell.getLbStatus().getText().equals("Táppénz") && cell.getDate().getYear() == chYear.getValue() &&
                        cell.getDate().getMonthValue() == chMonth.getValue()) {
                    allSickDays += 1;
                }
            }

            txHolidays.setText(allHolidays + " nap");
            txSick.setText(allSickDays + " nap");
            txAllWorkedDays.setText(allWorkedDays + " nap");

        }
    }

    public void setMain(Main main) {
        this.main = main;

        empTable.setItems(main.getEmployees().sorted());
    }
}
