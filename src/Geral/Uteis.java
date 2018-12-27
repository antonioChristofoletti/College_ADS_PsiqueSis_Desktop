package Geral;

import java.awt.Color;
import java.awt.Component;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.MaskFormatter;

/**
 * Classe que armazena algumas funções úteis para a equipe de desenvolvimento
 *
 * @author Antonio
 */
public abstract class Uteis {

    /**
     * Método responsável por invocar uma mensagem condicional
     * (showOptionDialog)
     *
     * @param component objeto que está invocando o método
     * @param mensagem mensagem que será apresentada
     * @param titulo título que será apresentado
     * @return '0' para não e '1' para sim
     */
    public static int mensagemCondicional(Component component, String mensagem, String titulo) {
        Object[] options = {"Sim", "Não"};

        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);

        int valor = JOptionPane.showOptionDialog(component, mensagem, titulo, 0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (valor == 0) {
            return 0;
        }

        return 1;
    }

    /**
     * Método responsável por invocar uma mensagem de aviso (showMessageDialog)
     *
     * @param component objeto que está invocando o método
     * @param mensagem mensagem que será apresentada
     * @param titulo título que será apresentado
     */
    public static void mensagemAviso(Component component, String mensagem, String titulo) {
        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);

        JOptionPane.showMessageDialog(component, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Método responsável por invocar uma mensagem de erro (showMessageDialog)
     *
     * @param component objeto que está invocando o método
     * @param mensagem mensagem que será apresentada
     * @param titulo título que será apresentado
     */
    public static void mensagemErro(Component component, String mensagem, String titulo) {
        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);

        JOptionPane.showMessageDialog(component, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Método responsável por retirar máscara das String, permanecendo apenas o
     * valor bruto
     *
     * @param stringComMascara String original que sofrerá alteração
     * @param valoresRetirar sinais e digitos que são considerados máscara.
     * Exemplo de passagem de parâmetro: ".()-* "
     * @param substituirPor valor que deverá ser colocado no local, no qual
     * existiam os valores de máscara
     * @return retorna a string sem a máscara
     */
    public static String retiraMascara(String stringComMascara, String valoresRetirar, String substituirPor) {
        for (int i = 0; i < valoresRetirar.length(); i++) {
            stringComMascara = stringComMascara.replace(String.valueOf(valoresRetirar.charAt(i)), substituirPor);
        }

        return stringComMascara;
    }

    /**
     * Método responsável por converter um Date para um formato legível, por
     * exemplO: dd/MM/yyyy ou MM/dd/yyy HH:mm
     *
     * @param mascara mascará de base para conversão
     * @param dt data que será utilizada
     * @return retorna uma string convertida
     */
    public static String converteData(String mascara, Date dt) {
        String aux = new SimpleDateFormat(mascara).format(dt);

        return aux;
    }

    /**
     * Método responsável por remover toda a acentuação de uma string
     *
     * @param str string que sofrerá a alteração
     * @return retorna uma String sem a acentuação
     */
    public static String removeAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    /**
     * Método responsável por colocar uma máscara em uma String
     *
     * @param valor valor que receberá uma String
     * @param mascara máscara que será colocada na String
     * @return retorna uma String com a máscara
     */
    public static String setaMascara(String valor, String mascara) {
        try {
            MaskFormatter mf;
            mf = new MaskFormatter(mascara);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(valor);
        } catch (ParseException ex) {
            return valor;
        }
    }
}
