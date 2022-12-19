#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <netinet/in.h>
#include <netinet/ip.h>

void deservire_client(int c) {
    uint16_t n,list[100], suma = 0;
    recv(c, &n, sizeof(n), MSG_WAITALL);
    n = ntohs(n);
    int i;

    for(i = 0; i < n; ++i) {
        recv(c, &list[i], sizeof(list[i]), MSG_WAITALL);
        list[i] = ntohs(list[i]);
        suma = suma + list[i];
    }
    suma = htons(suma);
    send(c, &suma, sizeof(suma), 0);

    close(c);
}

int main() {
    int s;
    struct sockaddr_in server, client;
    int c, l;

    s = socket(AF_INET, SOCK_STREAM, 0);
    if (s < 0) {
        printf("Eroare la crearea socketului server\n");
        return 1;
    }

    memset(&server, 0 , sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;

    if (bind(s, (struct sockaddr*) &server, sizeof(server)) < 0) {
        printf("Eroare la bind\n");
        return 1;
    }

    listen(s, 5);

    l = sizeof(client);
    memset(&client, 0, sizeof(client));

    while (1) {
        c = accept(s, (struct sockaddr *) &server, (socklen_t *) &l);
        printf("S-a conectat un client\n");
        if (fork() == 0) {
            deservire_client(c);
            return 0;
        }
    }
    
}
    
