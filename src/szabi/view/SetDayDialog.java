package szabi.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import szabi.calendar.CalendarCell;

public class SetDayDialog {

    private Stage dialogStage;
    private CalendarCell cell;
    @FXML
    private ChoiceBox<String> chStatus;
    @FXML
    private Text txDate;
    @FXML
    private TextField txBegin;
    @FXML
    private TextField txDone;
    @FXML
    private Button btOK;
    @FXML
    private Button btCancel;

    @FXML
    private void initialize() {
        chStatus.getItems().addAll("Munkanap", "Fizetett szabadság", "Táppénz",
                "Fizetett ünnep", "Pihenőnap");
        chStatus.setValue("Munkanap");
    }

    @FXML
    private void handleOK() {
        cell.getLbBegin().setText(txBegin.getText());
        cell.getLbDone().setText(txDone.getText());
        cell.setLbStatusText(chStatus.getValue());
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        this.dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCell(CalendarCell cell) {
        this.cell = cell;

        txDate.setText(cell.getDate().getYear() + "." + cell.getDate().getMonthValue() + "."
                + cell.getDate().getDayOfMonth());
        txBegin.setText(cell.getLbBegin().getText());
        txDone.setText(cell.getLbDone().getText());
    }
}
