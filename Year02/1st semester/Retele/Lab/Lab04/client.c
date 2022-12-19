#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>

int main() {
  int c;
  struct sockaddr_in server;
  uint16_t k;
  int i;
  
  
  c = socket(AF_INET, SOCK_DGRAM, 0);
  if (c < 0) {
    printf("Eroare la crearea socketului client\n");
    return 1;
  }
  
  memset(&server, 0, sizeof(server));
  server.sin_port = htons(1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr("172.30.117.184");
  
  for (i = 0; i < 100000; i++) {
    k = i + 1;
    k = htons(k);
    sendto(c, &k, sizeof(k), 0, (struct sockaddr *) &server, sizeof(server));
  }
  
  close(c);
}
