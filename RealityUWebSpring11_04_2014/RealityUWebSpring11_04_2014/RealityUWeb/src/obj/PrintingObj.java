package obj;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.CoordinatorDAO;
import dao.GroupsDAO;
import dao.SurveysDAO;

import java.awt.print.*;
import java.awt.*;

import javax.swing.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class PrintingObj {
	  String inputLine; 
	public void Printing(){
		JTextPane jtp = new JTextPane();
		jtp.setBackground(Color.white);
		jtp.setText("text to print");
		boolean show = true;
		try {
			jtp.print(null, null, show, null, null, show);
		} catch (java.awt.print.PrinterException ex) {
			ex.printStackTrace();
		}
	}
		
}
