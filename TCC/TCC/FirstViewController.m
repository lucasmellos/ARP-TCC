//
//  FirstViewController.m
//  TCC
//
//  Created by M on 12/08/14.
//  Copyright (c) 2014 M. All rights reserved.
//

#import "FirstViewController.h"

@interface FirstViewController ()

@end

@implementation FirstViewController
            
- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)LigarTV:(id)sender{
    // Pega o endereço IP
    NSArray *caminho = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *diretorioDocumento = [caminho objectAtIndex:0];
    NSString *caminhoArquivo = [diretorioDocumento stringByAppendingPathComponent:@"IPiOS.txt"];
    NSString *enderecoIp = [NSString stringWithContentsOfFile:caminhoArquivo encoding:NSUTF8StringEncoding error:NULL];
    
    //unichar c = 0x00;// c = 0x00;
//    NSString *nova = [NSString stringWithFormat:@"%c", c];
    //NSString *nova = [NSString stringWithCharacters:&c length:1];
    //NSString *s = [NSString stringWithFormat:@"Long %C dash", 0x2014];
 //   NSString *c= @"/ARP/enviar/Enter";
    // Pega a menssagem
    NSString *mensagem = [NSString stringWithFormat:@"/ARP/enviar/Enter",  0x00];
    //NSString *mensagem = [final stringByAppendingString:nova];
    
    // Mensagem de alerta
    UIAlertView *alert;
    
    // DEST Address
    sockaddr_in *dest_addr = (sockaddr_in*)malloc(sizeof(sockaddr_in));
    if(dest_addr == NULL){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Error creating dest_addr"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    // Init address
    memset(dest_addr, 0, sizeof(sockaddr_in));
    dest_addr->sin_family = AF_INET;
    
    // Get Source.
    
    // Load address.
    int pton_val = inet_pton(AF_INET, [enderecoIp UTF8String], &(dest_addr->sin_addr.s_addr));
    if(pton_val == 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"IP inválido"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }else if(pton_val < 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"Error in pton"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }
    // Definindo a porta
    dest_addr->sin_port=htons(4444);
    
    // Criando o socket.
    int sock_fd = socket(AF_INET, SOCK_DGRAM, UDP);
    
    // Error handling.
    if(sock_fd < 0){
        perror("socket");
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Socket não foi aberto"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok" otherButtonTitles:nil, nil];
        [alert show];
    }else{  // Our socket was opened
        // Get Message Data
        NSData *bytes = [mensagem dataUsingEncoding:NSUTF8StringEncoding];
        uint8_t * rawBytes = (uint8_t*)[bytes bytes];
        
        // Send our message.
        NSInteger error = sendto(sock_fd, rawBytes, [mensagem length], 0, (struct sockaddr*)dest_addr, sizeof(sockaddr_in));
        if(error < 0){
            alert = [[UIAlertView alloc]
                     initWithTitle:@"Erro!"
                     message:@"Messagem não chegou ao destino"
                     delegate:nil  // set nil if you don't want the yes button callback
                     cancelButtonTitle:@"Ok"                     otherButtonTitles:nil, nil];
            [alert show];
        }
    }
}


