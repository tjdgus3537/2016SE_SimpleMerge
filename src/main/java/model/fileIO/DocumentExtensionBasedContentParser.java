package model.fileIO;

/**
 * Created by Donghwan on 2016-11-24.
 *
 * 문서의 파일 확장자에 기반해서 파일에서 본문 부분을 파싱하는 객체의 인터페이스
 */
public interface DocumentExtensionBasedContentParser {
    /**
     * 문서의 파일 확장자에 기반해서 파일에서 본문 부분을 파싱한다.
     * @param rawContent 파싱할 원본 파일의 내용
     * @return 원본 파일에서 파싱한 본문 내용
     */
    String parseContent(String rawContent);
}
