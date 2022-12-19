#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>

int main() {
    int s;
    struct sockaddr_in server, client;
    int c, l;

    s = socket(AF_INET, SOCK_STREAM, 0);

    if (s < 0) {
        printf("Eroare la crearea socketului server\n");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;

    if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
        printf("Eroare la bind\n");
        return 1;
    }

    listen(s,5);

    l = sizeof(client);
    memset(&client, 0, sizeof(client));

    while (1) {
        char sir[100];
        c = accept(s, (struct sockaddr *) &client, (socklen_t *) &l);
        printf("S-a conectat un client\n");

        recv(c, sir, sizeof(sir), 0);
        
        printf("%s\n",sir);

        close(c);
    }
}
