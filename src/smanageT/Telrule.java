package smanageT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

public class Telrule {
	
	public Telrule(JTextField phoneNumberField) {
		
		AbstractDocument doc = (AbstractDocument) phoneNumberField.getDocument();
	    doc.setDocumentFilter(new DocumentFilter() {
	        Pattern pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
	        
	        @Override
	        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
	            String currentText = phoneNumberField.getText();
	            String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
	            
	            Matcher matcher = pattern.matcher(newText);
	            if (matcher.matches()) {
	                super.replace(fb, offset, length, text, attrs);
	            } else {
	                // 잘못된 형식을 입력했을 때 처리 (예를 들어, 경고 메시지 출력 등)
	                // 여기서는 간단히 경고 다이얼로그를 띄웁니다.
	                JOptionPane.showMessageDialog(null, "전화번호 형식은 '010-1234-1234'와 같이 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	}
	

       
    
}
