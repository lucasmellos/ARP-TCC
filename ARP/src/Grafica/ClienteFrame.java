package Grafica;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ClienteFrame extends javax.swing.JFrame {

    //Final de protocolo
    final char CHR0 = 0;
    String finalprotocolo = "" + CHR0 + "";

    //Strings de acionamento dos relês
    String ligarrele1 = "/ARP/relay/0013A20040A13917/1/1";
    String desligarrele1 = "/ARP/relay/0013A20040A13917/1/0";
    String ligarrele0 = "/ARP/relay/0013A20040A13917/0/1";
    String desligarrele0 = "/ARP/relay/0013A20040A13917/0/0";

    //Strings de uso dos comandos IR
    String record = "/ARP/IR/record" + finalprotocolo;
    String ligartv = "/ARP/IR/enviar/LigaTV" + finalprotocolo;
    String paracima = "/ARP/IR/enviar/Cima" + finalprotocolo;
    String parabaixo = "/ARP/IR/enviar/Baixo" + finalprotocolo;
    String paraesquerda = "/ARP/IR/enviar/Esquerda" + finalprotocolo;
    String paradireita = "/ARP/IR/enviar/Direita" + finalprotocolo;
    String enter = "/ARP/IR/enviar/Enter" + finalprotocolo;
    String testando = "/ARP/test" + finalprotocolo;

    //Conversão das Strings de acionamento em array de bytes
    byte[] arrayligarrele1 = ligarrele1.getBytes();
    byte[] arraydesligarrele1 = desligarrele1.getBytes();
    byte[] arrayligarrele0 = ligarrele0.getBytes();
    byte[] arraydesligarrele0 = desligarrele0.getBytes();

    //Conversão das Strings IR em array de bytes
    byte[] arrayligartv = ligartv.getBytes();
    byte[] arrayrecord = record.getBytes();
    byte[] arrayparacima = paracima.getBytes();
    byte[] arrayparabaixo = parabaixo.getBytes();
    byte[] arrayparaesquerda = paraesquerda.getBytes();
    byte[] arrayparadireita = paradireita.getBytes();
    byte[] arrayenter = enter.getBytes();
    byte[] arraytest = testando.getBytes();

    public ClienteFrame() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TelaIp = new javax.swing.JDialog();
        TxtIp = new javax.swing.JTextField();
        Salvarip = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnRecord = new javax.swing.JButton();
        btnConfiguracoes = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnDesligarRele1 = new javax.swing.JButton();
        btnDesligarRele0 = new javax.swing.JButton();
        btnLigarRele1 = new javax.swing.JButton();
        btnLigarRele0 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnParaEsquerda = new javax.swing.JButton();
        btnEnter = new javax.swing.JButton();
        btnParaBaixo = new javax.swing.JButton();
        btnParaCima = new javax.swing.JButton();
        btnParaDireita = new javax.swing.JButton();
        btnLigarTv = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        TxtIp.setToolTipText("");

        Salvarip.setText("Salvar");
        Salvarip.setToolTipText("");
        Salvarip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvaripActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel2.setText("Automação Residencial Protocolar");

        javax.swing.GroupLayout TelaIpLayout = new javax.swing.GroupLayout(TelaIp.getContentPane());
        TelaIp.getContentPane().setLayout(TelaIpLayout);
        TelaIpLayout.setHorizontalGroup(
            TelaIpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TelaIpLayout.createSequentialGroup()
                .addGroup(TelaIpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TelaIpLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(TxtIp, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(TelaIpLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(Salvarip))
                    .addGroup(TelaIpLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        TelaIpLayout.setVerticalGroup(
            TelaIpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TelaIpLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(TxtIp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Salvarip)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        TxtIp.getAccessibleContext().setAccessibleName("");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnRecord.setBackground(new java.awt.Color(255, 255, 255));
        btnRecord.setForeground(new java.awt.Color(204, 0, 0));
        btnRecord.setText("RECORD");
        btnRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecordActionPerformed(evt);
            }
        });

        btnConfiguracoes.setText("Configurações");
        btnConfiguracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracoesActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel1.setText("Automação Residencial Protocolar");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnDesligarRele1.setText("Desligar Rele 1");
        btnDesligarRele1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesligarRele1ActionPerformed(evt);
            }
        });

        btnDesligarRele0.setText("Desligar Rele 0");
        btnDesligarRele0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesligarRele0ActionPerformed(evt);
            }
        });

        btnLigarRele1.setText("Ligar Rele 1");
        btnLigarRele1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLigarRele1ActionPerformed(evt);
            }
        });

        btnLigarRele0.setText("Ligar Rele 0");
        btnLigarRele0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLigarRele0ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDesligarRele1)
                    .addComponent(btnDesligarRele0))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLigarRele1)
                    .addComponent(btnLigarRele0))
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDesligarRele0)
                    .addComponent(btnLigarRele0))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDesligarRele1)
                    .addComponent(btnLigarRele1))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnParaEsquerda.setText("Para Esquerda");
        btnParaEsquerda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParaEsquerdaActionPerformed(evt);
            }
        });

        btnEnter.setText("Enter");
        btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });

        btnParaBaixo.setText("Para Baixo");
        btnParaBaixo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParaBaixoActionPerformed(evt);
            }
        });

        btnParaCima.setText("Para Cima");
        btnParaCima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParaCimaActionPerformed(evt);
            }
        });

        btnParaDireita.setText("Para Direita");
        btnParaDireita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParaDireitaActionPerformed(evt);
            }
        });

        btnLigarTv.setText("Menu/Exit");
        btnLigarTv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLigarTvActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLigarTv, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnParaCima)
                            .addComponent(btnParaBaixo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnParaEsquerda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnParaDireita)
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLigarTv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnParaCima)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnParaDireita)
                    .addComponent(btnParaEsquerda)
                    .addComponent(btnEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(btnParaBaixo)
                .addGap(25, 25, 25))
        );

        jButton1.setText("Teste");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnRecord)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnConfiguracoes))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 120, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnConfiguracoes)
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(jButton1)
                .addGap(4, 4, 4)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(btnRecord)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecordActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura

        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo*/
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arrayrecord, arrayrecord.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {              //Exceções 
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }
    }//GEN-LAST:event_btnRecordActionPerformed

    private void btnConfiguracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracoesActionPerformed
        TelaIp.setSize(328, 167);
        TelaIp.setVisible(true);
    }//GEN-LAST:event_btnConfiguracoesActionPerformed

    private void btnLigarTvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLigarTvActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura
        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arrayligartv, arrayligartv.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }
    }//GEN-LAST:event_btnLigarTvActionPerformed

    private void SalvaripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvaripActionPerformed
        File numeroip = new File("IP.txt");                //Cria o objeto arquivo de texto .txt
        try (FileWriter escrever = new FileWriter(numeroip)) {
            escrever.write(TxtIp.getText());                //Escreve no arquivo pelo Text Field
            escrever.flush();   //Força a escrita
            JOptionPane.showMessageDialog(null, "IP salvo com sucesso!");
        } catch (IOException ex) {                          //Exception
            JOptionPane.showMessageDialog(null, "ERRO ao salvar IP");
        }
    }//GEN-LAST:event_SalvaripActionPerformed

    private void btnDesligarRele0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesligarRele0ActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura
        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arraydesligarrele0, arraydesligarrele0.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }
    }//GEN-LAST:event_btnDesligarRele0ActionPerformed

    private void btnLigarRele0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLigarRele0ActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura
        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arrayligarrele0, arrayligarrele0.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }
    }//GEN-LAST:event_btnLigarRele0ActionPerformed

    private void btnDesligarRele1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesligarRele1ActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura
        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arraydesligarrele1, arraydesligarrele1.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }
    }//GEN-LAST:event_btnDesligarRele1ActionPerformed

    private void btnLigarRele1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLigarRele1ActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura
        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arrayligarrele1, arrayligarrele1.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }
    }//GEN-LAST:event_btnLigarRele1ActionPerformed

    private void btnParaCimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParaCimaActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura
        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arrayparacima, arrayparacima.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }
    }//GEN-LAST:event_btnParaCimaActionPerformed

    private void btnParaDireitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParaDireitaActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura
        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arrayparadireita, arrayparadireita.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }
    }//GEN-LAST:event_btnParaDireitaActionPerformed

    private void btnParaBaixoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParaBaixoActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura
        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arrayparabaixo, arrayparabaixo.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }
    }//GEN-LAST:event_btnParaBaixoActionPerformed

    private void btnParaEsquerdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParaEsquerdaActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura
        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arrayparaesquerda, arrayparaesquerda.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }
    }//GEN-LAST:event_btnParaEsquerdaActionPerformed

    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura
        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arrayenter, arrayenter.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }
    }//GEN-LAST:event_btnEnterActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        File numeroip = new File("IP.txt");         //Cria o objeto de leitura
        try (FileReader lerarquivo = new FileReader(numeroip)) {
            ping();
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            DatagramSocket ds = null;

            ds = new DatagramSocket();              //Cria o objeto Datagram Socket
            InetAddress ip;                         //Define um IP
            String endip = String.valueOf(ler);       //Converte para char para String
            ip = InetAddress.getByName(endip);      //Passa o parâmetro IP em InetAddress por nome
            DatagramPacket dp = new DatagramPacket(arraytest, arraytest.length, ip, 4444);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha de conexão\n" + ex);
        }    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClienteFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Salvarip;
    private javax.swing.JDialog TelaIp;
    private javax.swing.JTextField TxtIp;
    private javax.swing.JButton btnConfiguracoes;
    private javax.swing.JButton btnDesligarRele0;
    private javax.swing.JButton btnDesligarRele1;
    private javax.swing.JButton btnEnter;
    private javax.swing.JButton btnLigarRele0;
    private javax.swing.JButton btnLigarRele1;
    private javax.swing.JButton btnLigarTv;
    private javax.swing.JButton btnParaBaixo;
    private javax.swing.JButton btnParaCima;
    private javax.swing.JButton btnParaDireita;
    private javax.swing.JButton btnParaEsquerda;
    private javax.swing.JButton btnRecord;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
private void ping() throws IOException {
        FileReader lerarquivo = null;
        try {
            File numeroip = new File("IP.txt");         //Cria o objeto de leitura
            lerarquivo = new FileReader(numeroip);
            char[] ler = new char[15];              //Cria um buffer de leitura
            lerarquivo.read(ler);                   //Lê o arquivo
            String endip = String.valueOf(ler);       //Converte para char para String
            if (InetAddress.getByName(endip).isReachable(1000)) {
                JOptionPane.showMessageDialog(null, "Falha na conexão. Verfique se você esta conectado a rede.");
            } else {
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                lerarquivo.close();
            } catch (IOException ex) {
                Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
