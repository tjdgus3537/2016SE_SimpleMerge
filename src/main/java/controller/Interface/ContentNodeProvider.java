package controller.Interface;

import javafx.scene.Node;

/**
 * Created by Donghwan on 5/24/2016.
 *
 * 프로그램의 정보(파일 내용이나 비교 결과 등)를 화면에 출력하는 노드를 반환할 수 있는 클래스들의 인터페이스
 */
public interface ContentNodeProvider {
    /**
     * 화면에 프로그램의 정보를 출력하는 노드를 반환한다.
     * @return 프로그램의 정보를 출력하는 노드
     */
    Node getContentNode();
}
