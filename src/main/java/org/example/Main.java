package org.example;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        for (int i = 2917; i <= 5697; i++) {
            String path = "C:\\Users\\Edi\\IdeaProjects\\SRF\\src\\Training\\" + i + "NEWS.XML";
            File xmlfile = new File(path);


            if (!xmlfile.exists()) {
                continue;
            }

            try {
                Document doc = builder.parse(xmlfile);


                try (FileWriter myWriter = new FileWriter(i+".txt")) {

                    NodeList titlu = doc.getElementsByTagName("title");
                    for (int r = 0; r < titlu.getLength(); r++) {
                        Node node = titlu.item(r);
                        String continutTitlu = node.getTextContent().toLowerCase().replaceAll("\\p{Punct}+"," ");


                            myWriter.write(continutTitlu + "\n");

                        System.out.println("Titlul fisierului: " + continutTitlu);
                    }

                    NodeList text = doc.getElementsByTagName("text");
                    for (int t = 0; t < text.getLength(); t++) {
                        Node node = text.item(t);
                        String continutText = node.getTextContent().toLowerCase().replaceAll("\\p{Punct}+"," ");
                            myWriter.write(continutText + "\n");
                            System.out.println("Continut " + i + ": " + continutText);

                        System.out.println("Continut: " + continutText);
                    }

                    System.out.println("Fisierul " + i + "NEWS.XML procesat cu succes.");

                }
            } catch (Exception e) {
                System.out.println("A apărut o eroare la procesarea fișierului: " + xmlfile.getName());
                e.printStackTrace();
            }
        }

    }
}