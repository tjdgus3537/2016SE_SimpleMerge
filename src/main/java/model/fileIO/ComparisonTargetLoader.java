package model.fileIO;

import model.fileIO.parser.DOCParser;
import model.fileIO.parser.DOCXParser;
import model.fileIO.parser.DocumentExtensionBasedContentParser;
import model.fileIO.parser.ODTParser;

import java.io.File;

/**
 * Created by Donghwan on 5/12/2016.
 *
 * 프로그램에서 비교할 파일을 읽어오는 객체
 */
public class ComparisonTargetLoader extends AbstractComparisonTargetLoader {

	@Override
	protected DocumentExtensionBasedContentParser getDocumentExtensionBasedContentParser(File file) {
		// TODO 지원되는 확장자에 따라 구분하는 코드 추가
		if(file.getName().endsWith(".doc")) { return new DOCParser(); }
		if(file.getName().endsWith(".docx")){ return new DOCXParser(); }
		if(file.getName().endsWith(".odt")){ return new ODTParser(); }
		return null;
	}

}
