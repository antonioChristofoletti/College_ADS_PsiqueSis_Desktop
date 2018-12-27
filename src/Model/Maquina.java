/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;

/**
 * Classe do tipo Model referente as máquinas da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class Maquina {

    protected int id;
    protected String ip;
    protected String mac;
    protected String porta;
    protected String status;
    protected Date dataAcesso;
    protected Pessoa p;

    /**
     * Esse construtor gerará automaticamente um objeto com o IP, MAC e
     * dataAcesso, bastanto informar apenas a porta do soquete e o status do
     * mesmo
     *
     * @param porta porta de acesso do soquete
     * @param status status do computador, por padrão é 'Ativo'
     * @throws Exception disparada no processo de leitura de MAC ou IP
     */
    public Maquina(String porta, String status) throws Exception {
        try {
            InetAddress address = InetAddress.getLocalHost();

            this.ip = address.getHostAddress();

            this.mac = "";

            this.dataAcesso = new Date();

            try {
                NetworkInterface ni = NetworkInterface.getByInetAddress(address);
                byte[] mac = ni.getHardwareAddress();
                for (int i = 0; i < mac.length; i++) {
                    this.mac += (String.format("%02X-", mac[i]));
                }
                this.mac = this.mac.substring(0, this.mac.length() - 1);
            } catch (Exception ex) {
                mac = "00-00-00-00-00-00";
            }

            this.porta = porta;
            this.status = status;
        } catch (Exception e) {
            throw new Exception("Erro ao gerar informações sobre a máquina. Erro: " + e.getMessage());
        }
    }

    public Maquina() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pessoa getP() {
        return p;
    }

    public void setP(Pessoa p) {
        this.p = p;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Date getDataAcesso() {
        return dataAcesso;
    }

    public void setDataAcesso(Date dataAcesso) {
        this.dataAcesso = dataAcesso;
    }
}