- (IBAction)Menu:(id)sender {
    // Pega o endereço IP
    NSArray *caminho = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *diretorioDocumento = [caminho objectAtIndex:0];
    NSString *caminhoArquivo = [diretorioDocumento stringByAppendingPathComponent:@"IPiOS.txt"];
    NSString *enderecoIp = [NSString stringWithContentsOfFile:caminhoArquivo encoding:NSUTF8StringEncoding error:NULL];
    
    unichar c = 0x00;// c = 0x00;
    //   NSString *nova = [NSString stringWithFormat:@"%c", c];
    NSString *nova = [NSString stringWithCharacters:&c length:1];
    
    // Pega a menssagem
    NSString *final = @"/ARP/enviar/LigaTV";
    
    NSString *mensagem = [final stringByAppendingString:nova];
    
    // Mensagem de alerta
    UIAlertView *alert;
    
    // DEST Address
    sockaddr_in *dest_addr = (sockaddr_in*)malloc(sizeof(sockaddr_in));
    if(dest_addr == NULL){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Error creating dest_addr"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    // Init address
    memset(dest_addr, 0, sizeof(sockaddr_in));
    dest_addr->sin_family = AF_INET;
    
    // Get Source.
    
    // Load address.
    int pton_val = inet_pton(AF_INET, [enderecoIp UTF8String], &(dest_addr->sin_addr.s_addr));
    if(pton_val == 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"IP inválido"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }else if(pton_val < 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"Error in pton"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }
    // Definindo a porta
    dest_addr->sin_port=htons(4444);
    
    // Criando o socket.
    int sock_fd = socket(AF_INET, SOCK_DGRAM, UDP);
    
    // Error handling.
    if(sock_fd < 0){
        perror("socket");
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Socket não foi aberto"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
    }else{  // Our socket was opened
        // Get Message Data
        NSData *bytes = [mensagem dataUsingEncoding:NSUTF8StringEncoding];
        uint8_t * rawBytes = (uint8_t*)[bytes bytes];
        
        // Send our message.
        NSInteger error = sendto(sock_fd, rawBytes, [mensagem length], 0, (struct sockaddr*)dest_addr, sizeof(sockaddr_in));
        if(error < 0){
            alert = [[UIAlertView alloc]
                     initWithTitle:@"Erro!"
                     message:@"Messagem não chegou ao destino"
                     delegate:nil  // set nil if you don't want the yes button callback
                     cancelButtonTitle:@"Ok"                     otherButtonTitles:nil, nil];
            [alert show];
        }
    }

}

- (IBAction)Esquerda:(id)sender {
    // Pega o endereço IP
    NSArray *caminho = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *diretorioDocumento = [caminho objectAtIndex:0];
    NSString *caminhoArquivo = [diretorioDocumento stringByAppendingPathComponent:@"IPiOS.txt"];
    NSString *enderecoIp = [NSString stringWithContentsOfFile:caminhoArquivo encoding:NSUTF8StringEncoding error:NULL];
    
 //   unichar c = 0x00;// c = 0x00;
    //    NSString *nova = [NSString stringWithFormat:@"%c", c];
 //   NSString *nova = [NSString stringWithCharacters:&c length:1];
    
    // Pega a menssagem
   // NSString *final = @"/ARP/enviar/Esquerda";
    
   // NSString *mensagem = [final stringByAppendingString:nova];
    NSString *mensagem = [NSString stringWithFormat:@"/ARP/enviar/Esquerda",   0];    

    
    // Mensagem de alerta
    UIAlertView *alert;
    
    // DEST Address
    sockaddr_in *dest_addr = (sockaddr_in*)malloc(sizeof(sockaddr_in));
    if(dest_addr == NULL){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Error creating dest_addr"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    // Init address
    memset(dest_addr, 0, sizeof(sockaddr_in));
    dest_addr->sin_family = AF_INET;
    
    // Get Source.
    
    // Load address.
    int pton_val = inet_pton(AF_INET, [enderecoIp UTF8String], &(dest_addr->sin_addr.s_addr));
    if(pton_val == 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"IP inválido"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }else if(pton_val < 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"Error in pton"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }
    // Definindo a porta
    dest_addr->sin_port=htons(4444);
    
    // Criando o socket.
    int sock_fd = socket(AF_INET, SOCK_DGRAM, UDP);
    
    // Error handling.
    if(sock_fd < 0){
        perror("socket");
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Socket não foi aberto"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
    }else{  // Our socket was opened
        // Get Message Data
        NSData *bytes = [mensagem dataUsingEncoding:NSUTF8StringEncoding];
        uint8_t * rawBytes = (uint8_t*)[bytes bytes];
        
        // Send our message.
        NSInteger error = sendto(sock_fd, rawBytes, [mensagem length], 0, (struct sockaddr*)dest_addr, sizeof(sockaddr_in));
        if(error < 0){
            alert = [[UIAlertView alloc]
                     initWithTitle:@"Erro!"
                     message:@"Messagem não chegou ao destino"
                     delegate:nil  // set nil if you don't want the yes button callback
                     cancelButtonTitle:@"Ok"                     otherButtonTitles:nil, nil];
            [alert show];
        }
    }

}

- (IBAction)Baixo:(id)sender {
    // Pega o endereço IP
    NSArray *caminho = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *diretorioDocumento = [caminho objectAtIndex:0];
    NSString *caminhoArquivo = [diretorioDocumento stringByAppendingPathComponent:@"IPiOS.txt"];
    NSString *enderecoIp = [NSString stringWithContentsOfFile:caminhoArquivo encoding:NSUTF8StringEncoding error:NULL];
    
    unichar c = 0x00;// c = 0x00;
    //    NSString *nova = [NSString stringWithFormat:@"%c", c];
    NSString *nova = [NSString stringWithCharacters:&c length:1];
    
    // Pega a menssagem
    NSString *final = @"/ARP/enviar/Baixo";
    
    NSString *mensagem = [final stringByAppendingString:nova];
    
    // Mensagem de alerta
    UIAlertView *alert;
    
    // DEST Address
    sockaddr_in *dest_addr = (sockaddr_in*)malloc(sizeof(sockaddr_in));
    if(dest_addr == NULL){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Error creating dest_addr"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    // Init address
    memset(dest_addr, 0, sizeof(sockaddr_in));
    dest_addr->sin_family = AF_INET;
    
    // Get Source.
    
    // Load address.
    int pton_val = inet_pton(AF_INET, [enderecoIp UTF8String], &(dest_addr->sin_addr.s_addr));
    if(pton_val == 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"IP inválido"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }else if(pton_val < 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"Error in pton"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }
    // Definindo a porta
    dest_addr->sin_port=htons(4444);
    
    // Criando o socket.
    int sock_fd = socket(AF_INET, SOCK_DGRAM, UDP);
    
    // Error handling.
    if(sock_fd < 0){
        perror("socket");
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Socket não foi aberto"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
    }else{  // Our socket was opened
        // Get Message Data
        NSData *bytes = [mensagem dataUsingEncoding:NSUTF8StringEncoding];
        uint8_t * rawBytes = (uint8_t*)[bytes bytes];
        
        // Send our message.
        NSInteger error = sendto(sock_fd, rawBytes, [mensagem length], 0, (struct sockaddr*)dest_addr, sizeof(sockaddr_in));
        if(error < 0){
            alert = [[UIAlertView alloc]
                     initWithTitle:@"Erro!"
                     message:@"Messagem não chegou ao destino"
                     delegate:nil  // set nil if you don't want the yes button callback
                     cancelButtonTitle:@"Ok"                     otherButtonTitles:nil, nil];
            [alert show];
        }
    }
}

- (IBAction)Direita:(id)sender {
    // Pega o endereço IP
    NSArray *caminho = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *diretorioDocumento = [caminho objectAtIndex:0];
    NSString *caminhoArquivo = [diretorioDocumento stringByAppendingPathComponent:@"IPiOS.txt"];
    NSString *enderecoIp = [NSString stringWithContentsOfFile:caminhoArquivo encoding:NSUTF8StringEncoding error:NULL];
    
    unichar c = 0x00;// c = 0x00;
    //    NSString *nova = [NSString stringWithFormat:@"%c", c];
    NSString *nova = [NSString stringWithCharacters:&c length:1];
    
    // Pega a menssagem
    NSString *final = @"/ARP/enviar/Direita";
    
    NSString *mensagem = [final stringByAppendingString:nova];
    
    // Mensagem de alerta
    UIAlertView *alert;
    
    // DEST Address
    sockaddr_in *dest_addr = (sockaddr_in*)malloc(sizeof(sockaddr_in));
    if(dest_addr == NULL){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Error creating dest_addr"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    // Init address
    memset(dest_addr, 0, sizeof(sockaddr_in));
    dest_addr->sin_family = AF_INET;
    
    // Get Source.
    
    // Load address.
    int pton_val = inet_pton(AF_INET, [enderecoIp UTF8String], &(dest_addr->sin_addr.s_addr));
    if(pton_val == 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"IP inválido"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }else if(pton_val < 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"Error in pton"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }
    // Definindo a porta
    dest_addr->sin_port=htons(4444);
    
    // Criando o socket.
    int sock_fd = socket(AF_INET, SOCK_DGRAM, UDP);
    
    // Error handling.
    if(sock_fd < 0){
        perror("socket");
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Socket não foi aberto"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
    }else{  // Our socket was opened
        // Get Message Data
        NSData *bytes = [mensagem dataUsingEncoding:NSUTF8StringEncoding];
        uint8_t * rawBytes = (uint8_t*)[bytes bytes];
        
        // Send our message.
        NSInteger error = sendto(sock_fd, rawBytes, [mensagem length], 0, (struct sockaddr*)dest_addr, sizeof(sockaddr_in));
        if(error < 0){
            alert = [[UIAlertView alloc]
                     initWithTitle:@"Erro!"
                     message:@"Messagem não chegou ao destino"
                     delegate:nil  // set nil if you don't want the yes button callback
                     cancelButtonTitle:@"Ok"                     otherButtonTitles:nil, nil];
            [alert show];
        }
    }
}

- (IBAction)Cima:(id)sender {
    // Pega o endereço IP
    NSArray *caminho = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *diretorioDocumento = [caminho objectAtIndex:0];
    NSString *caminhoArquivo = [diretorioDocumento stringByAppendingPathComponent:@"IPiOS.txt"];
    NSString *enderecoIp = [NSString stringWithContentsOfFile:caminhoArquivo encoding:NSUTF8StringEncoding error:NULL];
    
    unichar c = 0x0;// c = 0x00;
    //    NSString *nova = [NSString stringWithFormat:@"%c", c];
    NSString *nova = [NSString stringWithCharacters:&c length:1];
    
    // Pega a menssagem
    NSString *final = @"/ARP/enviar/Cima";
    
    NSString *mensagem = [final stringByAppendingString:nova];
    
    // Mensagem de alerta
    UIAlertView *alert;
    
    // DEST Address
    sockaddr_in *dest_addr = (sockaddr_in*)malloc(sizeof(sockaddr_in));
    if(dest_addr == NULL){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Error creating dest_addr"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    // Init address
    memset(dest_addr, 0, sizeof(sockaddr_in));
    dest_addr->sin_family = AF_INET;
    
    // Get Source.
    
    // Load address.
    int pton_val = inet_pton(AF_INET, [enderecoIp UTF8String], &(dest_addr->sin_addr.s_addr));
    if(pton_val == 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"IP inválido"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }else if(pton_val < 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"Error in pton"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }
    // Definindo a porta
    dest_addr->sin_port=htons(4444);
    
    // Criando o socket.
    int sock_fd = socket(AF_INET, SOCK_DGRAM, UDP);
    
    // Error handling.
    if(sock_fd < 0){
        perror("socket");
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Socket não foi aberto"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
    }else{  // Our socket was opened
        // Gfet Message Data
        NSData *bytes = [mensagem dataUsingEncoding:NSUTF8StringEncoding];
        uint8_t * rawBytes = (uint8_t*)[bytes bytes];
        
        // Send our message.
        NSInteger error = sendto(sock_fd, rawBytes, [mensagem length], 0, (struct sockaddr*)dest_addr, sizeof(sockaddr_in));
        if(error < 0){
            alert = [[UIAlertView alloc]
                     initWithTitle:@"Erro!"
                     message:@"Messagem não chegou ao destino"
                     delegate:nil  // set nil if you don't want the yes button callback
                     cancelButtonTitle:@"Ok"                     otherButtonTitles:nil, nil];
            [alert show];
        }
    }
}

- (IBAction)DesligarRele0:(id)sender {
    
    // Pega o endereço IP
    NSArray *caminho = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *diretorioDocumento = [caminho objectAtIndex:0];
    NSString *caminhoArquivo = [diretorioDocumento stringByAppendingPathComponent:@"IPiOS.txt"];
    NSString *enderecoIp = [NSString stringWithContentsOfFile:caminhoArquivo encoding:NSUTF8StringEncoding error:NULL];
    
    
    // Pega a menssagem
    NSString *mensagem = @"/ARP/relay/0013A20040A13917/0/0";
    
    // Mensagem de alerta
    UIAlertView *alert;
    
    // DEST Address
    sockaddr_in *dest_addr = (sockaddr_in*)malloc(sizeof(sockaddr_in));
    if(dest_addr == NULL){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Error creating dest_addr"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    // Init address
    memset(dest_addr, 0, sizeof(sockaddr_in));
    dest_addr->sin_family = AF_INET;
    
    // Get Source.
    
    // Load address.
    int pton_val = inet_pton(AF_INET, [enderecoIp UTF8String], &(dest_addr->sin_addr.s_addr));
    if(pton_val == 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"IP inválido"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }else if(pton_val < 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"Error in pton"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }
    // Definindo a porta
    dest_addr->sin_port=htons(4444);
    
    // Criando o socket.
    int sock_fd = socket(AF_INET, SOCK_DGRAM, UDP);
    
    // Error handling.
    if(sock_fd < 0){
        perror("socket");
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Socket não foi aberto"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
    }else{  // Our socket was opened
        // Get Message Data
        NSData *bytes = [mensagem dataUsingEncoding:NSUTF8StringEncoding];
        uint8_t * rawBytes = (uint8_t*)[bytes bytes];
        
        // Send our message.
        NSInteger error = sendto(sock_fd, rawBytes, [mensagem length], 0, (struct sockaddr*)dest_addr, sizeof(sockaddr_in));
        if(error < 0){
            alert = [[UIAlertView alloc]
                     initWithTitle:@"Erro!"
                     message:@"Messagem não chegou ao destino"
                     delegate:nil  // set nil if you don't want the yes button callback
                     cancelButtonTitle:@"Ok"                     otherButtonTitles:nil, nil];
            [alert show];
        }
    }
}
- (IBAction)LigarRele0:(id)sender {
    // Pega o endereço IP
    NSArray *caminho = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *diretorioDocumento = [caminho objectAtIndex:0];
    NSString *caminhoArquivo = [diretorioDocumento stringByAppendingPathComponent:@"IPiOS.txt"];
    NSString *enderecoIp = [NSString stringWithContentsOfFile:caminhoArquivo encoding:NSUTF8StringEncoding error:NULL];
    
    
    // Pega a menssagem
    NSString *mensagem = @"/ARP/relay/0013A20040A13917/0/1";
    
    // Mensagem de alerta
    UIAlertView *alert;
    
    // DEST Address
    sockaddr_in *dest_addr = (sockaddr_in*)malloc(sizeof(sockaddr_in));
    if(dest_addr == NULL){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Error creating dest_addr"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    // Init address
    memset(dest_addr, 0, sizeof(sockaddr_in));
    dest_addr->sin_family = AF_INET;
    
    // Get Source.
    
    // Load address.
    int pton_val = inet_pton(AF_INET, [enderecoIp UTF8String], &(dest_addr->sin_addr.s_addr));
    if(pton_val == 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"IP inválido"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }else if(pton_val < 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"Error in pton"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }
    // Definindo a porta
    dest_addr->sin_port=htons(4444);
    
    // Criando o socket.
    int sock_fd = socket(AF_INET, SOCK_DGRAM, UDP);
    
    // Error handling.
    if(sock_fd < 0){
        perror("socket");
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Socket não foi aberto"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
    }else{  // Our socket was opened
        // Get Message Data
        NSData *bytes = [mensagem dataUsingEncoding:NSUTF8StringEncoding];
        uint8_t * rawBytes = (uint8_t*)[bytes bytes];
        
        // Send our message.
        NSInteger error = sendto(sock_fd, rawBytes, [mensagem length], 0, (struct sockaddr*)dest_addr, sizeof(sockaddr_in));
        if(error < 0){
            alert = [[UIAlertView alloc]
                     initWithTitle:@"Erro!"
                     message:@"Messagem não chegou ao destino"
                     delegate:nil  // set nil if you don't want the yes button callback
                     cancelButtonTitle:@"Ok"                     otherButtonTitles:nil, nil];
            [alert show];
        }
    }
}
- (IBAction)DesligarRele1:(id)sender {
    
    // Pega o endereço IP
    NSArray *caminho = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *diretorioDocumento = [caminho objectAtIndex:0];
    NSString *caminhoArquivo = [diretorioDocumento stringByAppendingPathComponent:@"IPiOS.txt"];
    NSString *enderecoIp = [NSString stringWithContentsOfFile:caminhoArquivo encoding:NSUTF8StringEncoding error:NULL];
    
    
    // Pega a menssagem
    NSString *mensagem = @"/ARP/relay/0013A20040A13917/0/1";
    
    // Mensagem de alerta
    UIAlertView *alert;
    
    // DEST Address
    sockaddr_in *dest_addr = (sockaddr_in*)malloc(sizeof(sockaddr_in));
    if(dest_addr == NULL){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Error creating dest_addr"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    // Init address
    memset(dest_addr, 0, sizeof(sockaddr_in));
    dest_addr->sin_family = AF_INET;
    
    // Get Source.
    
    // Load address.
    int pton_val = inet_pton(AF_INET, [enderecoIp UTF8String], &(dest_addr->sin_addr.s_addr));
    if(pton_val == 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"IP inválido"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }else if(pton_val < 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"Error in pton"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }
    // Definindo a porta
    dest_addr->sin_port=htons(4444);
    
    // Criando o socket.
    int sock_fd = socket(AF_INET, SOCK_DGRAM, UDP);
    
    // Error handling.
    if(sock_fd < 0){
        perror("socket");
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Socket não foi aberto"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
    }else{  // Our socket was opened
        // Get Message Data
        NSData *bytes = [mensagem dataUsingEncoding:NSUTF8StringEncoding];
        uint8_t * rawBytes = (uint8_t*)[bytes bytes];
        
        // Send our message.
        NSInteger error = sendto(sock_fd, rawBytes, [mensagem length], 0, (struct sockaddr*)dest_addr, sizeof(sockaddr_in));
        if(error < 0){
            alert = [[UIAlertView alloc]
                     initWithTitle:@"Erro!"
                     message:@"Messagem não chegou ao destino"
                     delegate:nil  // set nil if you don't want the yes button callback
                     cancelButtonTitle:@"Ok"                     otherButtonTitles:nil, nil];
            [alert show];
        }
    }
}


- (IBAction)LigarRele1:(id)sender {
    // Pega o endereço IP
    NSArray *caminho = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *diretorioDocumento = [caminho objectAtIndex:0];
    NSString *caminhoArquivo = [diretorioDocumento stringByAppendingPathComponent:@"IPiOS.txt"];
    NSString *enderecoIp = [NSString stringWithContentsOfFile:caminhoArquivo encoding:NSUTF8StringEncoding error:NULL];
    
    
    // Pega a menssagem
    NSString *mensagem = @"/ARP/relay/0013A20040A13917/1/1";
    
    // Mensagem de alerta
    UIAlertView *alert;
    
    // DEST Address
    sockaddr_in *dest_addr = (sockaddr_in*)malloc(sizeof(sockaddr_in));
    if(dest_addr == NULL){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Error creating dest_addr"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    // Init address
    memset(dest_addr, 0, sizeof(sockaddr_in));
    dest_addr->sin_family = AF_INET;
    
    // Get Source.
    
    // Load address.
    int pton_val = inet_pton(AF_INET, [enderecoIp UTF8String], &(dest_addr->sin_addr.s_addr));
    if(pton_val == 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"IP inválido"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }else if(pton_val < 0){
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Error!"
                 message:@"Error in pton"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
        
    }
    // Definindo a porta
    dest_addr->sin_port=htons(4444);
    
    // Criando o socket.
    int sock_fd = socket(AF_INET, SOCK_DGRAM, UDP);
    
    // Error handling.
    if(sock_fd < 0){
        perror("socket");
        // Message Box with error.
        alert = [[UIAlertView alloc]
                 initWithTitle:@"Erro!"
                 message:@"Socket não foi aberto"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Ok"                 otherButtonTitles:nil, nil];
        [alert show];
    }else{  // Our socket was opened
        // Get Message Data
        NSData *bytes = [mensagem dataUsingEncoding:NSUTF8StringEncoding];
        uint8_t * rawBytes = (uint8_t*)[bytes bytes];
        
        // Send our message.
        NSInteger error = sendto(sock_fd, rawBytes, [mensagem length], 0, (struct sockaddr*)dest_addr, sizeof(sockaddr_in));
        if(error < 0){
            alert = [[UIAlertView alloc]
                     initWithTitle:@"Erro!"
                     message:@"Messagem não chegou ao destino"
                     delegate:nil  // set nil if you don't want the yes button callback
                     cancelButtonTitle:@"Ok"                     otherButtonTitles:nil, nil];
            [alert show];
        }
    }

}

@end
