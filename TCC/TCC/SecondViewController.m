//
//  SecondViewController.m
//  TCC
//
//  Created by M on 12/08/14.
//  Copyright (c) 2014 M. All rights reserved.
//

#import "SecondViewController.h"

@interface SecondViewController ()

@end

@implementation SecondViewController
            
- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)Salvar:(id)sender {
    NSString* enderecoIp = self.EnderecoIP.text;
    
    UIAlertView *alert;
    
    NSError *erro;
    
    NSArray *caminho = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *diretorioDocumento = [caminho objectAtIndex:0];
    NSString *caminhoArquivo = [diretorioDocumento stringByAppendingPathComponent:@"IPiOS.txt"];
    
    
    
    BOOL ok = [enderecoIp writeToFile:caminhoArquivo atomically:TRUE encoding:NSUTF8StringEncoding error:NULL];
    
    if (!ok) {
        NSLog(@"Erro na escrita do arquivo em %@\n%@",
              caminhoArquivo, [erro localizedFailureReason]);
    }else{
        alert = [[UIAlertView alloc]
                 initWithTitle:@"IP!"
                 message:@"Endereço salvo"
                 delegate:nil  // set nil if you don't want the yes button callback
                 cancelButtonTitle:@"Fechar"
                 otherButtonTitles:nil, nil];
        [alert show];
        
    }

}
@end
