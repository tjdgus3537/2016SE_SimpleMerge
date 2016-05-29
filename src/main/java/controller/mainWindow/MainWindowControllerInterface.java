package controller.mainWindow;

import controller.CompModeDisableReceiver;
import model.MainModelInterface;

/**
 * Created by Donghwan on 5/26/2016.
 *
 * 메인 창의 컨트롤러 인터페이스
 */
public interface MainWindowControllerInterface extends CompModeDisableReceiver {
    /**
     * 해당 컨트롤러가 참조할 모델을 설정한다.
     * @param model 컨트롤러가 참조할 모델
     */
    void setModel(MainModelInterface model);
}
