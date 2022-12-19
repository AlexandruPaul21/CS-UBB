#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <arpa/inet.h>

int main() {
    int c;
    struct sockaddr_in server, client;
    uint16_t n;

    c = socket(AF_INET, SOCK_DGRAM, 0);

    if (c < 0) {
        printf("Eroare la crearea socketului");
        return 1;
    }

    memset(&server, 0, sizeof(server));

    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr("127.0.0.1");

    printf("Dati n: ");
    scanf("%hu",&n);
    uint16_t mask;
    mask = htons(n);

    sendto(c, &mask, sizeof(mask), 0,(struct sockaddr *) &server,sizeof(server));
    
    long long i = 1;
    for(i = 0; i <= 100000000; i+=2) {
        i--;
    }

    int l = sizeof(client);
    memset(&client, 0, sizeof(client));

    uint16_t ans;
    recvfrom(c, &ans, sizeof(ans), MSG_WAITALL, (struct sockaddr *) &client,(socklen_t *) &l);
    
    ans = ntohs(ans);

    printf("%hu\n", ans);

    return 0;
}
