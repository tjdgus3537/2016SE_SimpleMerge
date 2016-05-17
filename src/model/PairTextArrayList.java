package model;

import java.util.ArrayList;

import javafx.scene.text.Text;

/**
 * Created by Seonghyeon on 5/17/2016.
 * 2개의 ArrayList<Text>을 묶어둔 단위
 * TextDecorator Class에서 PairBlockArrayList가 input으로 주어지고 Compare 했을 때 return되는 data의 type
 */

public class PairTextArrayList {
	private ArrayList<Text> left;
	private ArrayList<Text> right;
	
	public PairTextArrayList() {
		left = new ArrayList<Text>();
		right = new ArrayList<Text>();
	}
	
	public PairTextArrayList(ArrayList<Text> left, ArrayList<Text> right) {
		setLeft(left);
		setRight(right);
	}
	
	public void setLeft(ArrayList<Text> left) {
		this.left = left;
	}
	
	public void setRight(ArrayList<Text> right) {
		this.right = right;
	}
	
	public ArrayList<Text> getLeft() {
		return left;
	}
	
	public ArrayList<Text> getRight() {
		return right;
	}
	
	public void addLeft(Text b) {
		left.add(b);
	}
	
	public void addRight(Text b) {
		right.add(b);
	}
}
