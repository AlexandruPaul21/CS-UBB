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
  
    s = socket(AF_INET, SOCK_DGRAM, 0);
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
    int l = sizeof(client); 
    memset(&client, 0, sizeof(client));
while(1) {    
    uint16_t n; 
    recvfrom(s, &n, sizeof(n), MSG_WAITALL,(struct sockaddr *) &client, (socklen_t *) &l);
    n = ntohs(n);
    //if (n != 0) printf("%hu\n",n);
    uint16_t d, mask;
    int ok = 1;
    for (d = 2; d * d <= n; ++d) {
        if (n % d == 0) {
            ok = 0;
        }
    }
    if (ok) {
        mask = 1;
    } else {
        mask = 0;
    }
    //if (n != 0) printf("%hu\n",mask);
    mask = htons(mask);
    sendto(s, &mask, sizeof(mask), 0, (struct sockaddr *) &client, sizeof(client));
    if (n != 0) {
        break;
    }
}
    close(s);
}
