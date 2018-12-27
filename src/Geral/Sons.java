/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Geral;

import java.io.File;

/**
 * Classe responsável por armazenar os sons utilizados no sistema
 * @author Antonio
 */
public abstract class Sons {
    /**
     * Sons padrão de notificação de agendamento. Som semelhante ao encontrado no painél do banco Itaú
     */
    public final static File lightSongAgendamento = new File("src/Som/lightSongAgendamento.wav").getAbsoluteFile();
    
    /**
     * Variação de son para notificação de agendamento. Som de alerta, semelhante a uma sirene
     */
    public final static File alarmSongAgendamento = new File("src/Som/alarmSongAgendamento.wav").getAbsoluteFile();
}
