#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

int main () {
    int c;
    struct sockaddr_in server;
    uint16_t suma,n,list[100];

    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Eroare la crearea socketului clientului\n");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr("127.0.0.1");

    if (connect(c, (struct sockaddr *) &server, sizeof(server)) <0) {
        printf("Eroare la conectare la server");
        return 1;
    }

    printf("n = ");
    scanf("%hu",&n);
    int i;
    printf("Dati numerele: ");
    for(i=0; i<n; ++i) {
        scanf("%hu",&list[i]);
    }
    uint16_t x = htons(n);
    send(c,&x,sizeof(x),0);
    
    for(i=0; i<n; ++i) {
        uint16_t x = htons(list[i]);
        send(c,&x,sizeof(x),0);
    }
    recv(c,&suma,sizeof(suma), 0);
    suma = ntohs(suma);
    printf("Suma este %hu\n",suma);
    close(c);
}
