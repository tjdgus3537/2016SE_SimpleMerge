import controller.mainWindow.MainWindowControllerInterface;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.MainModel;
import model.MainModelInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.StringJoiner;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collector;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

/**
 * Created by SH on 2016. 11. 24..
 */
public class GUICompareTest extends ApplicationTest {

    @Override
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainModelInterface mainModel = new MainModel();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        Parent root = fxmlLoader.load();
        MainWindowControllerInterface controller = fxmlLoader.getController();
        controller.setModel(mainModel);

        Scene scene = new Scene(root);
        stage.setTitle("SimpleMerge - Team 6");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
        FxToolkit.cleanupStages();
    }

    @Before
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
    }

    @Test
    public void compareButtonEnabledTest() {

        SplitPane splitPane = find("#splitPane");
        ObservableList<Node> splitedItems = splitPane.getItems();
        String expectedContent = "hello\n" + "world\n" + "and\n" + "java";

        // ComparisonTargetLoader가 해당 파일의 내용을 잘 읽어오는지 확인
        File source = new File(getClass().getResource("readTestFile.txt").getPath());

        // 왼쪽로드
        clickOn(splitedItems.get(0).lookup("#load-button"));
        typeKeyCode(source.toString());
        type(KeyCode.ENTER);

        // 오른쪽로드
        clickOn(splitedItems.get(1).lookup("#load-button"));
        typeKeyCode(source.toString());
        type(KeyCode.ENTER);

        // enabled 되었는지 확인
        verifyThat((Node)find("#compare-button"), NodeMatchers.isEnabled());
    }

    @Test
    public void copyButtonTest() {

        SplitPane splitPane = find("#splitPane");
        ObservableList<Node> splitedItems = splitPane.getItems();

        File left = new File(getClass().getResource("1.txt").getPath());
        File right = new File(getClass().getResource("2.txt").getPath());

        // 왼쪽로드
        clickOn(splitedItems.get(0).lookup("#load-button"));
        typeKeyCode(left.toString());
        type(KeyCode.ENTER);

        // 오른쪽로드
        clickOn(splitedItems.get(1).lookup("#load-button"));
        typeKeyCode(right.toString());
        type(KeyCode.ENTER);

        // compare click
        clickOn( (Node)find("#compare-button") );

        // enabled 되었는지 확인
        verifyThat((Node)find("#copy-to-right-button"), NodeMatchers.isEnabled());
        verifyThat((Node)find("#copy-to-left-button"), NodeMatchers.isEnabled());
    }

    @Test
    public void compareEnabledAfterEditTest() {

        this.copyButtonTest();

        // edit 버튼을 누른다.
        clickOn ((Node)find("#edit-button"));

        // enabled 되었는지 확인
        verifyThat((Node)find("#compare-button"), NodeMatchers.isEnabled());
    }

    @Test
    public void compareDisabledAfterAnotherFileLoadTest() {

        SplitPane splitPane = find("#splitPane");
        ObservableList<Node> splitedItems = splitPane.getItems();

        File left = new File(getClass().getResource("1.txt").getPath());
        File right = new File(getClass().getResource("2.txt").getPath());
        File newFile = new File(getClass().getResource("1.txt").getPath());

        // 왼쪽로드
        clickOn(splitedItems.get(0).lookup("#load-button"));
        typeKeyCode(left.toString());
        type(KeyCode.ENTER);

        // 오른쪽로드
        clickOn(splitedItems.get(1).lookup("#load-button"));
        typeKeyCode(right.toString());
        type(KeyCode.ENTER);

        // 컴페어
        clickOn((Node)find("#compare-button"));

        // 왼쪽로드
        clickOn((Node)find("#load-button"));
        type(KeyCode.ENTER);
        typeKeyCode(newFile.toString());
        type(KeyCode.ENTER);

        // 컴페어모드 풀렸는지 확인
        TextArea textarea = find("#textarea");
        clickOn(textarea);

        type(KeyCode.T, KeyCode.E, KeyCode.S, KeyCode.T, KeyCode.ENTER);

        assertEquals(textarea.getText(), textarea.getText());
    }


    @After
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        FxToolkit.cleanupStages();

        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }

    private void typeKeyCode(String s) {

        char[] string = s.toCharArray();

        for( char c : string ){
            switch (c){
                case ':'  : push(KeyCode.SHIFT, KeyCode.SEMICOLON); break;
                case '\\' : push(KeyCode.BACK_SLASH); break;
                case '.' : push(KeyCode.PERIOD); break;
                case '/' : push(KeyCode.SLASH); break;
                default :
                    if( Character.isUpperCase(c) ){
                        push(KeyCode.SHIFT, KeyCode.getKeyCode(Character.toString(c)));
                    } else {
                        push(KeyCode.getKeyCode(Character.toString( Character.toUpperCase(c) )));
                    }
            }
        }

    }

    private <T extends Node> T find(final String query) {
        return lookup(query).query();
    }

}
