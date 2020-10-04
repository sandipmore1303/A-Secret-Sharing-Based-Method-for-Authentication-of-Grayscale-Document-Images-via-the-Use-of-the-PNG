import java.awt.*;
import java.awt.image.*;
import java.applet.*;
import java.net.*;
import java.io.*;
import java.lang.Math;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JApplet;
import javax.imageio.*;
import javax.swing.event.*;



public class medianFiltering extends JApplet {
	
	Image edgeImage, accImage, outputImage;
	MediaTracker tracker = null;
	PixelGrabber grabber = null;
	int width = 0, height = 0;
	String fileNames[] = {"I.png","TEST1_NOISE.png"};

	javax.swing.Timer timer;

	int templatesize=3;

	int imageNumber=0;
	static int progress=0;
	public int orig[] = null;
	
	Image image[] = new Image[fileNames.length];
	
	JProgressBar progressBar;
	JPanel controlPanel,noisePanel, imagePanel, progressPanel;
	JLabel origLabel, outputLabel,noiseLabel,noiseLabel2,noiseLabel3,comboLabel,templateLabel,processing;
	JSlider templateSlider, radiusSlider, noiseSlider;
	JRadioButton gaussianRadio, condimentRadio;
	JComboBox imSel;
	static medianFilter filter;
	static gaussianNoise gNoise;
	static condimentNoise cNoise;
	ButtonGroup radiogroup;
	String noisemode="Condiment";

