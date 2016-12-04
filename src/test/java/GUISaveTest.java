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
public class GUISaveTest extends ApplicationTest {

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
    public void emptyFileLoadTest() {

        TextArea textarea = find("#textarea");

        // ComparisonTargetLoader가 해당 파일의 내용을 잘 읽어오는지 확인
        File source = new File(getClass().getResource("empty.txt").getPath());

        // 왼쪽로드
        clickOn((Node)find("#load-button"));
        typeKeyCode(source.toString());
        type(KeyCode.ENTER);

        assertEquals(textarea.getText(), "");

    }

    @Test
    public void fileLoadTest() throws Exception {

        Node enabledButton = find("#load-button");
        String expectedContent = "hello\n" + "world\n" + "and\n" + "java";

        // ComparisonTargetLoader가 해당 파일의 내용을 잘 읽어오는지 확인
        File source = new File(getClass().getResource("readTestFile.txt").getPath());

        // 버튼 누르고 파일 찾는다.
        clickOn(enabledButton);
        typeKeyCode(source.toString());
        type(KeyCode.ENTER);

        String actual;
        // logic of ComparisonTargetLoader.readFile();
        try (BufferedReader reader = Files.newBufferedReader(source.toPath(), StandardCharsets.UTF_8)) {
            actual = reader.lines().collect(Collector.of(
                    ()->new StringJoiner("\n"),
                    StringJoiner::add,
                    StringJoiner::merge,
                    StringJoiner::toString));
        }

        assertEquals(expectedContent, actual);
        verifyThat(enabledButton, NodeMatchers.isEnabled());
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
