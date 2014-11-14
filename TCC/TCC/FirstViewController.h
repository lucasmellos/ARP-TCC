//
//  FirstViewController.h
//  TCC
//
//  Created by M on 12/08/14.
//  Copyright (c) 2014 M. All rights reserved.
//

#import <UIKit/UIKit.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <errno.h>
#define UDP 17
typedef struct sockaddr_in sockaddr_in;

@interface FirstViewController : UIViewController
- (IBAction)LigarTV:(id)sender;
- (IBAction)Menu:(id)sender;
- (IBAction)Esquerda:(id)sender;
- (IBAction)Baixo:(id)sender;
- (IBAction)Direita:(id)sender;
- (IBAction)Cima:(id)sender;
- (IBAction)DesligarRele0:(id)sender;
- (IBAction)LigarRele0:(id)sender;
- (IBAction)DesligarRele1:(id)sender;

- (IBAction)LigarRele1:(id)sender;

@end