	static final int NOISE_MIN = 0;
	static final int NOISE_MAX = 50;
	static final int NOISE_INIT = 5;
	static int noise=NOISE_INIT;
	 
	 
	   	// Applet init function	
	public void init() {
		
		tracker = new MediaTracker(this);
		for(int i = 0; i < fileNames.length; i++) {
			image[i] = getImage(this.getCodeBase(),fileNames[i]);
			image[i] = image[i].getScaledInstance(256, 256, Image.SCALE_SMOOTH);
			tracker.addImage(image[i], i);
		}
		try {
			tracker.waitForAll();
		}
		catch(InterruptedException e) {
			System.out.println("error: " + e);
		}
		
		Container cont = getContentPane();
		cont.removeAll();
		cont.setBackground(Color.black);
		cont.setLayout(new BorderLayout());
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2,4,15,0));
		controlPanel.setBackground(new Color(192,204,226));
		imagePanel = new JPanel();
		imagePanel.setBackground(new Color(192,204,226));
		progressPanel = new JPanel();
		progressPanel.setBackground(new Color(192,204,226));
		progressPanel.setLayout(new GridLayout(2,1));
		noisePanel = new JPanel();
		noisePanel.setBackground(new Color(192,204,226));
		noisePanel.setLayout(new GridLayout(2,1));

		comboLabel = new JLabel("IMAGE");
		comboLabel.setHorizontalAlignment(JLabel.CENTER);
		controlPanel.add(comboLabel);

		noiseLabel3 = new JLabel("NOISE TYPE");
		noiseLabel3.setHorizontalAlignment(JLabel.CENTER);
		controlPanel.add(noiseLabel3);

		noiseLabel = new JLabel("NOISE = "+NOISE_INIT+"%");
		noiseLabel.setHorizontalAlignment(JLabel.CENTER);
		controlPanel.add(noiseLabel);



		templateLabel = new JLabel("TEMPLATE SIZE = 3");
		templateLabel.setHorizontalAlignment(JLabel.CENTER);
		controlPanel.add(templateLabel);


		processing = new JLabel("Processing...");
		processing.setHorizontalAlignment(JLabel.LEFT);
		progressBar = new JProgressBar(0,100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true); //get space for the string
        progressBar.setString("");          //but don't paint it
		progressPanel.add(processing);
		progressPanel.add(progressBar);
		
		width = image[imageNumber].getWidth(null);
		height = image[imageNumber].getHeight(null);

		imSel = new JComboBox(fileNames);
		imageNumber = imSel.getSelectedIndex();
		imSel.addActionListener( 
			new ActionListener() {  
				public void actionPerformed(ActionEvent e) {
					imageNumber = imSel.getSelectedIndex();
					origLabel.setIcon(new ImageIcon(image[imageNumber]));	
					processImage();
				}
			}
		);
		controlPanel.add(imSel, BorderLayout.PAGE_START);

        timer = new javax.swing.Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                progressBar.setValue(filter.getProgress());
            }
        });

		origLabel = new JLabel("Original Image", new ImageIcon(image[imageNumber]), JLabel.CENTER);
		origLabel.setVerticalTextPosition(JLabel.BOTTOM);
		origLabel.setHorizontalTextPosition(JLabel.CENTER);
		origLabel.setForeground(Color.blue);
		imagePanel.add(origLabel);
		
		noiseLabel2 = new JLabel("Noisy Image", new ImageIcon(image[imageNumber]), JLabel.CENTER);
		noiseLabel2.setVerticalTextPosition(JLabel.BOTTOM);
		noiseLabel2.setHorizontalTextPosition(JLabel.CENTER);
		noiseLabel2.setForeground(Color.blue);
		imagePanel.add(noiseLabel2);
		
		outputLabel = new JLabel("Median Filtered", new ImageIcon(image[imageNumber]), JLabel.CENTER);
		outputLabel.setVerticalTextPosition(JLabel.BOTTOM);
		outputLabel.setHorizontalTextPosition(JLabel.CENTER);
		outputLabel.setForeground(Color.blue);
		imagePanel.add(outputLabel);
	

		gaussianRadio = new JRadioButton("Gaussian");
    	gaussianRadio.setActionCommand("Gaussian");
		gaussianRadio.setBackground(new Color(192,204,226));
		gaussianRadio.setHorizontalAlignment(SwingConstants.CENTER);
		condimentRadio = new JRadioButton("Condiment");
    	condimentRadio.setActionCommand("Condiment");
		condimentRadio.setHorizontalAlignment(SwingConstants.CENTER);
		condimentRadio.setBackground(new Color(192,204,226));
    	condimentRadio.setSelected(true);
	    radiogroup = new ButtonGroup();
	    radiogroup.add(condimentRadio);
	    radiogroup.add(gaussianRadio);
	    condimentRadio.addActionListener(new radiolistener());
	    gaussianRadio.addActionListener(new radiolistener());
		noisePanel.add(gaussianRadio);
		noisePanel.add(condimentRadio);
		controlPanel.add(noisePanel);

		noiseSlider = new JSlider(JSlider.HORIZONTAL, NOISE_MIN, NOISE_MAX, NOISE_INIT);
		noiseSlider.addChangeListener(new noiseListener());
		noiseSlider.setMajorTickSpacing(10);
		noiseSlider.setMinorTickSpacing(2);
		noiseSlider.setPaintTicks(true);
		noiseSlider.setPaintLabels(true);
		noiseSlider.setBackground(new Color(192,204,226));
		controlPanel.add(noiseSlider);


		templateSlider = new JSlider(JSlider.HORIZONTAL, 3, 43, 3);
		templateSlider.addChangeListener(new templateListener());
		templateSlider.setMajorTickSpacing(10);
		templateSlider.setMinorTickSpacing(2);
		templateSlider.setPaintTicks(true);
		templateSlider.setPaintLabels(true);
		templateSlider.setBackground(new Color(192,204,226));
		controlPanel.add(templateSlider);

		cont.add(controlPanel, BorderLayout.NORTH);
		cont.add(imagePanel, BorderLayout.CENTER);
		cont.add(progressPanel, BorderLayout.SOUTH);

		processImage();

	}
	class radiolistener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
			noisemode=e.getActionCommand();
			processImage();
	    }
	}
  	class templateListener implements ChangeListener {
	    public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
				int val=source.getValue();
				if(val%2==0)
					val=val+1;
				source.setValue(val);
	
				System.out.println("template="+val);
				templatesize=val;
				templateLabel.setText("TEMPLATE SIZE = "+val);
				processImage();
	        }    
	    }
	}
	class noiseListener implements ChangeListener {
	    public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
				System.out.println("noise="+source.getValue()+"%");
				noise=source.getValue();
				noiseLabel.setText("NOISE = "+source.getValue()+"%");
				processImage();
	        }    
	    }
	}
	private void processImage(){
		orig=new int[width*height];
		PixelGrabber grabber = new PixelGrabber(image[imageNumber], 0, 0, width, height, orig, 0, width);
		try {
			grabber.grabPixels();
		}
		catch(InterruptedException e2) {
			System.out.println("error: " + e2);
		}
		progressBar.setMaximum(width-templatesize);

		processing.setText("Processing...");
		templateSlider.setEnabled(false);
		imSel.setEnabled(false);
		noiseSlider.setEnabled(false);
		gaussianRadio.setEnabled(false);
		condimentRadio.setEnabled(false);

		gNoise = new gaussianNoise();
		cNoise = new condimentNoise();
		filter = new medianFilter();

		gNoise.init(orig,width,height,(float)noise);
		cNoise.init(orig,width,height,(float)noise/100);
		if(noisemode=="Gaussian"){
			orig=gNoise.process();
		}
		if(noisemode=="Condiment"){
			orig=cNoise.process();
		}

		filter.init(orig,width,height,templatesize);

		timer.start();
		new Thread(){
			public void run(){
				final Image output = createImage(new MemoryImageSource(width, height, filter.process(), 0, width));
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						outputLabel.setIcon(new ImageIcon(output));	
						if(noisemode=="Gaussian"){
						noiseLabel2.setIcon(new ImageIcon(createImage(new MemoryImageSource(width, height, gNoise.process(), 0, width))));	
						}
	                 			if(noisemode=="Condiment"){
						noiseLabel2.setIcon(new ImageIcon(createImage(new MemoryImageSource(width, height, cNoise.process(), 0, width))));	
						}
						processing.setText("Done");
						templateSlider.setEnabled(true);
						imSel.setEnabled(true);
						noiseSlider.setEnabled(true);
						gaussianRadio.setEnabled(true);
						condimentRadio.setEnabled(true);
                                               
					}
				});
			}
		}.start();
	}
       
        

}