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
	                // �߸��� ������ �Է����� �� ó�� (���� ���, ��� �޽��� ��� ��)
	                // ���⼭�� ������ ��� ���̾�α׸� ���ϴ�.
	                JOptionPane.showMessageDialog(null, "��ȭ��ȣ ������ '010-1234-1234'�� ���� �Է����ּ���.", "�Է� ����", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	}
	

       
    
}
