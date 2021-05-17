package com.mycompany.myapp.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.mycompany.myapp.entities.Job;
import com.sun.mail.smtp.SMTPTransport;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ShowJob extends BaseForm {

    Form current;

    public ShowJob(Resources res, Job j) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        //setTitle("Internships");
        getContentPane().setScrollVisible(false);

//        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton intern = RadioButton.createToggle("Internship", barGroup);
        intern.setUIID("SelectBar");
        RadioButton job = RadioButton.createToggle("Job", barGroup);
        job.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        intern.addActionListener((l) -> {
            new ListInternship(res).show();
        });
        job.addActionListener((l) -> {
            new ListJob(res).show();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, intern, job),
                FlowLayout.encloseBottom(arrow)
        ));

        intern.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(intern, arrow);
        });
        bindButtonSelection(intern, arrow);
        bindButtonSelection(job, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        String urlImage = "back-logo.jpeg";
        Image placeHolder = Image.createImage(120, 90);
        EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
        URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
        addButton(urlim, j.getLibelle(), j.getDescription(), j.getPost(), j.getContrat(),
                String.valueOf(j.getSalary()), j.getLevel(), j.getD(), j.getCat(), j);

        Button postuler = new Button("Apply");
        postuler.addActionListener(l->{
            sendMail();
            System.out.println("sent mail !");
            postuler.setEnabled(false);
            if (postuler.isEnabled()) {
                System.out.println("button activé ");
            } else {
                System.out.println("button désactivé ");
            }
        });
        add(postuler);
        
        ScaleImageLabel image = new ScaleImageLabel(urlim);
        Container containerImg = new Container();
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }

    private void addButton(Image img, String libelle, String description, String poste, String contrat,
            String salaire, String niveau, String date, String category, Job j) {

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);

        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);

        Label lLibelle = new Label(libelle);
        lLibelle.setUIID("container");
        TextArea lDescription = new TextArea(description);
        lDescription.setFocusable(false);
        lDescription.setEditable(false);
        lDescription.setUIID("NewsTopLine");
        Label p = new Label("Post", "container");
        Label lPoste = new Label(poste);
        lPoste.setUIID("NewsTopLine");
        Label c = new Label("Contract Type", "container");
        Label lContrat = new Label(contrat);
        lContrat.setUIID("NewsTopLine");
        Label l = new Label("Level", "container");
        Label lNiveau = new Label(niveau);
        lNiveau.setUIID("NewsTopLine");
        Label s = new Label("Salary", "container");
        Label lSalaire = new Label(salaire);
        lSalaire.setUIID("NewsTopLine");
        Label d = new Label("Expiration Date", "container");
        Label lDate = new Label(date.substring(0, 10));
        lDate.setUIID("NewsTopLine");
        Label cat = new Label("Category", "container");
        Label lCat = new Label(category.substring(9,category.length()-1));
        lCat.setUIID("NewsTopLine");

        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(lLibelle, lDescription, p, lPoste,
                c, lContrat, l, lNiveau, s, lSalaire, d, lDate, cat, lCat));
        add(cnt);

    }
    
    String nomPdf = "Job";
    
    //<editor-fold defaultstate="collapsed" desc=" SendMail ">
    public void sendMail(){
        
        //authentification info
        String username = "asma.besbes@esprit.tn";
        String password = "203JFT1621";
        String fromEmail = "asma.besbes@esprit.tn";
        String toEmail = "asma.besbes@esprit.tn";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        //properties.put("mail.smtp.port", "587");
        properties.put("mail.smtps.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.println("test password");
                return new PasswordAuthentication(username, password);
            }
        });
        
        //start our mail message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject("Posulation");
            
            createPDF();
            
            //msg.setText("Email Body Text");
            Multipart emailContent = new MimeMultipart();
            
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Offre");

            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile("C:/Users/hp/Downloads/WorkshopOarsingJsonWithComments (1)/WorkshopOarsingJson/src/CV/" + nomPdf + "-CV.pdf");
            
            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(attachment);

            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            e.printStackTrace();
            //Logger.getLogger(Pidev.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            //Logger.getLogger(Pidev.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="CreatePDF">
    public void createPDF(){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("C:/Users/hp/Downloads/WorkshopOarsingJsonWithComments (1)/WorkshopOarsingJson/src/CV/" + nomPdf + "-CV.pdf"));
            document.open();
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addContent(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Nom & Prénom" + "                                          " + "Image candidat"));
        //addEmptyLine(preface, 1);
        preface.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        //preface.add(new Paragraph(
        //        "Report generated by: " + System.getProperty("user.name") + ", " + new Date(), smallBold));
        preface.add(new Paragraph("CONTACT DETAILS"));
        preface.add(new Paragraph("Email : \n"));
        preface.add(new Paragraph("N tel : \n"));
        preface.add(new Paragraph("Adresse : \n"));
        preface.add(new Paragraph("Facebook : \n"));
        preface.add(new Paragraph("LinkedIn : \n"));
        
        addEmptyLine(preface, 2);
        addEmptyLine(preface, 8);

        document.add(preface);
        // Start a new page
        document.newPage();
    }
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    //</editor-fold>
    
}
