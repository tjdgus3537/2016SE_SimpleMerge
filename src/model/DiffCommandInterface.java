package model;

/**
 * Created by Seonghyeon on 5/22/2016.
 * Diff를 수행하는 곳과 Diff 호출하는 곳을 분리해주는 Interface
 * Controller에게 호출 당하면 Model에서 data를 가져와서 기능을 수행하고 결과를 Model에 반영한다.
 */

public interface DiffCommandInterface {
	/**
	 * Model에서 String을 얻어서 Diff의 compare를 실행하고 그 결과를 Model에 반영한다.
     */
	public void compare();
	/**
	 * Model에서 String을 얻어서 Diff의 copyToLeft를 실행하고 그 결과를 Model에 반영한다.
	 * @param blockNum copy하려는 block의 번호
     */	
	public void copyToLeft(int blockNum);
	/**
	 * Model에서 String을 얻어서 Diff의 copyToRight를 실행하고 그 결과를 Model에 반영한다.
	 * @param blockNum copy하려는 block의 번호
     */	
	public void copyToRight(int blockNum);
}
