##Automação Residencial Protocolar

Sobre
--------------
Este projeto compõe o Trabalho de Conclusão de Curso de Lucas Mellos Carlos e Francisco Vefago Guetener alunos do Instituto Federal Catarinense - Campus Avançado Sombrio intitulado de Automação Residencial Protocolar - Desenvolvimento Client-Side utilizando software e hardware livre.

O que compõe este projeto?
--------------------------------
O projeto é composto de uma arquitetura cliente/servidor na qual tem-se:
- Servidor:

Utilizando o hardware necessário para o equipamento. O código para a projeto encontra-se na pasta `Arduino Versão Adaptada/Automação Modular`.

As bibliotecas necessárias para o funcionamento do Arduino estão pasta `libraries` que compõe este repositório.

- Client-Side:

Para o funcionamento da comunicação via protocolo UDP na porta 4444 é necessário uma estação cliente, na qual tem-se disponível até então para as plataformas: Windows, Mac OS X, Linux, iOS e Android.

Instalação parte client-side
-------------------------------

Para a aplicação desenvolvida em Java para Windows, Mac OS X e Linux basta apenas importar a pasta `ARP` para o **[NetBeans IDE](https://netbeans.org/)**.

Para a app desenvolvida para iOS - iPhone é necessário somente realizar o download da pasta `TCC` e executar o projeto para Xcode.

Na aplicação Android ............

Configurando as estações (Servidor)
------------------------------------

A estação Arduino necessita do upload do código. Abrindo o serial monitor do Arduino IDE os comandos:
- Comando de configuração XBee do servidor:

```
  auto_config#
```

- Configuração do XBee escravo:

```
  ip=192.168.2.1#
  ```
  
-  Para realizar a troca de endereço IP. (Após a execução do comando reiniciar o Arduino)
  ```
  ip=192.168.2.1#
  ```

Configurando as estações (Cliente)
-------------------------------------

Qualquer uma das estações do lado do cliente terá o IP gravado em um arquivo com extensão `.txt`.

- Mensagens para serem enviadas:

Comando Record (Gravação de comandos IR):
Após o LED ficar piscando posicionar o controle remoto no recepetor IR para que consiga realizar a leitura da frequência, após apertar o botão que deseja que seje o correspondente do comando.
```
  /ARP/IR/record
```

Acionamento de rêles:
Para cada re........................

Contribuindo
------------
Este projeto tem um cunho de software livre, por isso qualquer contribuição será bem vinda.
São pontos a serem implementados ainda na concepção do autores:
- Implementar o final de protocolo para a plataforma iOS;
- Implementar um app para Windows Phone;
- Utilizar sensores de umidade e temperatura.

Contato
-------------
Lucas Mellos Carlos
- https://twitter.com/RealLucasMellos
- lucas.mellos@hotmail.com

Francisco Vefago Guetener
- franciscovguetener@gmail.com
