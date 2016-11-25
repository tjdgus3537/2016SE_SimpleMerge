package model.fileIO;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.detect.NameDetector;
import org.apache.tika.mime.MimeTypes;

import java.io.File;

/**
 * Created by Donghwan on 5/12/2016.
 *
 * 프로그램에서 비교할 파일을 읽어오는 객체
 */
public class ComparisonTargetLoader extends AbstractComparisionTargetLoader{
	@Override
	protected DocumentExtensionBasedContentParser getDocumentExtensionBasedContentParser(File file) {
		// TODO 지원되는 확장자에 따라 구분하는 코드 추가
		if(file.getName().endsWith(".doc")) { return new DOCParser(); }
		return null;
	}
}
