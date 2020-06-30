package szabi.calendar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;

public class CalendarCell extends VBox{

    private Label lbDate;
    private Label lbStatus;
    private Label lbBegin;
    private Label lbSep;
    private Label lbDone;
    private LocalDate date;

    public CalendarCell() {

        // cella beállítása
        setPrefSize(120,120);

        // első sor beállítása
        lbDate = new Label();
        lbDate.setFont(Font.font("System", FontWeight.BOLD, 25));
        HBox dateRow = new HBox();
        dateRow.setPrefHeight(50);
        dateRow.setAlignment(Pos.CENTER_RIGHT);
        dateRow.setPadding(new Insets(0, 10, 0, 0));
        dateRow.getChildren().add(lbDate);

        // második sor beállítása
        lbStatus = new Label();
        HBox statusRow = new HBox();
        statusRow.setPrefHeight(50);
        statusRow.setAlignment(Pos.CENTER);
        statusRow.getChildren().add(lbStatus);

        // harmadik sor beállítása
        lbBegin = new Label();
        lbSep = new Label("-");
        lbDone = new Label();
        HBox workingTimeRow = new HBox();
        workingTimeRow.setPrefHeight(50);
        workingTimeRow.setAlignment(Pos.CENTER);
        workingTimeRow.setSpacing(10);
        workingTimeRow.getChildren().addAll(lbBegin, lbSep, lbDone);

        // sorok hozzáadása a cellához
        this.getChildren().addAll(dateRow, statusRow, workingTimeRow);

    }

    public Label getLbDate() {
        return lbDate;
    }

    public void setLbDate(Label lbDate) {
        this.lbDate = lbDate;
    }

    public Label getLbStatus() {
        return lbStatus;
    }

    public void setLbStatus(Label lbStatus) {
        this.lbStatus = lbStatus;
    }

    public Label getLbBegin() {
        return lbBegin;
    }

    public void setLbBegin(Label lbBegin) {
        this.lbBegin = lbBegin;
    }

    public Label getLbSep() {
        return lbSep;
    }

    public void setLbSep(Label lbSep) {
        this.lbSep = lbSep;
    }

    public Label getLbDone() {
        return lbDone;
    }

    public void setLbDone(Label lbDone) {
        this.lbDone = lbDone;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLbDateText(int dayOfMonth) {
        this.lbDate.setText(Integer.toString(dayOfMonth));
    }

    // státusz beállítása az átadott szöveg alapján
    public void setLbStatusText(String status) {
        this.lbStatus.setText(status);
        switch (status) {
            case "Munkanap": lbStatus.setTextFill(Color.GREEN); break;
            case "Fizetett szabadság": lbStatus.setTextFill(Color.BLUE); break;
            case "Táppénz": lbStatus.setTextFill(Color.ORANGE); break;
            case "Fizetett ünnep": lbStatus.setTextFill(Color.RED); break;
            case "Pihenőnap": lbStatus.setTextFill(Color.DARKGRAY); break;
        }
    }

    // cellák beállítása melyek hétvégére esnek
    public void setWeekEndCells(){
        this.setStyle("-fx-background-color: lightgrey;"+
                "-fx-border-color: grey;");
        this.getLbDate().setTextFill(Color.GREY);
        this.lbStatus.setText("Pihenőnap");
        this.lbStatus.setTextFill(Color.DARKGRAY);
    }

    public void setNotCurrentMonthDay() {
        this.getLbDate().setTextFill(Color.GREY);
    }
}
