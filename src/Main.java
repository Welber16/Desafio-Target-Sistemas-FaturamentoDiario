import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Main {
    public static void main(String[] args) {
        List<BigDecimal> faturamentoDiario = new ArrayList<>();
        BigDecimal mediaMensal = BigDecimal.ZERO;
        BigDecimal menorValor = BigDecimal.ZERO;
        BigDecimal maiorValor = BigDecimal.ZERO;
        int diasAcimaDaMedia = 0;

        try {
            // Ler o arquivo XML e armazenar os dados em uma lista de BigDecimal
            File file = new File("src/faturamento.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(file);
            NodeList nodeList = doc.getElementsByTagName("row");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                BigDecimal valor = new BigDecimal(element.getElementsByTagName("valor").item(0).getTextContent());

                if (valor.compareTo(BigDecimal.ZERO) > 0) {
                    faturamentoDiario.add(valor);
                }
            }

            // Calcular a média mensal dos valores de faturamento diário
            BigDecimal soma = BigDecimal.ZERO;

            for (BigDecimal valor : faturamentoDiario) {
                soma = soma.add(valor);
            }

            // Percorrer a lista e armazenar o menor e o maior valor de faturamento diário
            if (!faturamentoDiario.isEmpty()) {
                menorValor = faturamentoDiario.get(0);
                maiorValor = faturamentoDiario.get(0);

                for (BigDecimal valor : faturamentoDiario) {
                    if (valor.compareTo(menorValor) < 0) {
                        menorValor = valor;
                    }

                    if (valor.compareTo(maiorValor) > 0) {
                        maiorValor = valor;
                    }
                }
            }

            // Percorrer a lista novamente e contar quantos dias tiveram o valor de faturamento diário superior à média mensal
            for (BigDecimal valor : faturamentoDiario) {
                if (valor.compareTo(mediaMensal) > 0) {
                    diasAcimaDaMedia++;
                }
            }

            // Imprimir os resultados
            System.out.println("Menor valor: " + menorValor);
            System.out.println("Maior valor: " + maiorValor);
            System.out.println("Dias acima da média: " + diasAcimaDaMedia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}