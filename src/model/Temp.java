package model;

import java.util.ArrayList;

import javafx.scene.text.Text;

/**
 * Created by Seonghyeon on 5/17/2016.
 * Diff Class의 결과인 Block으로 이루어진 data들을 View에서 바로 사용할 수 있도록
 * Text Class의 형태로 변환해주는 Class.
 * 상위 Class에게 하위 2개의 Class들의 기능을 적절히 활용하여 필요한 정보를 제공해준다.
 * String을 input으로 넣으면 적절한 Text로의 변환이 가능한 interface가 구현되어 있는 instance를
 * 상위 Class에서 호출할 때 생성자에서 parameter로 입력 받아 member variable로 가지고 있는다.
 */

//@TODO::추후 구현하기
public class Temp {
	//Diff diff;
	//TextDecorator textDecorator;
	
	/**
	 * 생성자.
	 * @param textDecorator에게 줄 관련된 interface
     */	
//	public Temp(//Interface inter~) {
//		//diff = new Diff();
//		//textDecorator = new TextDecorator(inter~);
//	}
	
	/**
	 * 두 개의 string을 input으로 받아서 비교 결과를 PairTextArrayList 형태로 return.
	 * @param left 비교할 문자열
	 * @param right 비교할 문자열
     * @return 비교 결과인 한 쌍의 ArrayList<Text>
     */
	//@TODO::내부 구현하기
	public PairTextArrayList compare(String left, String right) {
		return null;
	}
	
	/**
	 * 특정 줄에 해당하는 우측의 Block을 좌측으로 copy
	 * @param lineNum copy할 부분의 줄 번호
     * @return copy를 적용하고 난 후의 좌측의 ArrayList<Text>
     */	
	//@TODO::내부 구현하기
	public ArrayList<Text> copyToLeft(int lineNum) {
		return null;
	}
	
	/**
	 * 특정 줄에 해당하는 좌측의 Block을 우측으로 copy
	 * @param lineNum copy할 부분의 줄 번호
     * @return copy를 적용하고 난 후의 우측의 ArrayList<Text>
     */	
	//@TODO::내부 구현하기
	public ArrayList<Text> copyToRight(int lineNum) {
		return null;
	}
}
