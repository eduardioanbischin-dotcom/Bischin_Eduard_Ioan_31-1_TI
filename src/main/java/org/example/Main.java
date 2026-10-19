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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Set<String> stopwords = new HashSet<String>();
        Map<String, Integer> dictionarGlobal = new HashMap<>();
        int idCurent = 0;
        try {
            Path stop = Path.of("src/stopwords.txt");
            List<String> lines = Files.readAllLines(stop);
            for (String line : lines) {
                stopwords.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {

        }
        for (int i = 2917; i <= 5697; i++) {

            String path = "src/Training/" + i + "NEWS.XML";
            File xmlfile = new File(path);


            if (!xmlfile.exists()) {
                continue;
            }
            HashMap<Integer, Integer> words = new HashMap<>();
            try {
                Document doc = builder.parse(xmlfile);


                try (FileWriter myWriter = new FileWriter(i+".txt")) {

                    NodeList titlu = doc.getElementsByTagName("title");
                    for (int r = 0; r < titlu.getLength(); r++) {
                        Node node = titlu.item(r);
                        String continutTitlu = node.getTextContent().toLowerCase().replaceAll("\\p{Punct}+", " ").replaceAll("\\p{javaDigit}+", " ");
                        String[] cuvinte = continutTitlu.split("\\s+");
                        for (String cuvant : cuvinte) {

                            if (!cuvant.isEmpty() && !stopwords.contains(cuvant) && cuvant.length()>1 ) {
                                if (!dictionarGlobal.containsKey(cuvant)) {
                                    dictionarGlobal.put(cuvant, idCurent);
                                    idCurent++;
                                }

                                int wordId = dictionarGlobal.get(cuvant);


                                words.put(wordId, words.getOrDefault(wordId, 0) + 1);
                                myWriter.write(cuvant + " ");
                            }



                        }
                    }
                    myWriter.write("\n");


                    NodeList text = doc.getElementsByTagName("text");
                    for (int t = 0; t < text.getLength(); t++) {
                        Node node = text.item(t);
                        String continutText = node.getTextContent().toLowerCase().replaceAll("\\p{Punct}+"," ").replaceAll("\\p{javaDigit}+"," " );
                        String[] cuvinte = continutText.split("\\s+");
                        for (String cuvant : cuvinte) {

                            if (!cuvant.isEmpty() && !stopwords.contains(cuvant) && cuvant.length()>1 ) {
                                if (!dictionarGlobal.containsKey(cuvant)) {
                                    dictionarGlobal.put(cuvant, idCurent);
                                    idCurent++;
                                }

                                int wordId = dictionarGlobal.get(cuvant);


                                words.put(wordId, words.getOrDefault(wordId, 0) + 1);
                                myWriter.write(cuvant + " ");
                            }



                        }
                    }
                    myWriter.write("\n");

                    myWriter.write("--- Dictionar Global ---\n");
                    for (Map.Entry<String, Integer> pereche : dictionarGlobal.entrySet()) {
                        myWriter.write(pereche.getKey() + " : " + pereche.getValue() + "\n");
                    }

                    myWriter.write("\n--- Frecventa in acest text  ---\n");
                    for (Map.Entry<Integer, Integer> pereche : words.entrySet()) {
                        myWriter.write(pereche.getKey() + " : " + pereche.getValue() + "\n");
                    }     }
            } catch (Exception e) {
                System.out.println("A apărut o eroare la procesarea fișierului: " + xmlfile.getName());
                e.printStackTrace();
            }
        }

    }
}