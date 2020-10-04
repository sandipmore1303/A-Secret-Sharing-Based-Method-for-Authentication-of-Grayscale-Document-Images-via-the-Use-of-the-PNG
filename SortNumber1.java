//<applet code=SortNumber.class width=600 height=400></applet>

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
//import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.applet.Applet;
import java.applet.AppletContext;
//import java.net.MalformedURLException;
import java.net.URL;


public class SortNumber1 extends Applet implements ActionListener{

    private static JTextField text[];
    private static JPanel mainPanel,textPanel,panelStart,southPanel;
    private static JTextArea textArea;
    private static JButton buttonStart,buttonOk;
    Applet SortNumber=new Applet();



       public SortNumber1(){

        mainPanel = new JPanel();
        textPanel = new JPanel();
        panelStart = new JPanel();
        southPanel = new JPanel();

        buttonStart = new JButton("Start Sorting");
        buttonOk = new JButton("View Numbers");
        buttonStart.addActionListener(this);
        buttonOk.addActionListener(this);

        textArea = new JTextArea(7,40);

        text = new JTextField[10];
        text[0] = new JTextField(3);
        text[1] = new JTextField(3);
        text[2] = new JTextField(3);
        text[3] = new JTextField(3);
        text[4] = new JTextField(3);
        text[5] = new JTextField(3);
        text[6] = new JTextField(3);
        text[7] = new JTextField(3);
        text[8] = new JTextField(3);
        text[9] = new JTextField(3);

        for(int i=0;i<text.length;i++){
            textPanel.add(text[i]);
        }

        buttonStart.setEnabled(false);

        panelStart.add(buttonOk);
        panelStart.add(buttonStart);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(textPanel,BorderLayout.NORTH);
        mainPanel.add(panelStart,BorderLayout.CENTER);
        mainPanel.add(southPanel,BorderLayout.SOUTH);

        southPanel.add(new JScrollPane().add(textArea));

        add(mainPanel);
        setSize(500,250);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }



    @Override
	public void start(){
	//URL codeBase = getCodeBase();
        String ss="SortNumber";
//        AppletContext context = getAppletContext();
//        Applet  AppletInstance = context.getApplet(getCodeBase().getPath());
	//String dd=getCodeBase().getPath();
	try {
    URL u = new URL (getDocumentBase(), " SortNumber.java");
    getAppletContext().showDocument (u, "_blank");
} catch (Exception e) {
}
	}


//    public static void main(String[] args) {
//        new SortNumber();
//    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj == buttonStart){
            buttonOk.setEnabled(!false);
            buttonStart.setEnabled(false);

            //textArea.setText(sort());
            textArea.append(sort());
        }

        if(obj == buttonOk){
            buttonOk.setEnabled(false);
            buttonStart.setEnabled(!false);

            textArea.setText("  [ "+getNumbers()+ " ]"+"\n\n");

        }

    }

    private static String getNumbers(){
        String numbers = "";
        for(int i=0;i<text.length;i++){
            numbers = numbers+text[i].getText().trim()+", ";
        }

        return numbers.trim();
    }

    private static String sort(){

        int t0 = Integer.valueOf(text[0].getText().trim());
        int t1 = Integer.valueOf(text[1].getText().trim());
        int t2 = Integer.valueOf(text[2].getText().trim());
        int t3 = Integer.valueOf(text[3].getText().trim());
        int t4 = Integer.valueOf(text[4].getText().trim());
        int t5 = Integer.valueOf(text[5].getText().trim());
        int t6 = Integer.valueOf(text[6].getText().trim());
        int t7 = Integer.valueOf(text[7].getText().trim());
        int t8 = Integer.valueOf(text[8].getText().trim());
        int t9 = Integer.valueOf(text[9].getText().trim());

        int[] xx = {t0,t1,t2,t3,t4,t5,t6,t7,t8,t9};

        Arrays.sort(xx);
        return Arrays.toString(xx);

    }

}

