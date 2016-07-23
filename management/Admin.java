package management;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import java.util.List;
import java.util.ArrayList;

public class Admin {
	final int width = 900;
	final int height = 800;
	
	private JFrame mainFrame;
	private JPanel mainTable;
	private List<JButton> person;
	
	public Admin(String title) {
		// Run
		readCSVToArrayList("C:/Users/TEMP/Desktop/test.csv");
	}
	
	public ArrayList<List<String>> readCSVToArrayList (String csvpath) {
		ArrayList<List<String>> dataTable = new ArrayList<List<String>>();
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(csvpath));
//          reader.readLine();// �O�_Ū���Ĥ@�� (�[�W���ѥN��|Ū��,���Ѯ������|Ū��)
			String line = null;// �Ȧs��(���լO�_�wŪ����)
			
			 // read data
            while ((line = reader.readLine()) != null) {
                
                //�s��C�@�C��Ƥ��e(�)
                ArrayList<String> ticketStr = new ArrayList<String>();
                
                String item[] = line.split(",");//csv��󬰨̾ڳr������
                
                //�M���W�@���s�J�����
                ticketStr.clear();
                
                //Ū��(��C���)
                for(int i=0; i<item.length; i++){
                    
                    ticketStr.add(i, item[i]);
                    
//                    System.out.println(ticketStr.get(i));
                }
                
                dataTable.add(ticketStr);
            }
			
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		
		// ����
		for (int i = 0; i < dataTable.size(); i++) {
			for (int j = 0; j < dataTable.get(i).size(); j++) {
				System.out.print(dataTable.get(i).get(j));
				System.out.print("             ");
				if (j == dataTable.get(i).size() -1 )
					System.out.println();
					
			}
		}
		
		return dataTable;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Admin ad = new Admin("�а��޲z�t��");

	}

}
