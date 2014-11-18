# Automação Residencial Protocolar

Sobre
--------------
Este projeto compõe o Trabalho de Conclusão de Curso de Lucas Mellos Carlos e Francisco Vefago Guetener alunos do Instituto Federal Catarinense - Campus Avançado Sombrio intitulado de Automação Residencial Protocolar - Desenvolvimento Client-Side utilizando software e hardware livre. Este projeto basea-se no Albatross Master disponibilizado pela **[RoboCore](http://robocore.net)**, neste se o mesmo hardware utilizado no projeto original modificando apenas os códigos utilizados no projeto.

O que compõe este projeto?
--------------------------------
O projeto é composto de uma arquitetura cliente/servidor na qual tem-se:
- Servidor:

Utilizando o hardware necessário para o equipamento - um Arduino. O código para a projeto encontra-se na pasta `Arduino Versão Adaptada/Automação Modular`.

As bibliotecas necessárias para o funcionamento do Arduino estão na pasta `libraries` que compõe este repositório e devem ser colocadas dentro da pasta `libraries` do Arduino IDE.

- Client-Side:

Para o funcionamento da comunicação via protocolo UDP na porta 4444 é necessário uma estação cliente, na qual tem-se disponível até então para as plataformas: Windows, Mac OS X, Linux, iOS e Android. Ainda assim, deixa-se [novas sugestões](#contribuindo) de implementações em plataformas diferentes.

Instalação parte client-side
-------------------------------

Para a aplicação desenvolvida em Java para Windows, Mac OS X e Linux basta apenas importar a pasta `ARP` para o **[NetBeans IDE](https://netbeans.org/)**.

Para a app desenvolvida para iOS - iPhone é necessário somente realizar o download da pasta `TCC` e executar o projeto para **[Xcode](https://developer.apple.com/xcode/downloads/)**.

Na aplicação Android é só realizar a importação da pasta através do IDE  para dentro do **[Android SDK](http://developer.android.com/sdk/index.html)**.

Configurando a parte servidora
------------------------------------

A estação Arduino necessita do upload do código. Abrindo o serial monitor do Arduino IDE tem-se os comandos para manipulação da placa:

- Comando de configuração XBee do servidor:

```
  auto_config#

```

- Configuração do XBee escravo:

```
  config_slave:RCA_RELAY_1_0#
```
  
-  Para realizar a troca de endereço IP. (Após a execução do comando reiniciar o Arduino)
```
  ip=192.168.2.1#
```
  
- Mudança de MAC Address:
```
  mac=<xx-xx-xx-xx-xx-xx>#
```

- Verficar as informações de rede do Arduino e se as modificações surtiram efeito:
```
  ipconfig#
```

- Execução de testes:
A execução dos teste é definida somente para a aplicação Java para Windows, Mac OS X e Linux pelo fato de tornar o sistema mais privativo e dando uma melhor usabilidade aos usuários das aplicações móveis.
```
  final char CHR0 = 0;
  String finalprotocolo = "" + CHR0 + "";
  
  String testando = "/ARP/test" + finalprotocolo;
```

Configurando a parte cliente
-------------------------------------

Qualquer uma das estações do lado do cliente terá o IP gravado em um arquivo com extensão `.txt`.

- Mensagens para serem enviadas:

Comando Record (Gravação de comandos IR):
Após o LED ficar piscando posicionar o controle remoto no recepetor IR para que consiga realizar a leitura da frequência, após apertar o botão que deseja que seje o correspondente do comando. Função esta que assim como a função de teste se faz presente somente na aplicação Java para Windows, Mac OS X e Linux pelos mesmos motivos.
```
  final char CHR0 = 0;
  String finalprotocolo = "" + CHR0 + "";
  
  String record = "/ARP/IR/record" + finalprotocolo;
```

- Acionamento de rêles:
Os relês são identifcados por `0` e `1`. Sendo assim a mensagem é composta por:
```
  “/ARP/relay/<MAC_Xbee>/<número_relê>/<estado>”
```
- Envio de comandos IR:
As mensagens dos comandos IR  são compostas conforme o exemplo.
```
  final char CHR0 = 0;
  String finalprotocolo = "" + CHR0 + "";
  
  String ligartv = "/ARP/IR/enviar/LigaTV" + finalprotocolo;
```

Contribuindo
------------
Este projeto tem um cunho de software livre, por isso qualquer contribuição será bem vinda.
São pontos a serem implementados ainda na concepção do autores:
- Implementar o final de protocolo para a plataforma iOS;
- Implementar um app para Windows Phone;
- Utilizar sensores de umidade e temperatura;
- Implementar no uso do Framework Core Foundation Framework;
- Realizar a mesma implementação com WebSockets;
- Fazer a comunicação de fora da LAN.

Contato
-------------
Lucas Mellos Carlos
- https://twitter.com/RealLucasMellos
- lucas.mellos@hotmail.com

Francisco Vefago Guetener
- https://twitter.com/franciscovefago
- franciscovguetener@gmail.com
